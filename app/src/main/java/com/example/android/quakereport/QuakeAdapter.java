package com.example.android.quakereport;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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

        /*
                SET UP
         */

        // Log that parent is not null
        Log.d("QuakeAdapter", "ViewGroup parent: " + parent.toString() + "SUPERCAT");

        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link com.example.android.quakereport.Quakes} object located at this position in the list
        Quakes currentQuakes = getItem(position);


        /*
                FORMATTING
         */

        // Date and time formatting

        // Make a new Date object named quakeDate with the value found in the current Quakes mDate variable.
        Date quakeDate = new Date(currentQuakes.getmDate());
        // Format quakeDate as instructed in the formatDate() method.
        String quakeDateFormatted = formatDate(quakeDate);
        // Format quakeDate as instructed in the formatTime() method.
        String quakeTimeFormatted = formatTime(quakeDate);

        // TODO: Place and near formatting
        // if String place contains "of" { -find the index of "of" (ie if "of" is found in characters
        //                                  8 and 9, the index should be 9)
        //                                 -take indexes 0 tp (i) and set them to a String variable,
        //                                  quakeNearString
        //                                 -take indexes (i++) to (last index) and set them to
        //                                  a different String variable, quakePlaceString }

        // Get the raw description of where the quake took place
        String rawPlaceString = currentQuakes.getmPlace();
        // Use this String to determine whether there is a "near" description
        String lookForOf = "of";

        // Record whether there is a "near" description in the raw place String
        Boolean placeContainsOf = rawPlaceString.contains(lookForOf);

        //
        if (placeContainsOf) {
            int endOfNear = rawPlaceString.indexOf(lookForOf) + 1;
            Log.v("QuakeAdapter", "Index: " + endOfNear);
        }

        // else { set quakeNearString to "Near the" and quakePlaceString to getmPlace() }

        /*
                SETTING TEXTVIEWS
         */

        // Magnitude TextView

        // Find the TextView in the list_item.xml layout with the ID mag_text_view.
        TextView magTextView = (TextView) listItemView.findViewById(R.id.mag_text_view);
        // Get the magnitude of the earthquake from the currentQuakes object and set this text on
        // the mag TextView.
        magTextView.setText(currentQuakes.getmMag());
        // Log that getmMag did not return null
        Log.d("QuakeAdapter", "magTextView: " + magTextView.toString() + "SUPERCAT");

        // Place TextView

        // Find the TextView in the list_item.xml layout with the ID place_text_view.
        TextView placeTextView = (TextView) listItemView.findViewById(R.id.place_text_view);
        // Get the earthquake's place from the currentQuakes object and set this text on
        // the place TextView.
        placeTextView.setText(currentQuakes.getmPlace());

        // TODO: Near TextView

        // TODO: Find the TextView in the list_item.xml layout with the ID near_text_view.
        // TODO: Get the earthquake's near data from the formatting variable and set this text
        // TODO: on the near TextView.

        // Date TextView

        // Find the TextView in the list_item.xml layout with the ID date_text_view.
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_text_view);
        // Get the earthquake's date from the currentQuakes object and set this text on
        // the date TextView.
        dateTextView.setText(quakeDateFormatted);

        // Time TextView

        // Find the TextView in the list_item.xml layout with the ID time_text_view.
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time_text_view);
        // Get the earthquake's time from the currentQuakes object and set this text on
        // the time TextView.
        timeTextView.setText(quakeTimeFormatted);


        // Return

        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.
        return listItemView;
    }


    /*
            METHODS
     */

    /**
     *  Format quakeDate(@param pQuakeDate) to follow the format [Jan 23, 2009].
     *  @return the formatted pQuakeDate.
     */
    private String formatDate(Date pQuakeDate) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("LLL dd, yyyy", Locale.CANADA);
        return dateFormatter.format(pQuakeDate);
    }

    /**
     *  Format quakeTime(@param pQuakeTime) to follow the format [9:35 am].
     *  @return the formatted pQuakeTime.
     */
    private String formatTime(Date pQuakeTime) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("h:mm a", Locale.CANADA);
        return dateFormatter.format(pQuakeTime);
    }


}
