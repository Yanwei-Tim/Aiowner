package com.dudu.aiowner.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.BuildConfig;
import com.dudu.aiowner.R;
import com.dudu.aiowner.databinding.ActivityLoginBinding;
import com.dudu.aiowner.receiver.ReceiverRegister;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.aiowner.ui.main.MainActivity;
import com.dudu.workflow.common.FlowFactory;
import com.dudu.workflow.common.RequestFactory;

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

        findViewById(R.id.base_view).setBackgroundColor(getResources().getColor(R.color.aiowner));
    }

    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_login, null);
    }


    public void startMain(View view) {

        final String username = loginBinding.loginUserEt.getText().toString();
        final String password = loginBinding.loginPswEt.getText().toString();

        if (username.isEmpty()) {
            Log.d("login", "login:" + "请输入用户名");
            return;
        }
        if (password.isEmpty()) {
            Log.d("login", "login:" + "请输入密码");
            return;
        }

        RequestFactory.getUserRequest()
                .login(username, password, "android", success -> {
                    if (success) {
                        launchMainActivity(username);
                    } else {
//                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        Log.d("login", "loginError:" + "登录请求失败");
                    }
                });
    }

    private void launchMainActivity(String username) {
        FlowFactory.getUserDataFlow().saveUserName(username);
        ReceiverRegister.registPushManager(username);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        Log.d("login", "loginSuccess:" + "登录请求成功");
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

        if (BuildConfig.DEBUG) {
            loginBinding.loginPswEt.setText("123123");
        }
    }

    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("登录");
        super.onResume();
    }
}
