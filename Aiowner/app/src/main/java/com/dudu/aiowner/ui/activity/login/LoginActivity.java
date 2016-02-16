package com.dudu.aiowner.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dudu.aiowner.R;
import com.dudu.aiowner.receiver.ReceiverRegister;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.aiowner.ui.main.MainActivity;
import com.dudu.workflow.RequestFactory;
import com.dudu.workflow.user.UserFlow;
import com.dudu.workflow.user.UserRequest;

/**
 * Created by sunny_zhang on 2016/1/27.
 */
public class LoginActivity extends BaseActivity {
    private EditText login_user_edittext;
    private EditText login_password_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        login_user_edittext = (EditText) findViewById(R.id.login_user_edittext);
        login_password_et = (EditText) findViewById(R.id.forgetPsw_newPsw_et);
    }

    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.acitivty_login, null);
    }

    public void startMain(View view) {

        final String username = login_user_edittext.getText().toString();
        final String password = login_password_et.getText().toString();

        if (username.isEmpty()) {
            Toast.makeText(getApplicationContext(), "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestFactory.getUserRequest()
                .login(username, password, new UserRequest.LoginCallback() {
                    @Override
                    public void loginSuccess(boolean success) {
                        if (success) {
                            UserFlow.saveUserName(username);
                            ReceiverRegister.registPushManager(username);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            Toast.makeText(getApplicationContext(), "登录请求成功", Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            Toast.makeText(getApplicationContext(), "登录请求失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void startForgetPreventTheftPsw(View view) {

        startActivity(new Intent(LoginActivity.this, ForgetLoginPswActitivy.class));
    }

    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("登录");
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
