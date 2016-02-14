package com.dudu.workflow.user;

/**
 * Created by Administrator on 2016/2/14.
 */
public interface UserRequest {

    public void requestVerifyCode(String cellphone, RequestVerifyCodeCallback callback);

    public void isVerifyCodeValid(String cellphone, String securityCode, VerifyCodeValidCallback callback);

    public void settingPassword(String cellphone, String securityCode, String password, final RegisterCallback callback);

    public void login(String cellphone, String password, final LoginCallback callback);

    public interface RequestVerifyCodeCallback {
        public void requestVerifyCodeResult(boolean success);
    }

    public interface VerifyCodeValidCallback {
        public void verifyCodeIsValid(boolean success);
    }

    public interface RegisterCallback {
        public void registerSuccess(boolean success);
    }

    public interface LoginCallback {
        public void loginSuccess(boolean success);
    }
}
