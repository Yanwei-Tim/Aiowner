package com.dudu.workflow.user;

import android.util.Log;

import com.dudu.aiowner.rest.common.Request;
import com.dudu.aiowner.rest.model.LoginResponse;
import com.dudu.aiowner.rest.model.RegisterResponse;
import com.dudu.aiowner.rest.model.UserInfoResponse;
import com.dudu.aiowner.rest.model.UserLogin;
import com.dudu.aiowner.rest.model.UserRegister;
import com.dudu.workflow.common.CommonParams;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/2/14.
 */
public class UserRequestRetrofitImpl implements UserRequest {

    public static UserRequestRetrofitImpl mInstance = new UserRequestRetrofitImpl();

    public static UserRequestRetrofitImpl getInstance() {
        return mInstance;
    }

//    @Override
//    public void requestVerifyCode(String cellphone, final RequestVerifyCodeCallback callback) {
//        Call<RequestResponse> call = Request.getInstance().getUserService()
//                .getSecurityCode(cellphone, "password", "method", "messageId");
//        call.enqueue(new Callback<RequestResponse>() {
//            @Override
//            public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
//                callback.requestVerifyCodeResult(true);
//            }
//
//            @Override
//            public void onFailure(Call<RequestResponse> call, Throwable t) {
//                callback.requestVerifyCodeResult(false);
//            }
//        });
//    }

//    @Override
//    public void isVerifyCodeValid(String cellphone, String securityCode, final VerifyCodeValidCallback callback) {
//        Call<RegisterResponse> call = Request.getInstance().getUserService()
//                .registerWithPhone(cellphone, "securityCode", "password", "method", "messageId");
//        call.enqueue(new Callback<RegisterResponse>() {
//            @Override
//            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
//                callback.verifyCodeIsValid(true);
//            }
//
//            @Override
//            public void onFailure(Call<RegisterResponse> call, Throwable t) {
//                callback.verifyCodeIsValid(false);
//            }
//        });
//    }

//    @Override
//    public void settingPassword(String cellphone, String securityCode, String password, final RegisterCallback callback) {
//        Call<RegisterResponse> call = Request.getInstance().getUserService()
//                .registerWithPhone(cellphone, "securityCode", "password", "method", "messageId");
//        call.enqueue(new Callback<RegisterResponse>() {
//            @Override
//            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
//                callback.registerSuccess(true);
//            }
//
//            @Override
//            public void onFailure(Call<RegisterResponse> call, Throwable t) {
//                callback.registerSuccess(false);
//            }
//        });
//    }

    /**
     * 请求注册用户
     *
     * @param cellphone
     * @param platform
     * @param callback
     */
    @Override
    public void register(String cellphone, String platform, final RegisterCallback callback) {
        Call<RegisterResponse> call = Request.getInstance().getUserService()
                .register(new UserRegister(cellphone, "android"));
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                Log.d("registerResponse", "----" + response.body().resultCode + response.body().resultMsg);
                callback.registerSuccess(response.body().resultCode == 0);
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.d("registerError", "-----" + t);
                callback.registerSuccess(false);
            }
        });
    }

    /**
     * 请求登录
     *
     * @param cellphone
     * @param password
     * @param platform
     * @param callback
     */
    @Override
    public void login(String cellphone, String password, String platform, final LoginCallback callback) {
        Call<LoginResponse> call = Request.getInstance().getUserService()
                .login(new UserLogin(cellphone, password, "android"));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d("LoginResponse", "LoginResponse:" + response.body().resultCode);
                callback.loginSuccess(response.body().resultCode == 0);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("loginError", "loginError:" + t);
                callback.loginSuccess(false);
            }
        });
    }

    /**
     * 获取用户信息详情
     * @param callback
     */
    @Override
    public void userInfo(final UserInfoCallback callback) {
        Call<UserInfoResponse> call = Request.getInstance().getUserService()
                .getUserInfo(CommonParams.getInstance().getUserName(), "android");
        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                callback.requestSuccess(response.body());
            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                callback.requestError(t.toString());
            }
        });
    }

    @Override
    public void setRequestVerifyCodeResult(boolean requestVerifyCodeResult) {

    }

    @Override
    public void setVerifyCodeIsValid(boolean verifyCodeIsValid) {

    }

    @Override
    public void setRegisterSuccess(boolean registerSuccess) {

    }

    @Override
    public void setLoginSuccess(boolean loginSuccess) {

    }

}
