package com.dudu.aiowner.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sunny_zhang on 2016/3/2.
 */
public class SetTheftGesturePswOk extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initIntent();
    }

    private void initIntent() {

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SetTheftGesturePswOk.this, TheftPswActivity.class));
            }
        }, 3000);
    }
    @Override
    protected View getChildView() {

        return LayoutInflater.from(this).inflate(R.layout.activity_set_theft_gesture_psw_ok, null);
    }

    @Override
    protected void onResume() {

        observableFactory.getTitleObservable().titleText.set("");
        observableFactory.getTitleObservable().userIcon.set(false);
        observableFactory.getTitleObservable().hasBackGround.set(true);
        observableFactory.getTitleObservable().hasUserBackButton.set(true);
        observableFactory.getCommonObservable().hasBottomIcon.set(false);
        observableFactory.getCommonObservable().hasUserBackground.set(true);
        super.onResume();
    }
}
