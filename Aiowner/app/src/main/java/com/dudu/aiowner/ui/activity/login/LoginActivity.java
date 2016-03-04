package com.dudu.aiowner.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.databinding.ActivityLoginBinding;
import com.dudu.aiowner.receiver.ReceiverRegister;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.aiowner.ui.main.MainActivity;
import com.dudu.workflow.common.FlowFactory;
import com.dudu.workflow.common.RequestFactory;
import com.dudu.workflow.user.UserRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.functions.Action1;

/**
 * Created by sunny_zhang on 2016/1/27.
 */
public class LoginActivity extends BaseActivity {

    private Logger logger = LoggerFactory.getLogger("LoginActivity");
    private ActivityLoginBinding loginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.bind(childView);
        initUserName();
    }

    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_login, null);
    }

    public void startMain(View view) {

        final String username = loginBinding.loginUserEt.getText().toString();
        final String password = loginBinding.loginPswEt.getText().toString();

        if (username.isEmpty()) {
            logger.debug("请输入用户名");
            return;
        }
        if (password.isEmpty()) {
            logger.debug("请输入密码");
            return;
        }

        RequestFactory.getUserRequest()
                .login(username, password, new UserRequest.LoginCallback() {
                    @Override
                    public void loginSuccess(boolean success) {
                        if (success) {
                            FlowFactory.getUserDataFlow().saveUserName(username);
                            ReceiverRegister.registPushManager(username);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            logger.debug("登录请求成功");
                        } else {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            logger.debug("登录请求失败");
                        }
                    }
                });
        finish();
    }

    public void startForgetPreventTheftPsw(View view) {

        startActivity(new Intent(LoginActivity.this, ForgetLoginPswActitivy.class));
    }

    private void initUserName() {
        FlowFactory.getUserDataFlow()
                .getUserName()
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String userName) {
                        loginBinding.loginUserEt.setText(userName);
                    }
                });
    }

    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("登录");
        super.onResume();
    }
}
