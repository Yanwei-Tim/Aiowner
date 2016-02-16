package com.dudu.workflow;

import com.dudu.aiowner.commonlib.config.ConfigReader;
import com.dudu.workflow.user.UserFlow;
import com.dudu.workflow.user.UserInfo;

/**
 * Created by Administrator on 2016/2/16.
 */
public class CommonParams {

    private static CommonParams mInstance = new CommonParams();

    private UserInfo userInfo = new UserInfo();

    public static CommonParams getInstance() {
        return mInstance;
    }

    public void init() {
        if (ConfigReader.getInstance().isTest()) {
            userInfo.setUserName("13800138000");
        }else{
            userInfo.setUserName(UserFlow.getUserName());
        }
    }

    public String getUserName(){
        return userInfo.getUserName();
    }
}
