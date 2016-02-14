package com.dudu.aiowner.rest.common;

import com.dudu.aiowner.rest.service.LoginService;
import com.dudu.aiowner.rest.service.RegisterService;

/**
 * Created by Administrator on 2016/2/13.
 */
public class Request {
    private static Request mInstance = new Request();

    private static RetrofitClient mClient;

    private LoginService mLoginService;

    private RegisterService mRegisterService;

    public static Request getInstance() {
        return mInstance;
    }

    private Request() {
        mClient = new RetrofitClient();
        mLoginService = mClient.getRetrofit().create(LoginService.class);
        mRegisterService = mClient.getRetrofit().create(RegisterService.class);
    }

    public LoginService getLoginService() {
        return mLoginService;
    }

    public RegisterService getRegisterService() {
        return mRegisterService;
    }
}
