package com.dudu.aiowner.rest.common;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Eaway on 2016/2/13.
 */
public class RetrofitClient {

    private static final String BASE_URL = "http://www.github.com/";
    private final Retrofit retrofit;

    public RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(buildClient())
                .build();
    }

    private static OkHttpClient buildClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new LoggingInterceptor())
                .build();
        return client;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

}
