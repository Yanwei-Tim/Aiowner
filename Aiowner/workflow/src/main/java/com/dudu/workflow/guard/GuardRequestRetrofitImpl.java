package com.dudu.workflow.guard;

import android.util.Log;

import com.dudu.aiowner.commonlib.commonutils.FileUtils;
import com.dudu.aiowner.rest.common.Request;
import com.dudu.aiowner.rest.model.GuardStateResponse;
import com.dudu.aiowner.rest.model.RequestResponse;
import com.dudu.aiowner.rest.model.TheftStatusResponse;
import com.dudu.aiowner.rest.model.TheftUploadResponse;
import com.dudu.workflow.common.CommonParams;

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

    public static GuardRequestRetrofitImpl getInstance() {
        return mInstance;
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


        File file = new File(FileUtils.getStorageDir(),"aaa.png");

        Log.d("file","-----"+file.getPath()+" is exist: "+file.exists());

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        String phoneString = CommonParams.getInstance().getUserName();

        RequestBody phone = RequestBody.create(MediaType.parse("multipart/form-data"), phoneString);

        RequestBody platform = RequestBody.create(MediaType.parse("multipart/form-data"), "android");

        RequestBody type = RequestBody.create(MediaType.parse("multipart/form-data"), "driving");

        Call<TheftUploadResponse> call = Request.getInstance().getGuardService().getTheftUploadResult(requestBody, phone, platform, type);

        call.enqueue(new Callback<TheftUploadResponse>() {
            @Override
            public void onResponse(Call<TheftUploadResponse> call, Response<TheftUploadResponse> response) {

                Log.d("TheftUploadResponse", response.body().toString());

                callBack.uploadSucceed(response.body().resultCode == 0);
            }

            @Override
            public void onFailure(Call<TheftUploadResponse> call, Throwable t) {
                callBack.uploadError(t.toString());

                Log.d("TheftUploadResponse", "Error---"+t);
            }
        });


//
//        Call<TheftUploadResponse> call = Request.getInstance().getGuardService()
//                .getTheftUploadResult(CommonParams.getInstance().getUserName(), "android", "driving", "method", "messageId");
//        call.enqueue(new Callback<TheftUploadResponse>() {
//            @Override
//            public void onResponse(Call<TheftUploadResponse> call, Response<TheftUploadResponse> response) {
//                callBack.uploadSucceed(response.body().resultCode == 0);
//            }
//
//            @Override
//            public void onFailure(Call<TheftUploadResponse> call, Throwable t) {
//                callBack.uploadError(t.toString());
//            }
//        });
    }
}
