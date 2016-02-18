package com.dudu.workflow;

import com.dudu.workflow.user.UserInfo;

import rx.functions.Action1;

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
        DataFactory.getUserDataFlow().getUserName()
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        userInfo.setUserName(s);
                    }
                });
    }

    public String getUserName() {
        return userInfo.getUserName();
    }

    public void setUserName(String userName) {
        userInfo.setUserName(userName);
        DataFactory.getUserDataFlow().saveUserName(userName);
    }
}
