package com.example.android.quakereport;

/**
 * Created by Jessica on 2017-05-12.
 * com.example.android.quakereport.Quakes is a class that stores the magnitude, location and date of an earthquake.
 */

public class Quakes {

    /*
     *  VARIABLES
     */

    // The magnitude of the earthquake (must be a String since we haven't learnt floats
    //     and it is a decimal)
    private String mMag;

    // The city in which the earthquake took place
    private String mPlace;

    // The date in format MonthAbbreviation day, year
    private Long mDate;


    /*
     *  CONSTRUCTORS
     */

    /**
     * Constructor for the com.example.android.quakereport.Quakes class.
     * Requires @param mag to assign a magnitude,
     * @param place to assign where the earthquake took place,
     * and @param date to assign the day that the earthquake took place.
     */
    public Quakes(String mag, String place, Long date) {
        mMag = mag;
        mPlace = place;
        mDate = date;
    }


    /*
     *  GETTERS AND SETTERS
     */

    /**
     *  Gets the magnitude of the earthquake.
     */
    public String getmMag() { return mMag; }

    /**
     *  Gets the city in which the earthquake took place.
     */
    public String getmPlace() { return mPlace; }

    /**
     *  Gets the date that the earthquake took place.
     */
    public Long getmDate() { return mDate; }

}
