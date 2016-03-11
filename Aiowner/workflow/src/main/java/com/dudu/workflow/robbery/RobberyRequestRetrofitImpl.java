package com.dudu.workflow.robbery;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.dudu.aiowner.commonlib.CommonLib;
import com.dudu.aiowner.commonlib.commonutils.DataJsonTranslation;
import com.dudu.aiowner.commonlib.commonutils.FileUtils;
import com.dudu.aiowner.rest.common.Request;
import com.dudu.aiowner.rest.model.QueryRobberyResponse;
import com.dudu.aiowner.rest.model.RequestResponse;
import com.dudu.aiowner.rest.model.RobberySubSwitchRequest;
import com.dudu.aiowner.rest.model.RobberyUploadResponse;
import com.dudu.aiowner.rest.model.SetRobberySubSwitchResponse;
import com.dudu.aiowner.rest.model.volley.MultipartRequest;
import com.dudu.aiowner.rest.model.volley.MultipartRequestParams;
import com.dudu.workflow.common.CommonParams;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/2/15.
 */
public class RobberyRequestRetrofitImpl implements RobberyRequest {

    private String uploadUrl = "http://192.168.0.177:8888/robbery/upload";

    private RequestQueue queue;

    public static RobberyRequestRetrofitImpl mInstance = new RobberyRequestRetrofitImpl();

    public static RobberyRequestRetrofitImpl getInstance() {
        return mInstance;
    }

    public RobberyRequestRetrofitImpl() {
        queue = Volley.newRequestQueue(CommonLib.getInstance().getContext());
    }

    @Override
    public void getCarInsuranceAuthState() {

    }

    @Override
    public void requestCarInsuranceAuth() {

    }

    @Override
    public void isCarRobbed(final CarRobberdCallback callback) {
        Call<QueryRobberyResponse> call = Request.getInstance().getRobberyService()
                .getRobberyState(CommonParams.getInstance().getUserName());
        call.enqueue(new Callback<QueryRobberyResponse>() {
            @Override
            public void onResponse(Call<QueryRobberyResponse> call, Response<QueryRobberyResponse> response) {
                callback.hasRobbed(response.body().switch0 == 1);
            }

            @Override
            public void onFailure(Call<QueryRobberyResponse> call, Throwable t) {
                callback.requestError(t.toString());
            }
        });
    }


    @Override
    public void settingAntiRobberyMode(int type, int on_off, final SwitchCallback callback) {
        Call<RequestResponse> call = Request.getInstance().getRobberyService()
                .robberySwitch(CommonParams.getInstance().getUserName()
                        , type, on_off);
        call.enqueue(new Callback<RequestResponse>() {
            @Override
            public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                callback.switchSuccess(response.body().resultCode == 0);
            }

            @Override
            public void onFailure(Call<RequestResponse> call, Throwable t) {
                callback.requestError(t.getMessage().toString());
            }
        });
    }

    @Override
    public void closeAntiRobberyMode(final CloseRobberyModeCallback callback) {
        Call<RequestResponse> call = Request.getInstance().getRobberyService()
                .robberySwitch(CommonParams.getInstance().getUserName(), 0, 0);
        call.enqueue(new Callback<RequestResponse>() {
            @Override
            public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                callback.closeSuccess(response.body().resultCode == 0);
            }

            @Override
            public void onFailure(Call<RequestResponse> call, Throwable t) {
                callback.requestError(t.getMessage().toString());
            }
        });
    }

    @Override
    public void getRobberyState(final RobberStateCallback callback) {
        Call<QueryRobberyResponse> call = Request.getInstance().getRobberyService()
                .getRobberyState(CommonParams.getInstance().getUserName());
        call.enqueue(new Callback<QueryRobberyResponse>() {
            @Override
            public void onResponse(Call<QueryRobberyResponse> call, Response<QueryRobberyResponse> response) {
                callback.switchsState(response.body().switch1 == 1, response.body().switch2 == 1, response.body().switch3 == 1);
            }

            @Override
            public void onFailure(Call<QueryRobberyResponse> call, Throwable t) {
                callback.requestError(t.toString());
            }
        });
    }

    /**
     * 设置防劫子开关
     *
     * @param robberySwitchs
     * @param callBack
     */
    @Override
    public void setRooberySubSwitch(int[] robberySwitchs, RooberySubSwitchCallBack callBack) {
        Call<SetRobberySubSwitchResponse> call = Request.getInstance().getRobberyService()
                .setRobberySubSwitch(new RobberySubSwitchRequest(CommonParams.getInstance().getUserName(), "android", robberySwitchs));
        call.enqueue(new Callback<SetRobberySubSwitchResponse>() {
            @Override
            public void onResponse(Call<SetRobberySubSwitchResponse> call, Response<SetRobberySubSwitchResponse> response) {

                Log.d("setRooberySubSwitchResponse", "--------" + response.body().resultCode);
                callBack.switchSuccess(response.body().resultCode == 200);
            }

            @Override
            public void onFailure(Call<SetRobberySubSwitchResponse> call, Throwable t) {
                Log.d("setRooberySubSwitchFailure", "------" + t);
                callBack.requestError(t.toString());
            }
        });
    }

    /**
     * 获取防劫开关状态
     */
    //TODO


    /**
     * 获取防劫证件上传结果
     *
     * @param callBack
     */
    @Override
    public void getRobberyUploadResult(final RobberyRequest.UploadLicenceCallBack callBack) {

        File file = new File(FileUtils.getStorageDir(), "bbb.png");

        Log.d("file", "-----" + file.getPath() + " is exist: " + file.exists());

        MultipartRequestParams multiPartParams = new MultipartRequestParams();

        multiPartParams.put("file", file, file.getName());
        multiPartParams.put("phone", CommonParams.getInstance().getUserName());
        multiPartParams.put("platform", "android");

        MultipartRequest multipartRequest = new MultipartRequest(com.android.volley.Request.Method.POST, multiPartParams, uploadUrl,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        RobberyUploadResponse response1 = (RobberyUploadResponse) DataJsonTranslation.jsonToObject(response, RobberyUploadResponse.class);

                        Log.d("RobberyUploadResponse", "--------上传成功");
                        callBack.uploadSucceed(response1.resultCode == 0);
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                callBack.uploadError(error.toString());
                Log.d("RobberyUploadError", "--------上传失败");
            }
        });
        queue.add(multipartRequest);
    }
}
