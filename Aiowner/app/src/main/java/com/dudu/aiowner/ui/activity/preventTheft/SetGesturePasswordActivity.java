package com.dudu.aiowner.ui.activity.preventTheft;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.base.BaseActivity;

/**
 * Created by sunny_zhang on 2016/2/4.
 */
public class SetGesturePasswordActivity extends BaseActivity {
    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_set_gesture_password, null);
    }

    public void startDigitalPassword(View view) {

        startActivity(new Intent(SetGesturePasswordActivity.this, SetDigitalPasswordActivity.class));
    }

    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("车辆防盗");
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
