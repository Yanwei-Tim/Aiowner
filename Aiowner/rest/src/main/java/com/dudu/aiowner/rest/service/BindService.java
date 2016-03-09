package com.dudu.aiowner.rest.service;

import com.dudu.aiowner.rest.model.BindRequest;
import com.dudu.aiowner.rest.model.BindResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Robi on 2016-03-0919:06.
 */
public interface BindService {

    /*
    * {"resultCode":0,"resultMsg":"成功"}
                40025          用户已经绑定过
                40024          obe已经被使用
                40017          设备编码不存在
    * */

    /**绑定设备*/
    @POST("/binding")
    Call<BindResponse> bind(@Body BindRequest body);

    /**解除绑定设备*/
    @POST("/binding/un")
    Call<BindResponse> unbind(@Body BindRequest body);

    /**解除绑定设备*/
    @GET("/binding/getStatus/{phone}/{platform}")
    Call<BindResponse> getBindStatus(@Path("phone") String phone, @Path("platform") String platform);

}
