package com.dudu.aiowner.rest.common;

import android.util.Log;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.Request;

/**
 * Created by Administrator on 2016/2/14.
 */
public class LoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();
        Log.v("rest", "request:" + request.toString());
        if (request.body() != null) {
            FormBody body = (FormBody) request.body();
            int size = body.size();
            for (int i = 0; i < size; i++) {
                Log.v("rest", body.name(i) + " : " + body.value(i));
            }
        }
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        Log.v("rest", String.format("received " + response.toString() + " in %.1fms%n", (t2 - t1) / 1e6d));
        return response;
    }
}
