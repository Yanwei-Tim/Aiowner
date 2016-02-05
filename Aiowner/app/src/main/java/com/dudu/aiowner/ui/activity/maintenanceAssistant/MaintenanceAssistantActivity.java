package com.dudu.aiowner.ui.activity.maintenanceAssistant;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.activity.login.ForgetLoginPswActitivy;
import com.dudu.aiowner.ui.activity.preventTheft.SetDigitalPswActivity;
import com.dudu.aiowner.ui.base.BaseActivity;

/**
 * Created by Administrator on 2016/2/2.
 */
public class MaintenanceAssistantActivity extends BaseActivity {
    @Override
    protected View getChildView() {

        return LayoutInflater.from(this).inflate(R.layout.activity_maintenance_assistant, null);
    }

    public void startDigitalPassword(View view) {

        startActivity(new Intent(MaintenanceAssistantActivity.this, SetDigitalPswActivity.class));
    }

    public void startFogetPassword(View view) {

        startActivity(new Intent(MaintenanceAssistantActivity.this, ForgetLoginPswActitivy.class));
    }


    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("保养助手");
        observableFactory.getTitleObservable().userIcon.set(true);
        observableFactory.getCommonObservable().hasBottomIcon.set(false);
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
