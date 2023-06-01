package com.if4a.cashflow.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServer {

    private static final String BASE_URL ="https://kulinervincent.000webhostapp.com/";

    private static Retrofit retro;

    public static Retrofit konekRetrofit() {
        if (retro == null){
            retro = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retro;
    }
}
