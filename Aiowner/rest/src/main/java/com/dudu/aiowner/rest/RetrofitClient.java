package com.dudu.aiowner.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Eaway on 2016/2/13.
 */
public class RetrofitClient {

    private static final String BASE_URL = "http://www.github.com/";

    private final RestAdapter mRestAdapter;

    public RetrofitClient() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
                .create();

        mRestAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();

    }

    public RestAdapter getRestAdapter() {
        return mRestAdapter;
    }

}
