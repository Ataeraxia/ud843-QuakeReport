package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();
    public static final String LOG_FLAG = " SUPERCAT";

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    public static ArrayList<Quakes> makeQuake(String USGSQuery) {
        URL url = createURL(USGSQuery);

        String JSONResponse = null;
        try {
            JSONResponse = getJSONResponse(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream? " + e + LOG_FLAG);
        }

        ArrayList<Quakes> earthquakes = extractEarthquakes(JSONResponse);

        return earthquakes;
    }

    private static URL createURL(String USGSQuery) {

        URL url = null;

        try {
            url = new URL(USGSQuery);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL " + e + LOG_FLAG);
        }

        return url;
    }

    private static String getJSONResponse(URL url) throws IOException {

        //Vars
        String JSONResponse = null;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        //Guards
        if (url == null) {
            return null;
        }

        //Action
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* Milliseconds, that is */);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            int responseCode = urlConnection.getResponseCode();

            // TODO: Find new url for USGSQuery. You're getting a '301: permanently moved.'
            if (responseCode == 200) {
                inputStream = urlConnection.getInputStream();
                JSONResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error: Invalid response code " + responseCode + LOG_FLAG);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException opening url connection: " + e + LOG_FLAG);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return JSONResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader buffReader = new BufferedReader(reader);
            String line = buffReader.readLine();
            while (line != null) {
                output.append(line);
                line = buffReader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Quakes} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Quakes> extractEarthquakes(String JSONResponse) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(JSONResponse)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Quakes> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.

            JSONObject responseString = new JSONObject(JSONResponse);
            JSONArray earthquakeArray = responseString.getJSONArray("features");

            for (int i = 0; i < earthquakeArray.length(); i++) {
                JSONObject currentQuake = earthquakeArray.getJSONObject(i);
                JSONObject currentProperties = currentQuake.getJSONObject("properties");
                Double mag = currentProperties.getDouble("mag");
                String place = currentProperties.getString("place");
                Long time = currentProperties.getLong("time");
                String url = currentProperties.getString("url");

                Quakes quake = new Quakes(mag, place, time, url);
                earthquakes.add(quake);
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

}

