package com.dudu.workflow.common;

import com.dudu.aiowner.commonlib.config.ConfigReader;
import com.dudu.workflow.driving.DrivingRequest;
import com.dudu.workflow.driving.DrivingRequestRetrofitImpl;
import com.dudu.workflow.guard.GuardRequest;
import com.dudu.workflow.guard.GuardRequestRetrofitImpl;
import com.dudu.workflow.robbery.RobberyRequest;
import com.dudu.workflow.robbery.RobberyRequestRetrofitImpl;
import com.dudu.workflow.user.UserRequest;
import com.dudu.workflow.user.UserRequestRetrofitImpl;
import com.dudu.workflow.user.UserRequestTestImpl;

/**
 * Created by Administrator on 2016/2/14.
 */
public class RequestFactory {

    private static RequestFactory mInstance = new RequestFactory();

    private static UserRequest userRequest;
    private static RobberyRequest robberyRequest;
    private static GuardRequest guardRequest;
    private static DrivingRequest drivingRequest;

    public static RequestFactory getInstance() {
        return mInstance;
    }

    public void init() {
        if (ConfigReader.getInstance().isTest()) {
            userRequest = UserRequestTestImpl.getInstance();
        } else {
            userRequest = UserRequestRetrofitImpl.getInstance();
        }
        robberyRequest = RobberyRequestRetrofitImpl.getInstance();
        guardRequest = GuardRequestRetrofitImpl.getInstance();
        drivingRequest = DrivingRequestRetrofitImpl.getInstance();
    }

    public static UserRequest getUserRequest() {
        return userRequest;
    }

    public static RobberyRequest getRobberyRequest() {
        return robberyRequest;
    }

    public static GuardRequest getGuardRequest() {
        return guardRequest;
    }

    public static DrivingRequest getDrivingRequest() {
        return drivingRequest;
    }
}
