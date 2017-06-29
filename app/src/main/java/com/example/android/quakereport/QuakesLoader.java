package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Ataeraxia.
 */

public class QuakesLoader extends AsyncTaskLoader<List<Quakes>> {

    private String mUrl;

    public QuakesLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Quakes> loadInBackground() {
        return null;
    }
}
