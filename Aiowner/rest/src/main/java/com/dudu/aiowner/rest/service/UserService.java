package com.dudu.aiowner.rest.service;

import com.dudu.aiowner.rest.model.LoginResponse;
import com.dudu.aiowner.rest.model.RegisterResponse;
import com.dudu.aiowner.rest.model.RequestResponse;
import com.dudu.aiowner.rest.model.UserInfoResponse;
import com.dudu.aiowner.rest.model.UserLogin;
import com.dudu.aiowner.rest.model.UserRegister;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2016/2/13.
 */
public interface UserService {

    /**
     * 登录
     *
     * @param body 登录请求信息
     * @return LoginResponse 登录返回信息
     */

    @POST("/registered/login")
    public Call<LoginResponse> login(@Body UserLogin body);

    /**
     * 注册
     *
     * @param body 注册请求信息
     * @return RegisterResponse 注册返回信息
     */

    @POST("/registered/app")
    public Call<RegisterResponse> register(@Body UserRegister body);

    /**
     * 发送验证码
     *
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

    /**
     *获取用户信息
     *
     * @param phone
     * @param platform
     * @return
     */
    @GET("/user/info/{phone}/{platform}")
    public Call<UserInfoResponse> getUserInfo(@Path("phone") String phone, @Path("platform") String platform);
}
