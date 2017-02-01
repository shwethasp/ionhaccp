package com.ionhaccp.fragments;


import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ionhaccp.R;
import com.ionhaccp.activities.DashBoardActivity;
import com.ionhaccp.activities.LoginScreenActivity;
import com.ionhaccp.utils.Config;

import static android.content.Context.MODE_PRIVATE;
import static com.ionhaccp.activities.DashBoardActivity.mToolBarTitle;

/**
 * Created by mehtermu on 27-01-2017.
 */

public class ChangePasswordFragement extends Fragment implements View.OnClickListener {
    private DashBoardActivity activity;
    boolean enableBackNavigation = true;
    EditText mEdtOldPassword, mEdtNewPassword, mEditConformPasseord;
    TextInputLayout mTextInputLayoutOldPassword, mTextInputLayoutNewPassword, mTextInputLayoutConfirmPass;
    Button bntUpdatePass, bntCancle;
    View rootview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = new DashBoardActivity();
        activity.setDrawerState(false);
        rootview = inflater.inflate(R.layout.change_password_fragement, container, false);

        mToolBarTitle.setText(getResources().getString(R.string.settings));

       /* Toolbar toolbar = activity.toolbar;
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_material));*/

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);



        mEdtOldPassword = (EditText) rootview.findViewById(R.id.change_password_old);
        mEdtNewPassword = (EditText) rootview.findViewById(R.id.change_password_new);
        mEditConformPasseord = (EditText) rootview.findViewById(R.id.change_password_new_conform);

        mEdtOldPassword.addTextChangedListener(new TextWatcherPresenter(mEdtOldPassword));
        mEdtNewPassword.addTextChangedListener(new TextWatcherPresenter(mEdtNewPassword));
        mEditConformPasseord.addTextChangedListener(new TextWatcherPresenter(mEditConformPasseord));


        mTextInputLayoutOldPassword = (TextInputLayout) rootview.findViewById(R.id.input_layout_password_old);
        mTextInputLayoutNewPassword = (TextInputLayout) rootview.findViewById(R.id.input_layout_password_new);
        mTextInputLayoutConfirmPass = (TextInputLayout) rootview.findViewById(R.id.input_layout_password_conform);

        bntUpdatePass = (Button) rootview.findViewById(R.id.update_bnt);
        bntCancle = (Button) rootview.findViewById(R.id.cancel_bnt);
        bntUpdatePass.setOnClickListener(this);
        bntCancle.setOnClickListener(this);
        /*if (enableBackNavigation) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.setDrawerState(true);
                    FragmentManager fm = getActivity()
                            .getSupportFragmentManager();
                    fm.popBackStack("Setting", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF, MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString(Config.current_fragment, "");
                    edit.apply();
                    enableBackNavigation = false;
                }
            });
        }
*/
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(Config.current_fragment, "ChangePasswordFragement");
        edit.apply();
        return rootview;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update_bnt:
                SharedPreferences preferences = getActivity().getSharedPreferences(Config.SHARED_PREF, MODE_PRIVATE);
                String mStrOldPassword, mStrNewPassword, mStrConfPassword;
                mStrOldPassword = mEdtOldPassword.getText().toString().trim();
                mStrNewPassword = mEdtNewPassword.getText().toString().trim();
                mStrConfPassword = mEditConformPasseord.getText().toString().trim();

                if (!mStrOldPassword.equals("") && !mStrNewPassword.equals("") && !mStrConfPassword.equals("")) {
                    String mSharedOldPassword = preferences.getString(Config.login_password, "test");
                    if (mStrOldPassword.equals(mSharedOldPassword)) {
                        if (mStrNewPassword.equals(mStrConfPassword)) {

                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString(Config.login_password, mStrNewPassword);
                            editor.apply();

                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF, MODE_PRIVATE);
                            activity.setDrawerState(true);
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            fm.popBackStack("Setting", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.putString(Config.current_fragment, "");
                            edit.apply();
                        } else {
                            Snackbar.make(rootview.findViewById(R.id.change_password_root), getResources().getString(R.string.passwords_do_not_match), Snackbar.LENGTH_LONG).show();
                        }

                    } else {
                        Snackbar.make(rootview.findViewById(R.id.change_password_root), getResources().getString(R.string.wrong_password), Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(rootview.findViewById(R.id.change_password_root), getResources().getString(R.string.valid_passwords), Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.cancel_bnt:
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF, MODE_PRIVATE);
                activity.setDrawerState(true);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("Setting", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString(Config.current_fragment, "");
                edit.apply();
                break;
        }
    }



    public class TextWatcherPresenter implements TextWatcher {

        private View view;

        private TextWatcherPresenter(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            switch (view.getId()) {
                case R.id.change_password_old:
                    validatePassword(mEdtOldPassword, mTextInputLayoutOldPassword);

                    break;
                case R.id.change_password_new:
                    validatePassword(mEdtNewPassword, mTextInputLayoutNewPassword);

                    break;

                case R.id.change_password_new_conform:
                    validatePassword(mEditConformPasseord, mTextInputLayoutConfirmPass);
                    break;
            }
        }

        public void afterTextChanged(Editable editable) {

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validatePassword(EditText mEditTest, TextInputLayout mTextInputLayout) {
        if (mEditTest.getText().toString().trim().isEmpty()) {
            mTextInputLayout.setError(getString(R.string.err_msg_password));
           /* requestFocus(inputPassword);*/
            return false;
        } else {
            mTextInputLayout.setError(null);
            mTextInputLayout.setErrorEnabled(false);

        }

        return true;
    }


}
