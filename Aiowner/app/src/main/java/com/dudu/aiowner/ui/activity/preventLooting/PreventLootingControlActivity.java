package com.dudu.aiowner.ui.activity.preventLooting;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.activity.user.UserInfoActivity;
import com.dudu.aiowner.ui.base.BaseActivity;

/**
 * Created by Administrator on 2016/2/2.
 */
public class PreventLootingControlActivity extends BaseActivity {

    private ToggleButton looting_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
    }

    private void initView() {
        looting_switch = (ToggleButton) findViewById(R.id.prevent_looting_switch);
    }

    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_prevent_looting_control, null);
    }

    public void userInfo(View view) {
        startActivity(new Intent(PreventLootingControlActivity.this, UserInfoActivity.class));
    }

    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("车辆防劫");
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

    private void initEvent() {
        looting_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    looting_switch.setBackgroundResource(R.drawable.theft_lock_off);
                } else {
                    looting_switch.setBackgroundResource(R.drawable.theft_lock_on);
                }
            }
        });
    }
}
