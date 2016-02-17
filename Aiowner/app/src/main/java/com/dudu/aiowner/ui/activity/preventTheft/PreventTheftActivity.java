package com.dudu.aiowner.ui.activity.preventTheft;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.activity.user.UserInfoActivity;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.workflow.RequestFactory;
import com.dudu.workflow.guard.GuardFlow;
import com.dudu.workflow.guard.GuardRequest;

/**
 * Created by sunny_zhang on 2016/2/3.
 */
public class PreventTheftActivity extends BaseActivity {

    private ToggleButton theft_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
    }

    private void initView() {
        theft_switch = (ToggleButton) findViewById(R.id.prevent_theft_switch);
    }

    private void initEvent() {
        theft_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    RequestFactory.getGuardRequest().lockCar(new GuardRequest.LockStateCallBack() {
                        @Override
                        public void hasLocked(boolean locked) {
                            if (locked) {
                                GuardFlow.saveGuardState(locked);
                                theft_switch.setBackgroundResource(R.drawable.theft_lock_on);
                                Toast.makeText(getApplicationContext(), "请求关闭防盗模式成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "请求关闭防盗模式失败", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void requestError(String error) {
                            return;
                        }
                    });
                } else {
                    RequestFactory.getGuardRequest().unlockCar(new GuardRequest.UnlockCallBack() {
                        @Override
                        public void unlocked(boolean locked) {
                            if (locked) {
                                GuardFlow.saveGuardState(locked);
                                theft_switch.setBackgroundResource(R.drawable.theft_lock_off);
                                Toast.makeText(getApplicationContext(), "请求开启防盗模式成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "请求开启防盗模式失败", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void requestError(String error) {

                        }
                    });

                }
            }
        });
    }

    @Override
    protected View getChildView() {
        return LayoutInflater.from(PreventTheftActivity.this).inflate(R.layout.activity_prevent_theft, null);
    }

    public void startSetGesturePassword(View view) {


//        startActivity(new Intent(PreventTheftActivity.this, SetGesturePswActivity.class));
    }

    public void userInfo(View view) {
        startActivity(new Intent(PreventTheftActivity.this, UserInfoActivity.class));
    }

    @Override
    protected void onResume() {
        theft_switch.setChecked(GuardFlow.getGuardState());
        RequestFactory.getGuardRequest().isAntiTheftOpened(new GuardRequest.LockStateCallBack() {
            @Override
            public void hasLocked(boolean locked) {
                GuardFlow.saveGuardState(locked);
                theft_switch.setChecked(locked);
            }

            @Override
            public void requestError(String error) {

            }
        });
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
