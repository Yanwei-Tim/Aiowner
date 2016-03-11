package com.dudu.aiowner.rest.service;

import com.dudu.aiowner.rest.model.GuardStateResponse;
import com.dudu.aiowner.rest.model.PutTheftLicenseRequest;
import com.dudu.aiowner.rest.model.PutTheftLicenseResponse;
import com.dudu.aiowner.rest.model.RequestResponse;
import com.dudu.aiowner.rest.model.SetTheftPswRequest;
import com.dudu.aiowner.rest.model.SetTheftPswResponse;
import com.dudu.aiowner.rest.model.SetTheftSwitchResponse;
import com.dudu.aiowner.rest.model.TheftStatusResponse;
import com.dudu.aiowner.rest.model.TheftSwitchRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2016/2/16.
 */
public interface GuardService {

    /**
     * 防盗开关
     *
     * @param cellphone 账号
     * @param on_off    开关
     * @return
     */
    @GET("/external/app/theft/{cellphone}/{value}/android")
    public Call<RequestResponse> guardSwitch(@Path("cellphone") String cellphone, @Path("value") int on_off);

    /**
     * 获取防盗开关状态
     *
     * @param cellphone 账号
     * @return
     */
    @GET("/external/getThelfSwitch/{cellphone}")
    public Call<GuardStateResponse> getGuardState(@Path("cellphone") String cellphone);

//    /**
//     * 获取防盗证件上传的结果
//     *
//     * @return TheftUploadResponse 上传资料返回的方法
//     * @param file
//     * @param phone
//     * @param platform
//     * @param type
//     */
//    @Multipart
//    @POST("/theft/upload")
//    public Call<TheftUploadResponse> getTheftUploadResult(
//            @Part("file\"; name=\"image.png\"") RequestBody file,
//            @Part("phone") String  phone,
//            @Part("platform") String  platform,
//            @Part("type") String  type);

    /**
     * 获取防盗状态
     *
     * @param businessId 产品类别
     * @param platform   平台
     * @return
     */
    @GET("/theft/getStatus/{businessId}/{platform}")
    public Call<TheftStatusResponse> getTheftState(@Path("businessId") String businessId, @Path("platform") String platform);

    /**
     * 设置防盗开关状态
     *
     * @param body
     * @return
     */
    @POST("/theft/setThiefSwitchState")
    public Call<SetTheftSwitchResponse> setThiefSwitchState(@Body TheftSwitchRequest body);

    /**
     * 请求防盗证件认证
     *
     * @param body
     * @return
     */
    @POST("/theft/toDetermineAudit")
    public Call<PutTheftLicenseResponse> getTheftLicenseResult(@Body PutTheftLicenseRequest body);

    /**
     * 设置防盗密码
     *
     * @param body
     * @return
     */
    @POST("/theft/toDetermineAudit")
    public Call<SetTheftPswResponse> setTheftPsw(@Body SetTheftPswRequest body);



}
