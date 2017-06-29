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

import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Quakes>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String USGSQuery = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        quakesASyncTask fetchTask = new quakesASyncTask();
        fetchTask.execute(USGSQuery);
    }

    private void updateUI(ArrayList<Quakes> quakes) {
        // Create a new {@link ArrayAdapter} of earthquakes
        final QuakeAdapter adapter = new QuakeAdapter(
                this, quakes);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        // Set an onItemClickListener on the earthquakeListView so we can open urls
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Log the click for debugging
                Log.v("MainActivity", "Clicky clicky" + " SUPERCAT");

                // Find the current earthquake that was clicked on
                Quakes currentQuake = adapter.getItem(position);

                // Open the url associated with the earthquake that was clicked on
                Intent urlIntent = new Intent(Intent.ACTION_VIEW);
                urlIntent.setData(Uri.parse(currentQuake.getmUrl()));
                startActivity(urlIntent);
            }
        });
    }

    @Override
    public Loader<List<Quakes>> onCreateLoader(int i, Bundle bundle) {
        return new QuakesLoader(EarthquakeActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<List<Quakes>> loader, List<Quakes> earthquakes) {
        // TODO: Update the UI with the result
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
