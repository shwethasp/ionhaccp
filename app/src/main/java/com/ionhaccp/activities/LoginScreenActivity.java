package com.ionhaccp.activities;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ionhaccp.R;
import com.ionhaccp.interfaces.ApiInterface;
import com.ionhaccp.model.ModelClass;
import com.ionhaccp.utils.Config;
import com.ionhaccp.utils.Connectivity;

import java.util.Locale;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ionhaccp.utils.Config.login_username;

public class LoginScreenActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mEditUserName, mEditPassword, mEditEmailid, mEditTextForgotPass;
    TextView mForgotPassword, mSignUpText;
    TextInputLayout mTextInputLayoutUserName, mTextInputLayoutPassword, mTextInputLayoutForgotPass;
    Button mLoginBtn, mButtonSend;
    private Dialog mDialogForgotPwd;
    private String shared_UserName, shared_Pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        setLanguage();
        initializeUi();

    }

    private void initializeUi() {

        mEditUserName = (EditText) findViewById(R.id.username);
        mEditPassword = (EditText) findViewById(R.id.password);
        //mEditEmailid = (TextView) findViewById(R.id.emailid);
        mTextInputLayoutUserName = (TextInputLayout) findViewById(R.id.input_layout_username);
        //  mtTextInputLayoutEmailId = (TextInputLayout) findViewById(R.id.input_layout_email);
        mTextInputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        mForgotPassword = (TextView) findViewById(R.id.forgot_password);
        mSignUpText = (TextView) findViewById(R.id.sinup);
        mLoginBtn = (Button) findViewById(R.id.btn_login);
        forgotPasswordDialog();
        mLoginBtn.setOnClickListener(this);
        mSignUpText.setOnClickListener(this);
        mForgotPassword.setOnClickListener(this);
        mEditPassword.setTypeface(Typeface.DEFAULT);
        mEditPassword.setTransformationMethod(new PasswordTransformationMethod());
        mEditUserName.addTextChangedListener(new TextWatcherPresenter(mEditUserName));
        mEditPassword.addTextChangedListener(new TextWatcherPresenter(mEditPassword));
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
        Intent intent = new Intent(this, LoginScreenActivity.class);
        startActivity(intent);
        finish();

    }

    private void forgotPasswordDialog() {
        mDialogForgotPwd = new Dialog(this);
        mDialogForgotPwd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialogForgotPwd.setContentView(R.layout.forgot_password);
        mDialogForgotPwd.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialogForgotPwd.setCancelable(true);
        mTextInputLayoutForgotPass = (TextInputLayout) mDialogForgotPwd.findViewById(R.id.input_layout_forgotpass);
        mEditTextForgotPass = (EditText) mDialogForgotPwd.findViewById(R.id.forgot_password);
        mButtonSend = (Button) mDialogForgotPwd.findViewById(R.id.send_btn);
        mEditTextForgotPass.addTextChangedListener(new TextWatcherPresenter(mEditTextForgotPass));
        mButtonSend.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_login:
                SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF, MODE_PRIVATE);
                shared_UserName = preferences.getString(Config.login_username, "demo");
                shared_Pass = preferences.getString(Config.login_password, "test");
                call_send_Tracker_Message("LoginScreen: Login Button Clicked");
                if (mEditUserName.getText().toString().contentEquals(shared_UserName) && (mEditPassword.getText().toString().contentEquals(shared_Pass))) {
                    if (Connectivity.isConnected(getApplicationContext())) {
                        callLoginApi(mEditUserName.getText().toString(), mEditPassword.getText().toString());

                    } else {
                        Snackbar.make(findViewById(R.id.activity_login_screen), getResources().getString(R.string.internet_connection_msg), Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(findViewById(R.id.activity_login_screen), getResources().getString(R.string.enter_valid_emailid_password), Snackbar.LENGTH_LONG).show();
                }


                break;
            case R.id.forgot_password:

                mDialogForgotPwd.show();
                break;
            case R.id.sinup:
                call_send_Tracker_Message("LoginScreen: SignUp Button Clicked");
                String url = "https://ionhaccp.io/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;

            case R.id.send_btn:
                call_send_Tracker_Message("LoginScreen: Forgot Password Button Clicked");
                if (!mEditTextForgotPass.getText().toString().trim().isEmpty()) {
                    if (Connectivity.isConnected(getApplicationContext())) {
                        callForgotPassAPI();
                    } else {
                        Snackbar.make(findViewById(R.id.activity_login_screen), getResources().getString(R.string.internet_connection_msg), Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(findViewById(R.id.activity_login_screen), getResources().getString(R.string.err_msg_email), Snackbar.LENGTH_LONG).show();
                }
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
                case R.id.username:
                    validateUsername();

                    break;
                case R.id.password:
                    validatePassword();

                    break;

                case R.id.forgot_password:
                    validateForgotPassword();
                    break;
            }
        }

        public void afterTextChanged(Editable editable) {

        }
    }

    private boolean validatePassword() {
        if (mEditPassword.getText().toString().trim().isEmpty()) {
            mTextInputLayoutPassword.setError(getString(R.string.err_msg_password));
           /* requestFocus(inputPassword);*/
            return false;
        } else {
            mTextInputLayoutPassword.setError(null);
            mTextInputLayoutPassword.setErrorEnabled(false);

        }

        return true;
    }

    private boolean validateUsername() {
        if (mEditUserName.getText().toString().trim().isEmpty()) {
            mTextInputLayoutUserName.setError(getString(R.string.err_msg_username));
           /* requestFocus(inputPassword);*/
            return false;
        } else {
            mTextInputLayoutUserName.setError(null);
            mTextInputLayoutUserName.setErrorEnabled(false);

        }

        return true;
    }

    private boolean validateForgotPassword() {
        if (mEditTextForgotPass.getText().toString().trim().isEmpty()) {
            mTextInputLayoutForgotPass.setError(getString(R.string.err_msg_email));
           /* requestFocus(inputPassword);*/
            return false;
        } else {
            mTextInputLayoutForgotPass.setError(null);
            mTextInputLayoutForgotPass.setErrorEnabled(false);

        }

        return true;
    }

    private void callLoginApi(String username, String password) {
        //TODO: remove comment Field
       /* ApiInterface mApiService = this.getInterfaceService();
        LoginRequestModel loginRequest = new LoginRequestModel();
        loginRequest.setUserName(username);
        loginRequest.setPassword(password);
        Call<JsonElement> response = mApiService.login(loginRequest);

        response.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {


                String result = response.body().toString();
                if (response.code() == 200) {
                    Log.e("onResponse", result);
                    if ((result != null) && (!result.isEmpty())) {

                        LoginResponse(true);
                    }
                } else if (response.code() == 401) {
                    LoginResponse(false);

                } else if (response.code() == 403) {
                    LoginResponse(false);
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

                Log.e("onFailure", t.getMessage());
                LoginResponse(true);

            }
        });*/

        //TODO: remove below method LoginResponse()

        Snackbar.make(findViewById(R.id.activity_login_screen), "Login successfull", Snackbar.LENGTH_LONG).show();

        //Start Login Activity
        Intent i = new Intent(LoginScreenActivity.this, DashBoardActivity.class);
        startActivity(i);
    }

    private ApiInterface getInterfaceService() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ModelClass.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ApiInterface mInterfaceService = retrofit.create(ApiInterface.class);
        return mInterfaceService;
    }


    private void callForgotPassAPI() {

 /*ApiInterface mApiService = this.getInterfaceService();
        ForgotPassRequest forgotPassRequest = new ForgotPassRequest();
        forgotPassRequest.setEmailid(forgotpass);

        Call<JsonElement> response = mApiService.forgerPassword(forgotPassRequest);

        response.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {


                String result = response.body().toString();
                if (response.code() == 200) {
                    Log.e("onResponse", result);
                    if ((result != null) && (!result.isEmpty())) {

                        forgotPassStatus(true);
                    }
                } else if (response.code() == 401) {
                    forgotPassStatus(false);

                } else if (response.code() == 403) {
                    forgotPassStatus(false);
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

                Log.e("onFailure", t.getMessage());
                forgotPassStatus(true);

            }
        });*/

        //TODO: remove below method LoginResponse()

    }

    /*
    * Send Action Message to Google Analytics
    * */
    public void call_send_Tracker_Message(String sMessage) {
        com.ionhaccp.utils.GoogleAnalytics googleAnalytics = new com.ionhaccp.utils.GoogleAnalytics(getApplication());
        googleAnalytics.sendTrackerMessage(sMessage);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setLanguage();
    }
}
