package com.dudu.aiowner.ui.activity.preventTheft;

import android.content.Intent;
import android.os.Bundle;
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
public class OwnersReviewOkActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
    }

    private void initIntent() {

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(OwnersReviewOkActivity.this, PreventTheftActivity.class));
            }
        }, 3000);
    }


    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_owners_review_ok, null);
    }

    public void userInfo(View view) {
        startActivity(new Intent(OwnersReviewOkActivity.this, UserInfoActivity.class));
    }
//    public void startPreventTheft(View view) {
//
//        startActivity(new Intent(OwnersReviewOkActivity.this, PreventTheftActivity.class));
//    }


    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("车辆防盗");
        observableFactory.getTitleObservable().userIcon.set(true);
        observableFactory.getCommonObservable().hasBottomIcon.set(false);
        initIntent();
        super.onResume();
    }
}
