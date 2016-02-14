package com.dudu.aiowner.rest.service;

import com.dudu.aiowner.rest.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2016/2/13.
 */
public interface LoginService {
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
}

