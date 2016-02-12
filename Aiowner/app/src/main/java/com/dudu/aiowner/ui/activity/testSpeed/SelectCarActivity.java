package com.dudu.aiowner.ui.activity.testSpeed;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.activity.user.UserInfoActivity;
import com.dudu.aiowner.ui.base.BaseActivity;

/**
 * Created by Administrator on 2016/2/2.
 */
public class SelectCarActivity extends BaseActivity {
    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_sel_car, null);
    }

    public void userInfo(View view) {
        startActivity(new Intent(SelectCarActivity.this, UserInfoActivity.class));
    }

    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("加速测试");
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

    public void startTestSpeed(View view){
        startActivity(new Intent(SelectCarActivity.this, TestSpeedActivity.class));
    }
}
