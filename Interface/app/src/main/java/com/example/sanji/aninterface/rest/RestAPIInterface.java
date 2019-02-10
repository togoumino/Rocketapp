package com.example.sanji.aninterface.rest;

import com.example.sanji.aninterface.ConfigBasic;
import com.example.sanji.aninterface.response.PublicKey;
import com.example.sanji.aninterface.response.RocketLayout;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
public interface RestAPIInterface {
    @GET("users/key")
    Observable<PublicKey> getPublicKey();
    @Headers("Content-Type: application/json")
    @POST("users/login")
    Observable<Boolean> login(@Body String body);
    @GET("config/basic")
    Observable<ConfigBasic> getBasicConfig();
    @GET("config/rockets/{layout_name}")
    Observable<RocketLayout> getRocketLayout(@Path(value = "layout_name", encoded = true) String layout_name);
}