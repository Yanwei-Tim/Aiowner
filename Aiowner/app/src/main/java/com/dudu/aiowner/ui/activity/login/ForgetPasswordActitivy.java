package com.dudu.aiowner.ui.activity.login;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.base.BaseActivity;

/**
 * Created by sunny_zhang on 2016/1/29.
 */
public class ForgetPasswordActitivy extends BaseActivity {

    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.acitivty_forgetpassword, null);
    }

    public void startLoginActivity(View view) {
        startActivity(new Intent(ForgetPasswordActitivy.this, LoginActivity.class));
    }

    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("忘记密码");
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
