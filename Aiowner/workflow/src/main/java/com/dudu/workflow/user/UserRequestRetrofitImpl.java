package com.dudu.workflow.user;

import com.dudu.aiowner.rest.common.Request;
import com.dudu.aiowner.rest.model.LoginResponse;
import com.dudu.aiowner.rest.model.RegisterResponse;
import com.dudu.aiowner.rest.model.RequestResponse;

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

    @Override
    public void requestVerifyCode(String cellphone, final RequestVerifyCodeCallback callback) {
        Call<RequestResponse> call = Request.getInstance().getRegisterService()
                .getSecurityCode(cellphone, "password", "method", "messageId");
        call.enqueue(new Callback<RequestResponse>() {
            @Override
            public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                callback.requestVerifyCodeResult(true);
            }

            @Override
            public void onFailure(Call<RequestResponse> call, Throwable t) {
                callback.requestVerifyCodeResult(false);
            }
        });
    }

    @Override
    public void isVerifyCodeValid(String cellphone, String securityCode, final VerifyCodeValidCallback callback) {
        Call<RegisterResponse> call = Request.getInstance().getRegisterService()
                .registerWithPhone(cellphone, "securityCode", "password", "method", "messageId");
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                callback.verifyCodeIsValid(true);
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                callback.verifyCodeIsValid(false);
            }
        });
    }

    @Override
    public void settingPassword(String cellphone, String securityCode, String password, final RegisterCallback callback) {
        Call<RegisterResponse> call = Request.getInstance().getRegisterService()
                .registerWithPhone(cellphone, "securityCode", "password", "method", "messageId");
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                callback.registerSuccess(true);
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                callback.registerSuccess(false);
            }
        });
    }

    @Override
    public void login(String cellphone, String password, final LoginCallback callback) {
        Call<LoginResponse> call = Request.getInstance().getLoginService()
                .login(cellphone, password, "method", "messageId");
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                callback.loginSuccess(true);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                callback.loginSuccess(false);
            }
        });
    }

}
