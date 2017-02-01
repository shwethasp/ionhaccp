package com.ionhaccp.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.ionhaccp.application.IonhaccpApplication;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by mehtermu on 25-01-2017.
 */

public class GoogleAnalytics {
    private Tracker mTracker;
    Application application;

    public GoogleAnalytics(Application application) {
        this.application = application;
    }

    public void sendTrackerMessage(String message) {
        IonhaccpApplication ionhaccpApplication = (IonhaccpApplication) application;
        mTracker = ionhaccpApplication.getDefaultTracker();
        Log.i("Google Analytics", message);
        mTracker.setScreenName(message);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
