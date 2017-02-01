package com.ionhaccp.activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import android.util.Log;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.ionhaccp.R;
import com.ionhaccp.model.ModelClass;
import com.ionhaccp.application.IonhaccpApplication;

public class SplashScreenActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT = 3000;

    public static boolean istrigerred;

    private Tracker mTracker;
    private static final String TAG = "SplashScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        Log.i(TAG, " splash screen started");


        SharedPreferences preferencesPanic = getSharedPreferences(ModelClass.SharedPreferenceName, Context.MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                finish();
                startActivity(new Intent(SplashScreenActivity.this, LoginScreenActivity.class));
                overridePendingTransition(R.anim.activity_slide_left_in,
                        R.anim.activity_slide_left_out);

                Log.i(TAG, "Login in screen intent passed ");
            }
        }, SPLASH_SCREEN_TIME_OUT);


        call_send_Tracker_Message("Screen Name: Splash Screen");


    }

    /*
    * Send Action Message to Google Analytics
    * */
    public void call_send_Tracker_Message(String sMessage) {
        com.ionhaccp.utils.GoogleAnalytics googleAnalytics = new com.ionhaccp.utils.GoogleAnalytics(getApplication());
        googleAnalytics.sendTrackerMessage(sMessage);
    }
}
