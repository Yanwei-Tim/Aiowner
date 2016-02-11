package com.dudu.aiowner.ui.activity.user;

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
public class UserInfoActivity extends BaseActivity {
    @Override
    protected View getChildView() {

        return LayoutInflater.from(this).inflate(R.layout.activity_user_info, null);
    }

    public void startDigitalPassword(View view) {

        startActivity(new Intent(UserInfoActivity.this, SetDigitalPswActivity.class));
    }

    public void startFogetPassword(View view) {

        startActivity(new Intent(UserInfoActivity.this, ForgetLoginPswActitivy.class));
    }


    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("");
        observableFactory.getTitleObservable().userIcon.set(false);
        observableFactory.getCommonObservable().hasBottomIcon.set(false);
        observableFactory.getTitleObservable().userTitleLogo.set(true);
        observableFactory.getTitleObservable().hasBackGround.set(true);
        observableFactory.getTitleObservable().hasUserBackButton.set(true);
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
