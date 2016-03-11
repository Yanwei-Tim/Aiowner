package com.dudu.aiowner.rest.service;

import com.dudu.aiowner.rest.model.GetRobberyStatusResponse;
import com.dudu.aiowner.rest.model.QueryRobberyResponse;
import com.dudu.aiowner.rest.model.RequestResponse;
import com.dudu.aiowner.rest.model.RobberySubSwitchRequest;
import com.dudu.aiowner.rest.model.SetRobberySubSwitchResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2016/2/15.
 */
public interface RobberyService {

    @GET("/external/app/robbery/{cellphone}/{type}/{value}/android")
    public Call<RequestResponse> robberySwitch(@Path("cellphone") String cellphone, @Path("type") int type, @Path("value") int on_off);

    @GET("/external/getALLRobberySwitch/{cellphone}")
    public Call<QueryRobberyResponse> getRobberyState(@Path("cellphone") String cellphone);

    /**
     * 设置防劫子开关
     *
     * @param body
     * @return
     */
    @POST("/robbery/updateState")
    public Call<SetRobberySubSwitchResponse> setRobberySubSwitch(@Body RobberySubSwitchRequest body);

    /**
     * 获取防劫状态
     * @param businessId
     * @param platform
     * @return
     */
    @GET("/robbery/getStatus/{businessId}/{platform}")
    public Call<GetRobberyStatusResponse> getRobberyState(@Path("businessId") String businessId, @Path("platform") String platform);

}
