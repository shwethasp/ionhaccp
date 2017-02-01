package com.ionhaccp.fragments;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.BuildConfig;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ionhaccp.R;

import static android.R.attr.versionName;
import static com.ionhaccp.activities.DashBoardActivity.mToolBarTitle;

/**
 * Created by anshikas on 27-01-2017.
 */

public class AboutUsFragment extends Fragment {
    String revision, versionName;
    TextView mTextViewVersionNumber, mTextViewRevisionNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.aboutus_fragment, container,
                false);
        //geting the version name info
        initializeUi(rootview);
        mToolBarTitle.setText(getResources().getString(R.string.about_us));
        PackageManager packageManager = getActivity().getPackageManager();
        String packageName = getActivity().getPackageName();

        try {
            versionName = packageManager.getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        revision = versionName.substring(2, versionName.length());
        mTextViewVersionNumber.setText(versionName);
        mTextViewRevisionNumber.setText(revision);


        return rootview;
    }

    private void initializeUi(View rootview) {

        mTextViewVersionNumber = (TextView) rootview.findViewById(R.id.version);
        mTextViewRevisionNumber = (TextView) rootview.findViewById(R.id.revision_value);

    }
}
