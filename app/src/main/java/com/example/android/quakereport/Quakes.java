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
    private Double mMag;

    // The city in which the earthquake took place
    private String mPlace;

    // The date in format MonthAbbreviation day, year
    private Long mDate;

    // The url where the quake report is found
    private String mUrl;


    /*
     *  CONSTRUCTORS
     */

    /**
     * Constructor for the com.example.android.quakereport.Quakes class.
     * Requires @param mag to assign a magnitude,
     * @param place to assign where the earthquake took place,
     * @param date to assign the day that the earthquake took place, and @param url
     * to assign the url associated with the quake.
     */
    public Quakes(Double mag, String place, Long date, String url) {
        mMag = mag;
        mPlace = place;
        mDate = date;
        mUrl = url;
    }


    /*
     *  GETTERS AND SETTERS
     */

    /**
     *  Gets the magnitude of the earthquake.
     */
    public Double getmMag() { return mMag; }

    /**
     *  Gets the city in which the earthquake took place.
     */
    public String getmPlace() { return mPlace; }

    /**
     *  Gets the date that the earthquake took place.
     */
    public Long getmDate() { return mDate; }

    /**
     *  Gets the url that is associated with the earthquake.
     */
    public String getmUrl() { return mUrl; }

}
