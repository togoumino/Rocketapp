package com.example.sanji.aninterface.rest;

import com.example.sanji.aninterface.ConfigBasic;
import com.example.sanji.aninterface.response.PublicKey;
import com.example.sanji.aninterface.response.RocketLayout;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestApi {
    private RestAPIInterface restAPI;
    public RestApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + ConfigBasic.IP_ADDRESS + ":" + ConfigBasic.REST_API_PORT + "/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        restAPI = retrofit.create(RestAPIInterface.class);
    }
    public Observable<PublicKey> getPublicKey() {
        return restAPI.getPublicKey();
    }
    public Observable<Boolean> login(String body) {
        return restAPI.login(body);
    }
    public Observable<ConfigBasic> getBasicConfig() {
        return restAPI.getBasicConfig();
    }
    public Observable<RocketLayout> getRocketLayout(String layoutName) {
        return restAPI.getRocketLayout(layoutName);
    }
}
