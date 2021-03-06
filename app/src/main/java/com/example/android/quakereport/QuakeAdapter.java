package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
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

    Double doubMag;

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
        Log.d("QuakeAdapter", "ViewGroup parent: " + parent.toString() + " SUPERCAT");

        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link com.example.android.quakereport.Quakes} object located at this position in the list
        Quakes currentQuakes = getItem(position);

        // Set up variables now that we have context

        // Magnitude of the earthquake, found in Quakes.java
        doubMag = currentQuakes.getmMag();
        // Log the Double for bug testing
        Log.d("QuakeAdapter", "Double doubMag: " + doubMag + " SUPERCAT");


        /*
                FORMATTING
         */

        // Magnitude formatting

        // Create a new DecimalFormatter that will format the magnitude
        DecimalFormat decFormat = new DecimalFormat("0.0");

        // Create a String variable that will hold the formatted Double variable
        String formatMag = decFormat.format(doubMag);

        // Date and time formatting

        // Make a new Date object named quakeDate with the value found in the current Quakes mDate variable.
        Date quakeDate = new Date(currentQuakes.getmDate());
        // Log quakeDate for ease of debugging
        Log.d("QuakeAdapter", "Date object quakeDate: " + quakeDate.toString() + " SUPERCAT");
        // Format quakeDate as instructed in the formatDate() method.
        String quakeDateFormatted = formatDate(quakeDate);
        // Format quakeDate as instructed in the formatTime() method.
        String quakeTimeFormatted = formatTime(quakeDate);

        // Place and near formatting

        // Get the raw description of where the quake took place
        String rawPlaceString = currentQuakes.getmPlace();
        // Use this String to determine whether there is a "near" description
        String lookForOf = "of";
        // Declare nearSubString and placeSubString here so we can access them
        String nearSubString;
        String placeSubString;

        // Record whether there is a "near" description in the raw place String
        Boolean placeContainsOf = rawPlaceString.contains(lookForOf);

        if (placeContainsOf) {
            // Get the length of the near description
            int endOfNear = rawPlaceString.indexOf(lookForOf) + 2;
            // Get the length of the rawPlaceString
            int endOfRaw = rawPlaceString.length();
            // Log the length so we know we're doing it right
            Log.v("QuakeAdapter", "Index: " + endOfNear);

            // Set nearSubString to the near data
            nearSubString = rawPlaceString.substring(0, endOfNear);
            // Make sure we're doing it right
            Log.v("QuakeAdapter", "Near: " + nearSubString);

            // Set placeSubString to the place data
            placeSubString = rawPlaceString.substring(endOfNear + 1, endOfRaw);
            // Make sure we're doing it right
            Log.v("QuakeAdapter", "Location: " + placeSubString);
        }

        // else { set quakeNearString to "Near the" and quakePlaceString to getmPlace() }

        else {
            nearSubString = "Near the";
            placeSubString = rawPlaceString;
        }


        /*
                SETTING TEXTVIEWS
         */

        // Magnitude TextView

        // Find the TextView in the list_item.xml layout with the ID mag_text_view.
        TextView magTextView = (TextView) listItemView.findViewById(R.id.mag_text_view);
        // Get the magnitude of the earthquake from the currentQuakes object and set this text on
        // the mag TextView.
        magTextView.setText(formatMag);
        // Log that getmMag did not return null
        Log.d("QuakeAdapter", "magTextView: " + magTextView.toString() + "SUPERCAT");
        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magTextView.getBackground();
        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(doubMag);
        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        // Place TextView

        // Find the TextView in the list_item.xml layout with the ID place_text_view.
        TextView placeTextView = (TextView) listItemView.findViewById(R.id.place_text_view);
        // Get the earthquake's place from the currentQuakes object and set this text on
        // the place TextView.
        placeTextView.setText(placeSubString);

        // Near TextView

        // Find the TextView in the list_item.xml layout with the ID near_text_view.
        TextView nearTextView = (TextView) listItemView.findViewById(R.id.near_text_view);
        // Get the earthquake's near data from the formatting variable and set this text
        // on the near TextView.
        nearTextView.setText(nearSubString);

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

    private int getMagnitudeColor(Double magnitude) {
        int magColor;
        int intMag = (int) Math.floor(magnitude);

        switch(intMag) {
            case 10:
                magColor = R.color.magnitude10plus;
                break;
            case 9:
                magColor = R.color.magnitude9;
                break;
            case 8:
                magColor = R.color.magnitude8;
                break;
            case 7:
                magColor = R.color.magnitude7;
                break;
            case 6:
                magColor = R.color.magnitude6;
                break;
            case 5:
                magColor = R.color.magnitude5;
                break;
            case 4:
                magColor = R.color.magnitude4;
                break;
            case 3:
                magColor = R.color.magnitude3;
                break;
            case 2:
                magColor = R.color.magnitude2;
                break;
            default:
                magColor = R.color.magnitude1;
                break;
        }
        return ContextCompat.getColor(getContext(), magColor);    }

}
