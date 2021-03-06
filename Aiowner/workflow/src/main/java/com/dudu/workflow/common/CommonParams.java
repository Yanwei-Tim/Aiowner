package com.dudu.workflow.common;

import com.dudu.persistence.user.User;

import rx.functions.Action1;

/**
 * Created by Administrator on 2016/2/16.
 */
public class CommonParams {

    public static final int ROBBERYSTATE = 0;
    public static final int HEADLIGHT = 1;
    public static final int PARK = 2;
    public static final int GUN = 3;

    private static CommonParams mInstance = new CommonParams();

    private User user = new User();

    public static CommonParams getInstance() {
        return mInstance;
    }

    public void init() {
        FlowFactory.getUserDataFlow().getUserInfo()
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        CommonParams.this.user.setId(user.getId());
                        CommonParams.this.user.setUserName(user.getUserName());
                    }
                });
    }

    public User getUser() {
        if(user == null){
            user = new User();
            user.setId(1);
        }
        return user;
    }

    public String getUserName() {
        return user.getUserName();
    }

}
