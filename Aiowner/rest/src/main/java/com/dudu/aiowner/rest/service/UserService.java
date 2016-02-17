package com.dudu.aiowner.rest.service;

import com.dudu.aiowner.rest.model.LoginResponse;
import com.dudu.aiowner.rest.model.RegisterResponse;
import com.dudu.aiowner.rest.model.RequestResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2016/2/13.
 */
public interface UserService {

    /**
     * 登录
     *
     * @param cellphone 手机号码
     * @param password  密码
     * @param method    调用方法名
     * @param messageId 消息ID
     * @return LoginResponse 登录返回信息
     *
     */
    @FormUrlEncoded
    @POST("/appLogin")
    public Call<LoginResponse> login(@Field("cellphone") String cellphone,
                                     @Field("cellphone") String password,
                                     @Field("cellphone") String method,
                                     @Field("cellphone") String messageId);

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
    public Call<RegisterResponse> registerWithPhone(@Field("cellphone") String cellphone,
                                                    @Field("securityCode") String securityCode,
                                                    @Field("password") String password,
                                                    @Field("method") String method,
                                                    @Field("messageId") String messageId);

    /**
     * @param cellphone 手机号码
     * @param method    调用方法名
     * @param messageId 消息ID
     * @param type      业务类型（register 验证码; resetPwd 重置密码）
     */
    @POST("/sendSecurityCode")
    public Call<RequestResponse> getSecurityCode(@Field("cellphone") String cellphone,
                                                 @Field("cellphone") String method,
                                                 @Field("cellphone") String messageId,
                                                 @Field("cellphone") String type);
}
