package com.dudu.aiowner.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.dudu.aiowner.BuildConfig;
import com.dudu.aiowner.R;
import com.dudu.aiowner.receiver.ReceiverRegister;
import com.dudu.aiowner.ui.activity.login.LoginActivity;
import com.dudu.aiowner.ui.activity.register.TelephoneNumberActivity;
import com.dudu.workflow.common.FlowFactory;

/**
 * Created by sunny_zhang on 2016/1/29.
 */
public class HomeActivity extends Activity {
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_home);

        if (BuildConfig.DEBUG) {
            findViewById(R.id.home_login_btn).setOnLongClickListener(v -> {
                launchMainActivity("15820757371");
                return true;
            });
        }
    }

    private void launchMainActivity(String username) {
        FlowFactory.getUserDataFlow().saveUserName(username);
        ReceiverRegister.registPushManager(username);
        startActivity(new Intent(HomeActivity.this, MainActivity.class));
        Log.d("login", "loginSuccess:" + "登录请求成功");
        finish();
    }

    public void startRegister(View view) {
        startActivity(new Intent(HomeActivity.this, TelephoneNumberActivity.class));
    }

    public void startLogin(View view) {
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        finish();
    }
}
