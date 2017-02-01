package com.ionhaccp.utils;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.google.gson.JsonElement;
import com.ionhaccp.R;
import com.ionhaccp.activities.DashBoardActivity;
import com.ionhaccp.activities.LoginScreenActivity;
import com.ionhaccp.interfaces.ApiInterface;
import com.ionhaccp.model.LoginRequestModel;
import com.ionhaccp.model.ModelClass;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ionhaccp.R.id.username;

/**
 * Created by anshikas on 27-01-2017.
 */

public class ImageCachingUsingRetrofit  {

    //TODO: remove comment Field
     /*   ApiInterface mApiService = this.getInterfaceService();
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
        });

    //TODO: remove below method LoginResponse()

    Snackbar.make(findViewById(R.id.activity_login_screen), "Login successfull", Snackbar.LENGTH_LONG).show();

    //Start Login Activity
    Intent i=new Intent(LoginScreenActivity.this,DashBoardActivity.class);
    startActivity(i);
}*/





    private ApiInterface getInterfaceService() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ModelClass.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ApiInterface mInterfaceService = retrofit.create(ApiInterface.class);
        return mInterfaceService;
    }
}
