package com.dudu.workflow.user;

/**
 * Created by Administrator on 2016/2/14.
 */
public class UserRequestTestImpl implements UserRequest {

    public static UserRequestTestImpl mInstance = new UserRequestTestImpl();

    public static UserRequestTestImpl getInstance() {
        return mInstance;
    }

    private boolean requestVerifyCodeResult=true;

    private boolean verifyCodeIsValid=true;

    private boolean registerSuccess=true;

    private boolean loginSuccess=true;
    @Override
    public void requestVerifyCode(String cellphone, final RequestVerifyCodeCallback callback) {
        callback.requestVerifyCodeResult(requestVerifyCodeResult);
    }

    @Override
    public void isVerifyCodeValid(String cellphone, String securityCode, final VerifyCodeValidCallback callback) {
        callback.verifyCodeIsValid(verifyCodeIsValid);

    }

    @Override
    public void settingPassword(String cellphone, String securityCode, String password, final RegisterCallback callback) {
        callback.registerSuccess(registerSuccess);
    }

    @Override
    public void login(String cellphone, String password, LoginCallback callback) {
        callback.loginSuccess(loginSuccess);
    }

    public void setRequestVerifyCodeResult(boolean requestVerifyCodeResult) {
        this.requestVerifyCodeResult = requestVerifyCodeResult;
    }

    public void setVerifyCodeIsValid(boolean verifyCodeIsValid) {
        this.verifyCodeIsValid = verifyCodeIsValid;
    }

    public void setRegisterSuccess(boolean registerSuccess) {
        this.registerSuccess = registerSuccess;
    }

    public void setLoginSuccess(boolean loginSuccess) {
        this.loginSuccess = loginSuccess;
    }
}

