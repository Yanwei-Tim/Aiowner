package com.dudu.aiowner.rest.common;

import com.dudu.aiowner.commonlib.xml.ConfigReader;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Eaway on 2016/2/13.
 */
public class RetrofitClient {

    private final Retrofit retrofit;

    public RetrofitClient() {
        String baseUrl = ConfigReader.getInstance().getConfig().getServerAddress();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
//                .client(buildClient())
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
