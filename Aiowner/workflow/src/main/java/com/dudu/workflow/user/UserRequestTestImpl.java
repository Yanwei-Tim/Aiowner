package com.dudu.workflow.user;

/**
 * Created by Administrator on 2016/2/14.
 */
public class UserRequestTestImpl implements UserRequest {

    public static UserRequestTestImpl mInstance = new UserRequestTestImpl();

    public static UserRequestTestImpl getInstance() {
        return mInstance;
    }

    @Override
    public void requestVerifyCode(String cellphone, final RequestVerifyCodeCallback callback) {
        callback.requestVerifyCodeResult(true);
    }

    @Override
    public void isVerifyCodeValid(String cellphone, String securityCode, final VerifyCodeValidCallback callback) {
        callback.verifyCodeIsValid(true);

    }

    @Override
    public void settingPassword(String cellphone, String securityCode, String password, final RegisterCallback callback) {
        callback.registerSuccess(true);
    }

    @Override
    public void login(String cellphone, String password, LoginCallback callback) {
        callback.loginSuccess(true);
    }
}

