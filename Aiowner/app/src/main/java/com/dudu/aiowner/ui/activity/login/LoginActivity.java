package com.dudu.aiowner.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.aiowner.ui.main.MainActivity;

/**
 * Created by sunny_zhang on 2016/1/27.
 */
public class LoginActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.acitivty_login, null);
    }

    public void startMain(View view) {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    public void startFogetPassword(View view) {

        startActivity(new Intent(LoginActivity.this, ForgetPasswordActitivy.class));
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
