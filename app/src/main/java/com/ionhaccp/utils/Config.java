package com.ionhaccp.utils;

/**
 * Created by Ravi Tamada on 28/09/16.
 * www.androidhive.info
 */

public class Config {

    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static String isNotificationEnable = "isNotificationEnable";

    public static final String SHARED_PREF = "ah_firebase";

    //BackPressed prefference
    public static final String current_fragment = "current_fragment";

    //Login prefference
    public static final String login_username = "login_username";
    public static final String login_password = "login_password";

    //Language Screen
    public static final String selected_language = "selected_language";


}
