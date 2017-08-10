/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Quakes>> {

    private static final int EARTHQUAKE_LOADER_ID = 1;
    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    private QuakeAdapter mAdapter;
    private static final String USGSQuery = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Start the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Find a reference to the {@link ListView} in the layout
        ListView quakeListView = (ListView) findViewById(R.id.list);

        // Init the QuakeAdapter (may cause nullpointerexception?)
        mAdapter = new QuakeAdapter(this, new ArrayList<Quakes>());

        // Populate the ListView with the QuakeAdapter's ArrayList
        quakeListView.setAdapter(mAdapter);

        getLoaderManager().initLoader(0, null, this);

        quakesASyncTask fetchTask = new quakesASyncTask();
        fetchTask.execute(USGSQuery);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected earthquake.
        quakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                Quakes currentQuake = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri quakeUri = Uri.parse(currentQuake.getmUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, quakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
    }

    private void updateUI(ArrayList<Quakes> quakes) {

        // Find a reference to the {@link ListView} in the layout
        ListView quakeListView = (ListView) findViewById(R.id.list);

        // Set an onItemClickListener on the earthquakeListView so we can open urls
        quakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Log the click for debugging
                Log.v("MainActivity", "Clicky clicky" + " SUPERCAT");

                // Find the current earthquake that was clicked on
                Quakes currentQuake = mAdapter.getItem(position);

                // Open the url associated with the earthquake that was clicked on
                Intent urlIntent = new Intent(Intent.ACTION_VIEW);
                urlIntent.setData(Uri.parse(currentQuake.getmUrl()));
                startActivity(urlIntent);
            }
        });
    }

    @Override
    public Loader<List<Quakes>> onCreateLoader(int i, Bundle bundle) {
        return new QuakesLoader(EarthquakeActivity.this, USGSQuery);
    }

    @Override
    public void onLoadFinished(Loader<List<Quakes>> loader, List<Quakes> earthquakes) {
        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);

        }
    }

    @Override
    public void onLoaderReset(Loader<List<Quakes>> loader) {
        // TODO: Loader reset, so we can clear out our existing data.
    }

    private class quakesASyncTask extends AsyncTask<String, Void, ArrayList<Quakes>> {

        @Override
        protected ArrayList<Quakes> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            ArrayList<Quakes> quakes = QueryUtils.makeQuake(urls[0]);
            return quakes;

        }

        @Override
        protected void onPostExecute(ArrayList<Quakes> quakes) {

            if (quakes == null) {
                return;
            }

            updateUI(quakes);
        }
    }
}
