package com.dudu.workflow;

import com.dudu.aiowner.commonlib.CommonLib;
import com.dudu.workflow.user.UserRequest;
import com.dudu.workflow.user.UserRequestRetrofitImpl;
import com.dudu.workflow.user.UserRequestTestImpl;

/**
 * Created by Administrator on 2016/2/14.
 */
public class RequestFactory {

    public static UserRequest getUserRequest() {
        UserRequest userRequest;
        if (CommonLib.isTest()) {
            userRequest = UserRequestTestImpl.getInstance();
        } else {
            userRequest = UserRequestRetrofitImpl.getInstance();
        }
        return userRequest;
    }
}
