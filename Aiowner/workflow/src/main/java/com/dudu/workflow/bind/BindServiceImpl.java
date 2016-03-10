package com.dudu.workflow.bind;

import android.text.TextUtils;

import com.dudu.aiowner.rest.common.Request;
import com.dudu.aiowner.rest.model.BindRequest;
import com.dudu.aiowner.rest.model.BindResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Robi on 2016-03-0919:24.
 */
public class BindServiceImpl {

    public static void bind(BindRequest request, IBindListener listener) {
        Request.getInstance().getBindService().bind(request).enqueue(new Callback<BindResponse>() {
            @Override
            public void onResponse(Call<BindResponse> call, Response<BindResponse> response) {
                BindResponse body = response.body();
                if (body != null) {
                    listener.onBind(body.resultCode == 0, body.resultMsg);
                } else {
                    listener.onBind(false, "Bind Failed, Please Try Again.");
                }
            }

            @Override
            public void onFailure(Call<BindResponse> call, Throwable t) {
                listener.onFailed(t.toString());
            }
        });
    }

    public static void unbind(BindRequest request, IBindListener listener) {
        Request.getInstance().getBindService().unbind(request).enqueue(new Callback<BindResponse>() {
            @Override
            public void onResponse(Call<BindResponse> call, Response<BindResponse> response) {
                BindResponse body = response.body();
                if (body != null) {
                    listener.onUnBind(body.resultCode == 0, body.resultMsg);
                } else {
                    listener.onUnBind(false, "Unbind Failed, Please Try Again.");
                }
            }

            @Override
            public void onFailure(Call<BindResponse> call, Throwable t) {
                listener.onFailed(t.toString());
            }
        });
    }

    public static void getBindStatus(String phone, String platform, IBindListener listener) {
        Request.getInstance().getBindService().getBindStatus(phone, platform).enqueue(new Callback<BindResponse>() {
            @Override
            public void onResponse(Call<BindResponse> call, Response<BindResponse> response) {
                BindResponse body = response.body();
                if (body != null) {
                    listener.onGetBindStatus(!TextUtils.isEmpty(body.obeId), body.obeId);
                } else {
                    listener.onFailed("Get Bind Status Failed, Please Try Again.");
                }
            }

            @Override
            public void onFailure(Call<BindResponse> call, Throwable t) {
                listener.onFailed(t.toString());
            }
        });
    }
}
