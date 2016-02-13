package com.dudu.aiowner.rest.service;

import com.dudu.aiowner.rest.model.RegisterResponse;
import com.dudu.aiowner.rest.model.RequestResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.POST;

/**
 * Created by Administrator on 2016/2/13.
 */
public interface RegisterService {

    /**
     * 注册
     *
     * @param cellphone    手机号码
     * @param securityCode 验证码
     * @param password     密码
     * @param method       调用方法名
     * @param messageId    消息ID
     */
    @POST("/registerFromAPP")
    public void registerWithPhone(@Field("cellphone") String cellphone,
                                  @Field("securityCode") String securityCode,
                                  @Field("password") String password,
                                  @Field("method") String method,
                                  @Field("messageId") String messageId,
                                  Callback<RegisterResponse> callback);

    /**
     * @param cellphone 手机号码
     * @param method    调用方法名
     * @param messageId 消息ID
     * @param type      业务类型（register 验证码; resetPwd 重置密码）
     * @param callback
     */
    @POST("/sendSecurityCode")
    public void getSecurityCode(@Field("cellphone") String cellphone,
                                @Field("cellphone") String method,
                                @Field("cellphone") String messageId,
                                @Field("cellphone") String type,
                                Callback<RequestResponse> callback);
}
