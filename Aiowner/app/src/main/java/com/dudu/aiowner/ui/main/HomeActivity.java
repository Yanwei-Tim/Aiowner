package com.dudu.aiowner.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.activity.login.LoginActivity;
import com.dudu.aiowner.ui.activity.register.TelephoneNumberActivity;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

/**
 * Created by sunny_zhang on 2016/1/29.
 */
public class HomeActivity extends Activity {
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        registPushManager();
    }

    private void registPushManager() {
        // 开启logcat输出，方便debug，发布时请关闭
        XGPushConfig.enableDebug(this, true);
        Context context = getApplicationContext();
        XGPushManager.registerPush(context, "18520339890", new XGIOperateCallback() {
            @Override
            public void onSuccess(Object o, int i) {
                Toast.makeText(HomeActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "注册成功");
            }

            @Override
            public void onFail(Object o, int i, String s) {
                Toast.makeText(HomeActivity.this, "注册失败" + s, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "注册失败" + s);
            }
        });
    }

    public void startRegister(View view) {

        startActivity(new Intent(HomeActivity.this, TelephoneNumberActivity.class));
    }

    public void startLogin(View view) {

        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
    }
}
