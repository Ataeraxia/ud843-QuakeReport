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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.
        final ArrayList<Quakes> earthquakes = new ArrayList<>();
        earthquakes.add(new Quakes("3.7", "San Francisco", "Nov 11, 2017"));
        earthquakes.add(new Quakes("2.3", "London", "Nov 11, 2017"));
        earthquakes.add(new Quakes("4.9", "Tokyo", "Nov 11, 2017"));
        earthquakes.add(new Quakes("2.9", "Mexico City", "Nov 11, 2017"));
        earthquakes.add(new Quakes("7.3", "Moscow", "Nov 11, 2017"));
        earthquakes.add(new Quakes("8.2", "Rio de Janeiro", "Nov 11, 2017"));
        earthquakes.add(new Quakes("1.4", "Paris", "Nov 11, 2017"));

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        QuakeAdapter adapter = new QuakeAdapter(
                this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);
    }
}
