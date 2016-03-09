package com.dudu.aiowner.rest.service;

import com.dudu.aiowner.rest.model.GuardStateResponse;
import com.dudu.aiowner.rest.model.RequestResponse;
import com.dudu.aiowner.rest.model.TheftStatusResponse;
import com.dudu.aiowner.rest.model.TheftUploadResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
     * 获取防盗证件上传的结果
     *
     * @return TheftUploadResponse 上传资料返回的方法
     * @param file
     * @param phone
     * @param platform
     * @param type
     */
    @Multipart
    @POST("/theft/upload")
    public Call<TheftUploadResponse> getTheftUploadResult(
            @Part("file\"; name=\"image.png\"") RequestBody file,
            @Part("phone") String  phone,
            @Part("platform") String  platform,
            @Part("type") String  type);


//    /**
//     * 获取防盗证件上传的结果
//     *
//     * @return TheftUploadResponse 上传资料返回的方法
//     */
//    @FormUrlEncoded
//    @POST("/theft/upload")
//    public Call<TheftUploadResponse> getTheftUploadResult(@Field("cellphone") String cellphone,
//                                                          @Field("platform") String platform,
//                                                          @Field("type") String type,
//                                                          @Field("method") String method,
//                                                          @Field("messageId") String messageId);

//    @POST("/theft/setProtectThiefPassword")
//    public Call<SetTheftPswResponse> setTheftPsw
}
