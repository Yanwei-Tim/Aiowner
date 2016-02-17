package com.dudu.aiowner.rest.service;

import com.dudu.aiowner.rest.model.RequestResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2016/2/17.
 */
public interface DrivingService {

    /**
     * 加速测试
     * @param cellphone 账号
     * @param type 测速类型(1.100km/h;2.200km/h;3.300km/h)
     * @return
     */
    @GET("/external/app/acceleratedTestStart/{cellphone}/{type}/android")
    public Call<RequestResponse> startAcceleratedTesting(@Path("cellphone") String cellphone, @Path("type")int type);
}
