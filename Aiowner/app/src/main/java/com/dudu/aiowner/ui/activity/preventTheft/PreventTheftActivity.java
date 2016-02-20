package com.dudu.aiowner.ui.activity.preventTheft;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.activity.user.UserInfoActivity;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.workflow.common.FlowFactory;
import com.dudu.workflow.common.ObservableFactory;
import com.dudu.workflow.common.RequestFactory;
import com.dudu.workflow.guard.GuardRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.functions.Action1;

/**
 * Created by sunny_zhang on 2016/2/3.
 */
public class PreventTheftActivity extends BaseActivity {

    private ToggleButton theft_switch;

    private Logger logger = LoggerFactory.getLogger("PreventTheftActivity");

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
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                FlowFactory.getSwitchDataFlow()
                        .saveGuardSwitch(isChecked);
                if (isChecked) {
                    RequestFactory.getGuardRequest().lockCar(new GuardRequest.LockStateCallBack() {
                        @Override
                        public void hasLocked(boolean locked) {
                            if (locked) {
                                Toast.makeText(getApplicationContext(), "请求关闭防盗模式成功", Toast.LENGTH_SHORT).show();
                            } else {
                                FlowFactory.getSwitchDataFlow().saveGuardSwitch(!locked);
                                Toast.makeText(getApplicationContext(), "请求关闭防盗模式失败", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void requestError(String error) {
                            logger.error(error);
                            FlowFactory.getSwitchDataFlow().saveGuardSwitch(!isChecked);
                        }
                    });
                } else {
                    RequestFactory.getGuardRequest().unlockCar(new GuardRequest.UnlockCallBack() {
                        @Override
                        public void unlocked(boolean locked) {
                            if (locked) {
                                Toast.makeText(getApplicationContext(), "请求开启防盗模式成功", Toast.LENGTH_SHORT).show();
                            } else {
                                FlowFactory.getSwitchDataFlow().saveGuardSwitch(!locked);
                                Toast.makeText(getApplicationContext(), "请求开启防盗模式失败", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void requestError(String error) {
                            logger.error(error);
                            FlowFactory.getSwitchDataFlow().saveGuardSwitch(!isChecked);
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
        observableFactory.getTitleObservable().titleText.set("车辆防盗");
        observableFactory.getTitleObservable().userIcon.set(true);
        observableFactory.getCommonObservable().hasBottomIcon.set(false);
        reflashSwitch();
        super.onResume();
    }

    private void reflashSwitch() {
        FlowFactory.getSwitchDataFlow().getGuardSwitch()
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        theft_switch.setChecked(aBoolean);
                    }
                });
        ObservableFactory.getGuardReceiveObservable()
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean locked) {
                        theft_switch.setChecked(!locked);
                    }
                });
        RequestFactory.getGuardRequest().isAntiTheftOpened(new GuardRequest.LockStateCallBack() {
            @Override
            public void hasLocked(boolean locked) {
//                GuardFlow.saveGuardState(locked);
                theft_switch.setChecked(!locked);
                FlowFactory.getSwitchDataFlow().saveRobberyState(locked);
            }

            @Override
            public void requestError(String error) {

            }
        });
    }
}
