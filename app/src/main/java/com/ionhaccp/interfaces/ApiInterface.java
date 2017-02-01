package com.ionhaccp.interfaces;

import com.google.gson.JsonElement;
import com.ionhaccp.model.ForgotPassRequestModel;
import com.ionhaccp.model.LoginRequestModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by anshikas on 18-01-2017.
 */

public interface ApiInterface {

    @Headers({"Accept:  application/json",
            "Content-Type:  application/json"}
    )
    @POST("/api/account/authenticate")
    Call<JsonElement> login(@Body LoginRequestModel loginRequest);


    @Headers({"Accept:  application/json",
            "Content-Type:  application/json"}
    )
    @POST("/api/account/forgotpassword")
    Call<JsonElement> forgerPassword(@Body ForgotPassRequestModel forgotPassRequest);
}
