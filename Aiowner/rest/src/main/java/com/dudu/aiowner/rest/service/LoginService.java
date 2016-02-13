package com.dudu.aiowner.rest.service;

import com.dudu.aiowner.rest.model.LoginResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.POST;

/**
 * Created by Administrator on 2016/2/13.
 */
public interface LoginService {
    /**
     * 登录
     * @param cellphone 手机号码
     * @param password 密码
     * @param method 调用方法名
     * @param messageId 消息ID
     * @param callback LoginResponse 登录返回信息
     */
    @POST("/appLogin")
    public void login(@Field("cellphone") String cellphone,
                      @Field("cellphone") String password,
                      @Field("cellphone") String method,
                      @Field("cellphone") String messageId,
                      Callback<LoginResponse> callback);
}

