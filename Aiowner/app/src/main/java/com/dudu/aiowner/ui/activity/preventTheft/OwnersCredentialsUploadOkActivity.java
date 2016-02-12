package com.dudu.aiowner.ui.activity.preventTheft;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.activity.user.UserInfoActivity;
import com.dudu.aiowner.ui.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sunny_zhang on 2016/2/3.
 */
public class OwnersCredentialsUploadOkActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
    }

    private void initIntent() {

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(OwnersCredentialsUploadOkActivity.this, OwnersReviewActivity.class));
            }
        }, 3000);
    }

    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_owners_credentials_upload_ok, null);
    }

    public void userInfo(View view) {
        startActivity(new Intent(OwnersCredentialsUploadOkActivity.this, UserInfoActivity.class));
    }

//    public void startOwnersReview(View view) {
//
//        startActivity(new Intent(OwnersCredentialsUploadOkActivity.this, OwnersReviewActivity.class));
//    }

    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("车辆防盗");
        observableFactory.getTitleObservable().userIcon.set(true);
        observableFactory.getCommonObservable().hasBottomIcon.set(false);
        initIntent();
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
