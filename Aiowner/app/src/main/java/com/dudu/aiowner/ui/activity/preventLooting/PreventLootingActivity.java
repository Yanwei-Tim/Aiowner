package com.dudu.aiowner.ui.activity.preventLooting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.dudu.aiowner.R;
import com.dudu.aiowner.commonlib.model.ReceiverData;
import com.dudu.aiowner.ui.activity.user.UserInfoActivity;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.workflow.ObservableFactory;
import com.dudu.workflow.RequestFactory;
import com.dudu.workflow.robbery.RobberyRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.functions.Action1;

/**
 * Created by Administrator on 2016/2/2.
 */
public class PreventLootingActivity extends BaseActivity {
    private ToggleButton light_switch;
    private ToggleButton debus_switch;
    private ToggleButton brake_switch;

    private Logger logger = LoggerFactory.getLogger("PreventLootingActivity");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
    }

    private void initView() {
        light_switch = (ToggleButton) findViewById(R.id.prevent_looting_light_switch);
        debus_switch = (ToggleButton) findViewById(R.id.prevent_looting_debus_switch);
        brake_switch = (ToggleButton) findViewById(R.id.prevent_looting_brake_switch);
    }

    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_prevent_looting, null);
    }

    public void userInfo(View view) {
        startActivity(new Intent(PreventLootingActivity.this, UserInfoActivity.class));
    }

    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("车辆防劫");
        observableFactory.getTitleObservable().userIcon.set(true);
        observableFactory.getCommonObservable().hasBottomIcon.set(false);
        reflashSwitch();
        super.onResume();
    }

    private void reflashSwitch() {
        ObservableFactory.getRobberyFlow()
                .subscribe(new Action1<ReceiverData>() {
                    @Override
                    public void call(ReceiverData receiverData) {
                        light_switch.setChecked(receiverData.getSwitch1Value().equals("1"));
                        debus_switch.setChecked(receiverData.getSwitch2Value().equals("1"));
                        brake_switch.setChecked(receiverData.getSwitch3Value().equals("1"));
                    }
                });
        RequestFactory.getRobberyRequest()
                .getRobberyState(new RobberyRequest.RobberStateCallback() {
                    @Override
                    public void switchsState(boolean flashRateTimes, boolean emergencyCutoff, boolean stepOnTheGas) {
                        light_switch.setChecked(flashRateTimes);
                        debus_switch.setChecked(emergencyCutoff);
                        brake_switch.setChecked(stepOnTheGas);
                    }

                    @Override
                    public void requestError(String error) {
                        logger.error(error);
                    }
                });
    }

    public void settingAntiRobberyMode(final ToggleButton switchButton, final int type, final boolean open) {
        RequestFactory.getInstance().getRobberyRequest()
                .settingAntiRobberyMode(type, open ? 1 : 0, new RobberyRequest.SwitchCallback() {
                    @Override
                    public void switchSuccess(boolean success) {
                        if (success) {
//                            RobberyFlow.saveRobbeySingleState(open, type);
                            if (open) {
                                switchButton.setBackgroundResource(R.drawable.looting_lock_on);
                            } else {
                                switchButton.setBackgroundResource(R.drawable.looting_lock_off);
                            }
                        } else {
                            Toast.makeText(getApplication(), "防劫模式" + (open ? "开启" : "关闭") + "失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void requestError(String error) {
                        Toast.makeText(getApplication(), "防劫模式" + (open ? "开启" : "关闭") + "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initEvent() {
        light_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settingAntiRobberyMode(light_switch, 1, isChecked);
            }
        });

        debus_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settingAntiRobberyMode(debus_switch, 2, isChecked);
            }
        });

        brake_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settingAntiRobberyMode(brake_switch, 3, isChecked);
            }
        });
    }
}
