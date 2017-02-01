package com.ionhaccp.asynktask;

import android.os.AsyncTask;

import com.aginova.icelsius.ICelsius;

import java.util.ArrayList;

/**
 * Created by mehtermu on 31-01-2017.
 */

public class IcelsiusScanAsynkTask extends AsyncTask<String, Integer, ArrayList<String>> {
    @Override
    protected ArrayList doInBackground(String... strings) {
        ArrayList<String> SensorsList;
        try {
            SensorsList = ICelsius.scanSensors(10000);
            return SensorsList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
