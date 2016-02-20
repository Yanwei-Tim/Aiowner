package com.dudu.workflow.driving;

import com.dudu.aiowner.rest.common.Request;
import com.dudu.aiowner.rest.model.RequestResponse;
import com.dudu.workflow.common.CommonParams;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/2/17.
 */
public class DrivingRequestRetrofitImpl implements DrivingRequest {

    private static final String TAG = "DrivingRequestRetrofitImpl";

    public static DrivingRequestRetrofitImpl mInstance = new DrivingRequestRetrofitImpl();

    public static DrivingRequestRetrofitImpl getInstance() {
        return mInstance;
    }
    
    @Override
    public void startacceleratedTesting(int type, final RequestCallback callback) {
        Call<RequestResponse> call = Request.getInstance().getDrivingService()
                .startAcceleratedTesting(CommonParams.getInstance().getUser().getUserName(), type);
        call.enqueue(new Callback<RequestResponse>() {
            @Override
            public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                callback.requestSuccess(response.body().resultCode == 0);
            }

            @Override
            public void onFailure(Call<RequestResponse> call, Throwable t) {
                callback.requestSuccess(false);
            }
        });
    }
}
