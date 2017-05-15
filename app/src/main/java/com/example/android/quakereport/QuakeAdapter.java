package com.example.android.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 *  Created by Jessica on 2017-05-12.
 *  QuakeAdapter is a custom ArrayAdapter for displaying information about earthquakes
 *      in a list.
 */

public class QuakeAdapter extends ArrayAdapter<Quakes> {

    /*
            VARIABLES
     */


    /*
            CONSTRUCTORS
     */

    /**
     *  Constructor for the QuakeAdapter class.
     *  @param context is the current context that the adapter is being created in.
     *  @param quakes is the list of earthquake information to be displayed.
     */
    public QuakeAdapter(Context context, ArrayList<Quakes> quakes) {
        super(context, 0, quakes);
    }


    /*
            GETTERS AND SETTERS
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link com.example.android.quakereport.Quakes} object located at this position in the list
        Quakes currentQuakes = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID mag_text_view.
        TextView magTextView = (TextView) listItemView.findViewById(R.id.mag_text_view);
        // Get the magnitude of the earthquake from the currentQuakes object and set this text on
        // the mag TextView.
        magTextView.setText(currentQuakes.getmMag());

        // Find the TextView in the list_item.xml layout with the ID place_text_view.
        TextView placeTextView = (TextView) listItemView.findViewById(R.id.place_text_view);
        // Get the earthquake's place from the currentQuakes object and set this text on
        // the place TextView.
        placeTextView.setText(currentQuakes.getmPlace());

        // Find the TextView in the list_item.xml layout with the ID date_text_view.
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_text_view);
        // Get the earthquake's date from the currentQuakes object and set this text on
        // the date TextView.
        dateTextView.setText(currentQuakes.getmDate());

        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.
        return listItemView;
    }
}
