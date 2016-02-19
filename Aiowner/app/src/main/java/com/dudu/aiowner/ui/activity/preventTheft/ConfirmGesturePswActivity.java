package com.dudu.aiowner.ui.activity.preventTheft;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.activity.login.ForgetLoginPswActitivy;
import com.dudu.aiowner.ui.activity.user.UserInfoActivity;
import com.dudu.aiowner.ui.base.BaseActivity;

/**
 * Created by sunny_zhang on 2016/2/4.
 */
public class ConfirmGesturePswActivity extends BaseActivity {
    @Override
    protected View getChildView() {

        return LayoutInflater.from(this).inflate(R.layout.activity_confirm_gesture_psw, null);
    }

    public void startDigitalPassword(View view) {

        startActivity(new Intent(ConfirmGesturePswActivity.this, SetDigitalPswActivity.class));
    }

    public void startFogetPassword(View view) {

        startActivity(new Intent(ConfirmGesturePswActivity.this, ForgetLoginPswActitivy.class));
    }
    public void userInfo(View view) {
        startActivity(new Intent(ConfirmGesturePswActivity.this, UserInfoActivity.class));
    }

    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("车辆防盗");
        observableFactory.getTitleObservable().userIcon.set(true);
        observableFactory.getCommonObservable().hasBottomIcon.set(true);
        super.onResume();
    }
}
