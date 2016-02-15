package com.dudu.aiowner.rest.service;

import com.dudu.aiowner.rest.model.QueryRobberyResponse;
import com.dudu.aiowner.rest.model.RequestResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2016/2/15.
 */
public interface RobberyService {

    @GET("/external/mirror/robbery/{cellphone}/{type}/{value}")
    public Call<RequestResponse> robberySwitch(@Path("cellphone") String cellphone, @Path("type")int type, @Path("value")int on_off);

    @GET("/external/getALLRobberySwitch/{cellphone}")
    public Call<QueryRobberyResponse> getRobberyState(@Path("cellphone")String cellphone);
}
