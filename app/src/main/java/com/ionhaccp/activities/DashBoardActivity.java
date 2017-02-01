package com.ionhaccp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ionhaccp.R;
import com.ionhaccp.fragments.AboutUsFragment;
import com.ionhaccp.fragments.DashboardFragment;
import com.ionhaccp.fragments.HelpFragment;
import com.ionhaccp.fragments.PrivacyPolicyFragment;
import com.ionhaccp.fragments.SettingFragment;
import com.ionhaccp.fragments.TermsAndConditionFragment;
import com.ionhaccp.generic.sensor.lib.GenericSensorClass;
import com.ionhaccp.utils.AWS_Class;
import com.ionhaccp.utils.Config;

import java.util.ArrayList;
import java.util.Locale;

public class DashBoardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    int existCount;

    private int lastposition;
    private FragmentTransaction fragmentTransaction;
    Fragment fragment, currentFragment;
    public static TextView mToolBarTitle;
    public static Toolbar toolbar;
    public static ActionBarDrawerToggle toggle;
    public static DrawerLayout drawer;
    public static GenericSensorClass genericSensorClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        setLanguage();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolBarTitle = (TextView) findViewById(R.id.toolbartext);
        // toolbar.setText("Pickup Point");
        mToolBarTitle.setText(getResources().getString(R.string.title_activity_dashboard));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //taking the total number of fragment


        //Adding the the fragment
        fragment = new DashboardFragment();
        fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.add(R.id.fragment_placeholder,
                fragment, "Dashboard");

        fragmentTransaction.commit();

        Log.i("currentFragment", "Fragment transaction complete for the first time");


        /*
        * Connect To Sensor.
        * */
        connectToSensor(getApplicationContext());

//        awsCognitoService();

    }

    public void awsCognitoService() {
        AWS_Class awsClass = new AWS_Class(getApplicationContext());
    }

    public void connectToSensor(Context mContext) {
        genericSensorClass = new GenericSensorClass(mContext);
        genericSensorClass.ConnectSensorDevice();
        Log.i("", "");
    }

    private void setLanguage() {
        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF, MODE_PRIVATE);
        String lang = preferences.getString(Config.selected_language, "en");
        Locale current = getResources().getConfiguration().locale;

        if (!current.toString().equals(lang)) {
            setLocale(lang);
        }
    }

    public void setLocale(String lang) {
        Log.i("", "");
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent intent = new Intent(this, DashBoardActivity.class);
        startActivity(intent);
        finish();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int count = getFragmentManager().getBackStackEntryCount();

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF, MODE_PRIVATE);
        String fragment_name = sharedPreferences.getString(Config.current_fragment, "");
        if (!fragment_name.equals("")) {
            setDrawerState(true);
            FragmentManager fm = getSupportFragmentManager();
            fm.popBackStack("Setting", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(Config.current_fragment, "");
            edit.apply();
        } else {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
            /*super.onBackPressed();*/
                currentFragment = getSupportFragmentManager().findFragmentByTag("Dashboard");
                if ((currentFragment instanceof DashboardFragment)) {

                    existCount = existCount + 1;
                    if (existCount == 2) {
                        finish();
                    } else {
                        Snackbar.make(findViewById(R.id.activity_main), getResources().getString(R.string.press_bach_exit), Snackbar.LENGTH_LONG).show();
                    }


                    Handler handler = new Handler();
                    Runnable run = new Runnable() {
                        @Override
                        public void run() {
                            existCount = 0;

                        }
                    };
                    handler.removeCallbacks(run);
                    handler.postDelayed(run, 5000);


                } else {
                    drawer.closeDrawer(GravityCompat.START);
                    fragment = new DashboardFragment();
                    fragmentTransaction = getSupportFragmentManager()
                            .beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_placeholder,
                            fragment, "Dashboard");

                    fragmentTransaction.commit();
                }
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_aboutus) {
            fragment = new AboutUsFragment();
            fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction.replace(R.id.fragment_placeholder,
                    fragment, "About us");
           /* fragmentTransaction.addToBackStack(null);*/
            fragmentTransaction.commit();

        } else if (id == R.id.nav_dashboard) {

            fragment = new DashboardFragment();
            fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction.replace(R.id.fragment_placeholder,
                    fragment, "Dashboard");
            /*fragmentTransaction.addToBackStack(null);*/
            fragmentTransaction.commit();


        } else if (id == R.id.nav_setting) {
            Bundle bundle = new Bundle();

            fragment = new SettingFragment();
            fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction.replace(R.id.fragment_placeholder,
                    fragment, "Setting");
            /*fragmentTransaction.addToBackStack(null);*/
            fragmentTransaction.commit();


        } else if (id == R.id.nav_term_conditions) {

            fragment = new TermsAndConditionFragment();
            fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction.replace(R.id.fragment_placeholder,
                    fragment, "Term andconditions");
            /*fragmentTransaction.addToBackStack(null);*/
            fragmentTransaction.commit();

        } else if (id == R.id.nav_help) {
            fragment = new HelpFragment();
            fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction.replace(R.id.fragment_placeholder,
                    fragment, "Help");
           /* fragmentTransaction.addToBackStack(null);*/
            fragmentTransaction.commit();

        } else if (id == R.id.nav_signout) {

            finish();
        } else if (id == R.id.nav_private_policy) {
            fragment = new PrivacyPolicyFragment();
            fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction.replace(R.id.fragment_placeholder,
                    fragment);
           /* fragmentTransaction.addToBackStack(null);*/
            fragmentTransaction.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setDrawerState(boolean isEnabled) {
        if (isEnabled) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//            toggle.onDrawerStateChanged(DrawerLayout.);
            toggle.setDrawerIndicatorEnabled(true);
            toggle.syncState();

        } else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//            toggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toggle.setDrawerIndicatorEnabled(false);
            toggle.syncState();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setLanguage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (genericSensorClass != null) {
            genericSensorClass.DisconnectSensorDevice();
        }
    }
}


