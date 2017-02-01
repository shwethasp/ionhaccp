package com.ionhaccp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ionhaccp.R;

import static com.ionhaccp.activities.DashBoardActivity.mToolBarTitle;

/**
 * Created by anshikas on 27-01-2017.
 */

public class HelpFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.help_fragment, container,
                false);
        mToolBarTitle.setText(getResources().getString(R.string.help));

        return rootview;
    }
}
