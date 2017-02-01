package com.ionhaccp.fragments;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ionhaccp.R;
import com.ionhaccp.activities.DashBoardActivity;
import com.ionhaccp.adapters.LanguageSelectAdaptor;
import com.ionhaccp.model.SelectLanguageModel;
import com.ionhaccp.utils.Config;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.ionhaccp.activities.DashBoardActivity.mToolBarTitle;

/**
 * Created by mehtermu on 30-01-2017.
 */

public class SelectLanguageFragement extends Fragment {
    View rootview;
    private ArrayList<SelectLanguageModel> languageArrayList;
    private SharedPreferences sharedPreferences;
    private RecyclerView recyclerView;
    private LanguageSelectAdaptor mAdapter;
    private DashBoardActivity activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.select_language, container, false);
        activity = new DashBoardActivity();
        activity.setDrawerState(false);
        mToolBarTitle.setText(getResources().getString(R.string.settings));

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);


        recyclerView = (RecyclerView) rootview.findViewById(R.id.select_language_switch);

        languageArrayList = new ArrayList<SelectLanguageModel>();
        mAdapter = new LanguageSelectAdaptor(getActivity(), languageArrayList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF, MODE_PRIVATE);
        for (int i = 0; i < getResources().getStringArray(R.array.languages).length; i++) {
            languageArrayList.add(new SelectLanguageModel(getResources().getStringArray(R.array.languages)[i],getResources().getStringArray(R.array.languages_code)[i]));

        }
        mAdapter.notifyDataSetChanged();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(Config.current_fragment, "SelectLanguageFragement");
        edit.apply();

        return rootview;
    }
}
