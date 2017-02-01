package com.ionhaccp.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Config;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by mehtermu on 27-01-2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        storeRegIdInPref(refreshedToken);
        sendRegistrationToServer(refreshedToken);
        subscribeToTopic();
    }

    private void subscribeToTopic() {

        FirebaseMessaging.getInstance().subscribeToTopic(com.ionhaccp.utils.Config.TOPIC_GLOBAL);
    }


    /*
    * Send Token to 3rd party server.
    * */
    public void sendRegistrationToServer(String sToken) {
    }

    /*
    * Store generated token in shared preferences.
    * */
    private void storeRegIdInPref(String sToken) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(com.ionhaccp.utils.Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", sToken);
        editor.apply();
    }
}
