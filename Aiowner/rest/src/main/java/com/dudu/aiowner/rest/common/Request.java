package com.dudu.aiowner.rest.common;

import com.dudu.aiowner.rest.service.BindService;
import com.dudu.aiowner.rest.service.DrivingService;
import com.dudu.aiowner.rest.service.GuardService;
import com.dudu.aiowner.rest.service.RobberyService;
import com.dudu.aiowner.rest.service.UserService;

/**
 * Created by Administrator on 2016/2/13.
 */
public class Request {
    private static Request mInstance = new Request();

    private static RetrofitClient mClient;

    private UserService mUserService;
    private RobberyService mRobberyService;
    private GuardService mGuardService;
    private DrivingService mDrivingService;
    private BindService bindService;

    public static Request getInstance() {
        return mInstance;
    }

    private Request() {
    }

    public void init(){
        mClient = new RetrofitClient();
        mUserService = mClient.getRetrofit().create(UserService.class);
        mRobberyService = mClient.getRetrofit().create(RobberyService.class);
        mGuardService = mClient.getRetrofit().create(GuardService.class);
        mDrivingService = mClient.getRetrofit().create(DrivingService.class);
        bindService = mClient.getRetrofit().create(BindService.class);
    }

    public UserService getUserService() {
        return mUserService;
    }

    public RobberyService getRobberyService() {
        return mRobberyService;
    }

    public GuardService getGuardService() {
        return mGuardService;
    }

    public DrivingService getDrivingService() {
        return mDrivingService;
    }

    public BindService getBindService() {
        return bindService;
    }
}
