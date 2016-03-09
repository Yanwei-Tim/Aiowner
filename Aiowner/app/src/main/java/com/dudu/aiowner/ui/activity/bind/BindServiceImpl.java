package com.dudu.aiowner.ui.activity.bind;

import com.dudu.aiowner.rest.common.Request;
import com.dudu.aiowner.rest.model.BindRequest;
import com.dudu.aiowner.rest.model.BindResponse;

import retrofit2.Callback;

/**
 * Created by Robi on 2016-03-0919:24.
 */
public class BindServiceImpl {
    public static void bind(BindRequest request, Callback<BindResponse> callback) {
        Request.getInstance().getBindService().bind(request).enqueue(callback);
    }

    public static void unbind(BindRequest request, Callback<BindResponse> callback) {
        Request.getInstance().getBindService().unbind(request).enqueue(callback);
    }

    public static void getBindStatus( String phone, String platform, Callback<BindResponse> callback) {
        Request.getInstance().getBindService().getBindStatus(phone, platform).enqueue(callback);
    }
}
