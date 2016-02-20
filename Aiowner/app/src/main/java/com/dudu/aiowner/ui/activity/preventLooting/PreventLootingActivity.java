package com.dudu.aiowner.ui.activity.preventLooting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.activity.user.UserInfoActivity;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.workflow.common.CommonParams;
import com.dudu.workflow.common.FlowFactory;
import com.dudu.workflow.common.ObservableFactory;
import com.dudu.workflow.common.RequestFactory;
import com.dudu.workflow.robbery.RobberyRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        FlowFactory.getSwitchDataFlow().getRobberySwitches()
                .subscribe(robberySwitches -> {
                    light_switch.setChecked(robberySwitches.isHeadlight());
                    debus_switch.setChecked(robberySwitches.isPark());
                    brake_switch.setChecked(robberySwitches.isGun());
                });
        ObservableFactory.getRobberyFlow()
                .subscribe(receiverData -> {
                    boolean headLight = receiverData.getSwitch1Value().equals("1");
                    boolean park = receiverData.getSwitch2Value().equals("1");
                    boolean gun = receiverData.getSwitch3Value().equals("1");
                    light_switch.setChecked(headLight);
                    debus_switch.setChecked(park);
                    brake_switch.setChecked(gun);
                    FlowFactory.getSwitchDataFlow().saveRobberySwitch(CommonParams.HEADLIGHT, headLight);
                    FlowFactory.getSwitchDataFlow().saveRobberySwitch(CommonParams.PARK, park);
                    FlowFactory.getSwitchDataFlow().saveRobberySwitch(CommonParams.GUN, gun);
                    boolean robberyState = receiverData.getSwitch0Value().equals("1");
                    if(robberyState){
                        startActivity(new Intent(PreventLootingActivity.this, PreventLootingControlActivity.class));
                        FlowFactory.getSwitchDataFlow().saveRobberyState(robberyState);
                        PreventLootingActivity.this.finish();
                    }
                });
        RequestFactory.getRobberyRequest()
                .getRobberyState(new RobberyRequest.RobberStateCallback() {
                    @Override
                    public void switchsState(boolean flashRateTimes, boolean emergencyCutoff, boolean stepOnTheGas) {
                        light_switch.setChecked(flashRateTimes);
                        debus_switch.setChecked(emergencyCutoff);
                        brake_switch.setChecked(stepOnTheGas);
                        FlowFactory.getSwitchDataFlow().saveRobberySwitch(CommonParams.HEADLIGHT, flashRateTimes);
                        FlowFactory.getSwitchDataFlow().saveRobberySwitch(CommonParams.PARK, emergencyCutoff);
                        FlowFactory.getSwitchDataFlow().saveRobberySwitch(CommonParams.GUN, stepOnTheGas);
                    }

                    @Override
                    public void requestError(String error) {
                        logger.error(error);
                    }
                });
    }

    public void settingAntiRobberyMode(final ToggleButton switchButton, final int type, final boolean open) {
        FlowFactory.getSwitchDataFlow().saveRobberySwitch(type, open);
        RequestFactory.getInstance().getRobberyRequest()
                .settingAntiRobberyMode(type, open ? 1 : 0, new RobberyRequest.SwitchCallback() {
                    @Override
                    public void switchSuccess(boolean success) {
                        if (success) {
                            if (open) {
                                switchButton.setBackgroundResource(R.drawable.looting_lock_on);
                            } else {
                                switchButton.setBackgroundResource(R.drawable.looting_lock_off);
                            }
                        } else {
                            FlowFactory.getSwitchDataFlow().saveRobberySwitch(type, !open);
                            logger.debug("防劫模式" + (open ? "开启" : "关闭") + "失败");
                        }
                    }

                    @Override
                    public void requestError(String error) {
                        FlowFactory.getSwitchDataFlow().saveRobberySwitch(type, !open);
                        logger.debug("防劫模式" + (open ? "开启" : "关闭") + "请求失败");
                    }
                });
    }

    private void initEvent() {
        light_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settingAntiRobberyMode(light_switch, CommonParams.HEADLIGHT, isChecked);
            }
        });

        debus_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settingAntiRobberyMode(debus_switch, CommonParams.PARK, isChecked);
            }
        });

        brake_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settingAntiRobberyMode(brake_switch, CommonParams.GUN, isChecked);
            }
        });
    }
}
