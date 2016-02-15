package com.dudu.aiowner.rest.common;

import com.dudu.aiowner.rest.service.LoginService;
import com.dudu.aiowner.rest.service.RegisterService;
import com.dudu.aiowner.rest.service.RobberyService;

/**
 * Created by Administrator on 2016/2/13.
 */
public class Request {
    private static Request mInstance = new Request();

    private static RetrofitClient mClient;

    private LoginService mLoginService;
    private RegisterService mRegisterService;
    private RobberyService mRobberyService;

    public static Request getInstance() {
        return mInstance;
    }

    private Request() {
    }

    public void init(){
        mClient = new RetrofitClient();
        mLoginService = mClient.getRetrofit().create(LoginService.class);
        mRegisterService = mClient.getRetrofit().create(RegisterService.class);
        mRobberyService = mClient.getRetrofit().create(RobberyService.class);
    }

    public LoginService getLoginService() {
        return mLoginService;
    }

    public RegisterService getRegisterService() {
        return mRegisterService;
    }

    public RobberyService getRobberyService() {
        return mRobberyService;
    }
}
