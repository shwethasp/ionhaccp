package com.ionhaccp.fragments;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.aginova.icelsius.ICelsius;
import com.aginova.model.Model;
import com.ionhaccp.R;
import com.ionhaccp.activities.DashBoardActivity;
import com.ionhaccp.asynktask.IcelsiusScanAsynkTask;
import com.ionhaccp.generic.sensor.lib.GenericSensorClass;
import com.ionhaccp.model.WifiSSID;
import com.ionhaccp.utils.Config;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.ionhaccp.activities.DashBoardActivity.mToolBarTitle;

/**
 * Created by anshikas on 27-01-2017.
 */

public class SettingFragment extends Fragment implements View.OnClickListener {
    private LinearLayout changePasswordLayout, changeLanguageLayout;
    private Switch mPushNotificationSwitch;
    private FragmentTransaction fragmentTransaction;
    private Fragment fragment;
    private SharedPreferences preferences;
    private boolean isNotificationEnable;
    private TextView txtSensorStatus;
    private Button infraButton;
    private String mSelectedSensor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.setting_fragment, container,
                false);

        mToolBarTitle.setText(getResources().getString(R.string.settings));

        changeLanguageLayout = (LinearLayout) rootview.findViewById(R.id.change_language_layout);
        changePasswordLayout = (LinearLayout) rootview.findViewById(R.id.change_password_layout);
        mPushNotificationSwitch = (Switch) rootview.findViewById(R.id.push_notification_switch);
        txtSensorStatus = (TextView) rootview.findViewById(R.id.sensorStatus);
        infraButton = (Button) rootview.findViewById(R.id.change_infra);

        changeLanguageLayout.setOnClickListener(this);
        changePasswordLayout.setOnClickListener(this);

        /*
        * Set User Push Notification Enable/Disable Prefference
        * */
        preferences = getActivity().getSharedPreferences(Config.SHARED_PREF, MODE_PRIVATE);
        isNotificationEnable = preferences.getBoolean(Config.isNotificationEnable, true);
        if (isNotificationEnable) {
            mPushNotificationSwitch.setChecked(true);
        } else {
            mPushNotificationSwitch.setChecked(false);
        }


        mPushNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(Config.isNotificationEnable, true);
                    editor.apply();
                } else {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(Config.isNotificationEnable, false);
                    editor.apply();
                }
            }
        });

        /*if (DashBoardActivity.genericSensorClass != null) {
            final IntentFilter sensorIntentFilter = new IntentFilter();
            sensorIntentFilter.addAction(GenericSensorClass.sensorStatusKey);
            getActivity().registerReceiver(genericStatusReciver, sensorIntentFilter);
            DashBoardActivity.genericSensorClass.getSensorStatus();
        } else {

        }*/


        infraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                IcelsiusScanAsynkTask icelsiusScanAsynkTask = new IcelsiusScanAsynkTask() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        progressDialog.show();
                    }

                    @Override
                    protected void onPostExecute(final ArrayList<String> SensorsList) {
                        super.onPostExecute(SensorsList);
                        if (progressDialog.isShowing()) {
                            progressDialog.cancel();
                        }
                        if (SensorsList != null) {

                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_list_item_1, SensorsList) {
                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {
                                    View view = super.getView(position, convertView, parent);
                                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                                    text1.setTextColor(Color.BLACK);
                                    return view;
                                }
                            };


                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                                    getActivity());
                            alertDialog.setTitle(getResources().getString(R.string.select_one_sensor));
                            alertDialog.setAdapter(adapter,
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            mSelectedSensor = SensorsList.get(which);
                                            String mSSIDs = ICelsius.ScanAccessPoints(mSelectedSensor);
                                            Log.e("mSSIDs", mSSIDs);

                                            if (mSSIDs.contains("SSID")) {
                                                showSSIDDialog(mSSIDs);
                                            } else
                                                Toast.makeText(getActivity(), mSSIDs,
                                                        Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            alertDialog.show();
                        } else
                            Toast.makeText(getActivity(),
                                    getResources().getString(R.string.no_sensors_found),
                                    Toast.LENGTH_SHORT).show();
                    }
                };

                icelsiusScanAsynkTask.execute();


            }
        });


        return rootview;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.change_password_layout:
                fragment = new ChangePasswordFragement();
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_placeholder, fragment, "Change Password");
                fragmentTransaction.addToBackStack("Setting");
                fragmentTransaction.commit();
                break;
            case R.id.change_language_layout:
                fragment = new SelectLanguageFragement();
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_placeholder, fragment, "Select Languagea");
                fragmentTransaction.addToBackStack("Setting");
                fragmentTransaction.commit();
                break;
        }
    }

    BroadcastReceiver genericStatusReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            final String action = intent.getAction();
            if (action.equals(GenericSensorClass.sensorStatusKey)) {
                txtSensorStatus.setText(intent.getStringExtra(GenericSensorClass.SENSOR_STATUS));
            }


        }
    };

    protected void showSSIDDialog(String mSSIDs) {
        final List<WifiSSID> mWifiList = new ArrayList<WifiSSID>();

        //Split and save SSID and Security Values
        WifiSSID mWifiModel = null;
        for (String s : mSSIDs.split("&")) {
            if (s.split("=")[0].equals("SSID")) {
                mWifiModel = new WifiSSID();
                mWifiModel.setSSID(s.split("=")[1]);
            } else if (s.split("=")[0].equals("Security")) {
                mWifiModel.setSecurity(s.split("=")[1]);
                mWifiList.add(mWifiModel);
            }
        }

        final ArrayList<String> mSSIDList = new ArrayList<String>();

        for (int i = 0; i < mWifiList.size(); i++) {
            mSSIDList.add(mWifiList.get(i).getSSID());
        }

        if (mSSIDList.size() > 0) {

            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, mSSIDList) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    text1.setTextColor(Color.BLACK);
                    return view;
                }
            };

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    getActivity());
            alertDialog.setTitle("Select SSID:");
            alertDialog.setAdapter(adapter,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(
                                DialogInterface dialog,
                                int which) {
                            Boolean isWPA = false;
                            if (mWifiList.get(which).getSecurity().equals("3"))
                                isWPA = true;

                            showConnectDialog(mSelectedSensor, isWPA, mSSIDList.get(which).toString(), dialog);

                        }
                    });
            alertDialog.show();
        } else
            Toast.makeText(
                    getActivity(),
                    "No connected sensors found, please scan again.",
                    Toast.LENGTH_SHORT).show();

    }

    public void showConnectDialog(final String sensorID, final Boolean isSecurity,
                                  final String mSSID, final DialogInterface mSSIDDialog) {

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog, null);

        final EditText Et = (EditText) view.findViewById(R.id.editText1);

        Et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        if (!isSecurity)
            Et.setVisibility(View.GONE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder
                .setView(view)
                .setTitle("iCelsius")
                .setMessage("Reconfiguring Sensor To Network: " + mSSID)
                .setCancelable(true)
                .setPositiveButton("Proceed",
                        new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog,
                                                int id) {


                                if (isSecurity) {
                                    String strPassword = Et.getText().toString();
                                    if (strPassword.trim().length() > 0) {
                                        String str_change_mode = ICelsius.ChangeToInfraMode(sensorID, mSSID, strPassword, true, "1");
                                        Toast.makeText(getActivity(), str_change_mode,
                                                Toast.LENGTH_SHORT).show();
                                    } else
                                        Toast.makeText(getActivity(), "Please enter the network password!",
                                                Toast.LENGTH_SHORT).show();
                                } else {
                                    String str_change_mode = ICelsius.ChangeToInfraMode(sensorID, mSSID, null, false, "1");
                                    Toast.makeText(getActivity(), str_change_mode, Toast.LENGTH_SHORT).show();
                                }
//                                Connectionformed = false;
//                                recreate();
                                mSSIDDialog.dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}
