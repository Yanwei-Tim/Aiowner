package com.dudu.aiowner.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.activity.bind.DeviceBindActivity;
import com.dudu.aiowner.ui.base.BaseActivity;

/**
 * Created by Administrator on 2016/2/2.
 */
public class UserInfoActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View getChildView() {

        return LayoutInflater.from(this).inflate(R.layout.activity_user_info, null);
    }

    public void startModifyHeadPortraitAndNickname(View view) {

        startActivity(new Intent(UserInfoActivity.this, ModifyHeadActivity.class));
    }

    public void startModifyPsw(View view) {

        startActivity(new Intent(UserInfoActivity.this, ModifyPswActivity.class));
    }

    public void startGesturePsw(View view) {
        startActivity(new Intent(UserInfoActivity.this, TheftPswActivity.class));
    }

    public void startUserFeedback(View view) {
        startActivity(new Intent(UserInfoActivity.this, UserFeedbackActivity.class));
    }

    public void startDeviceMatch(View view) {
//        startActivity(new Intent(UserInfoActivity.this, DeviceMatchAcitivty.class));
//        QrScan.launch(this, 500,500);
        DeviceBindActivity.launch(this);
    }
    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("");
        observableFactory.getTitleObservable().userIcon.set(false);
        observableFactory.getTitleObservable().userTitleLogo.set(true);
        observableFactory.getTitleObservable().hasBackGround.set(true);
        observableFactory.getTitleObservable().hasUserBackButton.set(true);
        observableFactory.getCommonObservable().hasBottomIcon.set(false);
        observableFactory.getCommonObservable().hasUserBackground.set(true);
        super.onResume();
    }
}
