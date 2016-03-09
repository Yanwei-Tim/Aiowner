package com.dudu.workflow.guard;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.dudu.aiowner.commonlib.CommonLib;
import com.dudu.aiowner.commonlib.commonutils.DataJsonTranslation;
import com.dudu.aiowner.commonlib.commonutils.FileUtils;
import com.dudu.aiowner.rest.common.Request;
import com.dudu.aiowner.rest.model.GuardStateResponse;
import com.dudu.aiowner.rest.model.RequestResponse;
import com.dudu.aiowner.rest.model.TheftStatusResponse;
import com.dudu.aiowner.rest.model.TheftUploadResponse;
import com.dudu.aiowner.rest.model.volley.MultipartRequest;
import com.dudu.aiowner.rest.model.volley.MultipartRequestParams;
import com.dudu.workflow.common.CommonParams;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/2/16.
 */
public class GuardRequestRetrofitImpl implements GuardRequest {

    public static GuardRequestRetrofitImpl mInstance = new GuardRequestRetrofitImpl();

    private Logger logger = LoggerFactory.getLogger("GuardRequestRetrofitImpl");

    private RequestQueue queue;

    private String uploadUrl = "http://192.168.0.177:8888/theft/upload";

    public static GuardRequestRetrofitImpl getInstance() {
        return mInstance;
    }

    public GuardRequestRetrofitImpl() {

        queue = Volley.newRequestQueue(CommonLib.getInstance().getContext());
    }

    @Override
    public void isAntiTheftOpened(final LockStateCallBack callBack) {
        Call<GuardStateResponse> call = Request.getInstance().getGuardService()
                .getGuardState(CommonParams.getInstance().getUserName());
        call.enqueue(new Callback<GuardStateResponse>() {
            @Override
            public void onResponse(Call<GuardStateResponse> call, Response<GuardStateResponse> response) {

//                Log.d("isAntiTheftOpened","switchValue:"+response.body().switchValue);

                callBack.hasLocked(response.body().switchValue == 1);

            }

            @Override
            public void onFailure(Call<GuardStateResponse> call, Throwable t) {
                callBack.requestError(t.toString());
            }
        });
    }

    @Override
    public void lockCar(final LockStateCallBack callBack) {
        Call<RequestResponse> call = Request.getInstance().getGuardService()
                .guardSwitch(CommonParams.getInstance().getUserName(), 1);
        call.enqueue(new Callback<RequestResponse>() {
            @Override
            public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                callBack.hasLocked(response.body().resultCode == 0);
            }

            @Override
            public void onFailure(Call<RequestResponse> call, Throwable t) {
                callBack.requestError(t.toString());
            }
        });
    }

    @Override
    public void unlockCar(final UnlockCallBack callBack) {
        Call<RequestResponse> call = Request.getInstance().getGuardService()
                .guardSwitch(CommonParams.getInstance().getUserName(), 0);
        call.enqueue(new Callback<RequestResponse>() {
            @Override
            public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                callBack.unlocked(response.body().resultCode == 0);
            }

            @Override
            public void onFailure(Call<RequestResponse> call, Throwable t) {
                callBack.requestError(t.toString());
            }
        });
    }

    @Override
    public void getTheftStatus(final TheftStatusCallBack callBack) {
        Call<TheftStatusResponse> call = Request.getInstance().getGuardService()
                .getTheftState(CommonParams.getInstance().getUserName(), "android");
        call.enqueue(new Callback<TheftStatusResponse>() {
            @Override
            public void onResponse(Call<TheftStatusResponse> call, Response<TheftStatusResponse> response) {
                callBack.getTheftStatus(response.body());
            }

            @Override
            public void onFailure(Call<TheftStatusResponse> call, Throwable t) {
                callBack.requestError(t.toString());
            }
        });
    }

    @Override
    public void getTheftUploadResult(final UploadLicenceCallBack callBack) {

        File file = new File(FileUtils.getStorageDir(), "aaa.png");

        Log.d("file", "-----" + file.getPath() + " is exist: " + file.exists());
        logger.debug("-----" + file.getPath() + " is exist: " + file.exists());

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartRequestParams multiPartParams = new MultipartRequestParams();

        multiPartParams.put("file", file, file.getName());
        multiPartParams.put("phone", CommonParams.getInstance().getUserName());
        multiPartParams.put("platform", "android");
        multiPartParams.put("type", "driving");

        MultipartRequest multipartRequest = new MultipartRequest(com.android.volley.Request.Method.POST, multiPartParams, uploadUrl,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        TheftUploadResponse response1 = (TheftUploadResponse) DataJsonTranslation.jsonToObject(response, TheftUploadResponse.class);
                        callBack.uploadSucceed(response1.resultCode==0);

                        logger.debug(response);
                        logger.debug("请求完成");
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                callBack.uploadError(error.toString());
                logger.error("请求出错:", error);
            }
        });
        queue.add(multipartRequest);

    }
}
