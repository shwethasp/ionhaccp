package com.ionhaccp.generic.sensor.lib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;

import com.aginova.icelsius.ICelsius;

import java.util.ArrayList;

import static android.R.attr.delay;
import static com.aginova.icelsius.ICelsius.context;
import static com.aginova.icelsius.ICelsius.scanSensors;

/**
 * Created by mehtermu on 31-01-2017.
 */

public class GenericSensorClass {
    public Context mContext;
    private final String TAG = "GenericSensorClass";
    public static String strSensorStatus = "";
    public static String sensorStatusKey = "sensorStatusKey";
    public static String SENSOR_STATUS = "sensor_status";
    ArrayList<String> SensorsList;

    public GenericSensorClass(Context mContext) {
        this.mContext = mContext;
    }

    public void ConnectSensorDevice() {
        ICelsius.init(mContext);

        mContext.registerReceiver(ionhaccp_iCelsiusReceiver, makeiCelsiusIntentFilter());
        strSensorStatus = "SENSOR DISCONNETED";

    }


    public void DisconnectSensorDevice() {

        ICelsius.disconnect();
        mContext.unregisterReceiver(ionhaccp_iCelsiusReceiver);
        strSensorStatus = "Disconnected";
    }


    private final BroadcastReceiver ionhaccp_iCelsiusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(ICelsius.UDP_BROADCAST)) {
                // STATUS holds Boolean value of connection state of sensors.
                Boolean isConnected = intent.getBooleanExtra(ICelsius.STATUS, false);
                // STATE holds String value of connection state of sensors.
                Log.i(TAG, "Sensor State: " + intent.getStringExtra(ICelsius.STATE));
                strSensorStatus = intent.getStringExtra(ICelsius.STATE);

                getSensorStatus();
                /*SensorsList = ICelsius.scanSensors(5000);*/
               /* Log.i("", "");
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms

                        Log.i("", "");
                    }
                }, 1000);*/

                Log.i("", "");
//                getTemperature();
            } else if (action.equals(ICelsius.UDP_ERROR_BROADCAST))
                // ERROR holds if any error happens in API.
                Log.i(TAG, " Error: " + intent.getStringExtra(ICelsius.ERROR));
        }
    };

    private static IntentFilter makeiCelsiusIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("UDPBroadcast");
        return intentFilter;
    }

    public String getSensorStatus() {
        Intent intent = new Intent();
        intent.setAction(sensorStatusKey);
        intent.putExtra(SENSOR_STATUS, strSensorStatus);
        mContext.sendBroadcast(intent);
        return strSensorStatus;
    }

   /* public String getTemperature() {
        String temp_data = "";
        ArrayList<String> sensorList = ICelsius.scanSensors(1000);
        if (!sensorList.isEmpty()) {
            ICelsius.getSensorInformation(sensorList.get(0));

            temp_data = ICelsius.getTemperature(sensorList.get(0));
            Log.i("Generic Class:",temp_data);
        }

        return temp_data;
    }*/

}
