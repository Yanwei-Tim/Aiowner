package com.dudu.aiowner.ui.activity.preventLooting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ToggleButton;

import com.dudu.aiowner.R;
import com.dudu.aiowner.commonlib.model.ReceiverData;
import com.dudu.aiowner.ui.activity.user.UserInfoActivity;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.workflow.common.FlowFactory;
import com.dudu.workflow.common.RequestFactory;
import com.dudu.workflow.receiver.ReceiverDataFlow;
import com.dudu.workflow.robbery.RobberyRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.greenrobot.event.EventBus;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/2/2.
 */
public class PreventLootingControlActivity extends BaseActivity {

    private ToggleButton looting_switch;

    private Logger logger = LoggerFactory.getLogger("PreventLootingControlActivity");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
        EventBus.getDefault().register(this);
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
        reflashSwitch();
        super.onResume();
    }

    private void reflashSwitch(){
        FlowFactory.getSwitchDataFlow().getRobberyState()
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        looting_switch.setChecked(aBoolean);
                    }
                });
//        ObservableFactory.getRobberyFlow()
//                .subscribe(new Action1<ReceiverData>() {
//                    @Override
//                    public void call(ReceiverData receiverData) {
//                        boolean hasLocked = receiverData.getSwitch0Value().equals("1");
//                        FlowFactory.getSwitchDataFlow().saveRobberyState(hasLocked);
//                        looting_switch.setChecked(hasLocked);
//                    }
//                });
        RequestFactory.getRobberyRequest().isCarRobbed(new RobberyRequest.CarRobberdCallback() {
            @Override
            public void hasRobbed(boolean robbed) {
                looting_switch.setChecked(robbed);
                FlowFactory.getSwitchDataFlow().saveRobberyState(robbed);
                if (!robbed) {
                    startActivity(new Intent(PreventLootingControlActivity.this, PreventLootingActivity.class));
                    PreventLootingControlActivity.this.finish();
                }
            }

            @Override
            public void requestError(String error) {
                logger.error(error);
            }
        });
    }

    private void initEvent() {
        looting_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSwitch(looting_switch.isChecked());
            }
        });
    }

    private void checkSwitch(boolean isChecked){
        FlowFactory.getSwitchDataFlow().saveRobberyState(isChecked);
        if (!isChecked) {
            RequestFactory.getRobberyRequest().closeAntiRobberyMode(new RobberyRequest.CloseRobberyModeCallback() {
                @Override
                public void closeSuccess(boolean success) {
                    if (success) {
                        logger.debug("请求关闭防劫模式成功");
                    } else {
                        logger.debug("请求关闭防劫模式失败");
                    }
                }

                @Override
                public void requestError(String error) {
                    logger.error(error);
                }
            });
        }
    }

    public void onEventMainThread(ReceiverData event) {
        if(ReceiverDataFlow.getGuardReceiveData(event)){
            boolean switchvalue = event.getSwitchValue().equals("1");
            FlowFactory.getSwitchDataFlow().saveGuardSwitch(switchvalue);
            looting_switch.setChecked(switchvalue);
            if (!switchvalue) {
                startActivity(new Intent(PreventLootingControlActivity.this, PreventLootingActivity.class));
                FlowFactory.getSwitchDataFlow().saveRobberyState(switchvalue);
                PreventLootingControlActivity.this.finish();
            }
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
