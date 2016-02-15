package com.dudu.workflow;

import com.dudu.aiowner.commonlib.CommonLib;
import com.dudu.workflow.user.UserRequest;
import com.dudu.workflow.user.UserRequestRetrofitImpl;
import com.dudu.workflow.user.UserRequestTestImpl;

/**
 * Created by Administrator on 2016/2/14.
 */
public class RequestFactory {

    private static RequestFactory mInstance = new RequestFactory();

    private static UserRequest userRequest;

    public static RequestFactory getInstance(){
        return mInstance;
    }

    public void init(){
        if (CommonLib.isTest()) {
            userRequest = UserRequestTestImpl.getInstance();
        } else {
            userRequest = UserRequestRetrofitImpl.getInstance();
        }
    }

    public static UserRequest getUserRequest() {
        return userRequest;
    }
}
