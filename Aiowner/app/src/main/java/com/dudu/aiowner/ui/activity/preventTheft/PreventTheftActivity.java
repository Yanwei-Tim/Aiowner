package com.dudu.aiowner.ui.activity.preventTheft;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.databinding.ActivityPreventTheftBinding;
import com.dudu.aiowner.commonlib.model.ReceiverData;
import com.dudu.aiowner.ui.activity.user.UserInfoActivity;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.workflow.common.FlowFactory;
import com.dudu.workflow.common.RequestFactory;
import com.dudu.workflow.guard.GuardRequest;
import com.dudu.workflow.receiver.ReceiverDataFlow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.greenrobot.event.EventBus;
import rx.functions.Action1;

/**
 * Created by sunny_zhang on 2016/2/3.
 */
public class PreventTheftActivity extends BaseActivity {


    private ActivityPreventTheftBinding preventTheftBinding;
    private Logger logger = LoggerFactory.getLogger("PreventTheftActivity");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preventTheftBinding = ActivityPreventTheftBinding.bind(childView);

        EventBus.getDefault().register(this);
    }

    public void theftSwitchClick(View view) {

        boolean isChecked = preventTheftBinding.preventTheftSwitch.isChecked();
        checkSwitch(isChecked);

    }


    private void checkSwitch(boolean isChecked) {

        preventTheftBinding.preventTheftSwitch.setChecked(isChecked);

        FlowFactory.getSwitchDataFlow().saveGuardSwitch(isChecked);

        if (isChecked) {
            RequestFactory.getGuardRequest().lockCar(
                    new GuardRequest.LockStateCallBack() {
                        @Override
                        public void hasLocked(boolean locked) {
                            if (locked) {
                                logger.debug("请求打开防盗模式成功");
                            } else {
                                logger.debug("请求打开防盗模式失败");
                                checkSwitch(isChecked);
                            }
                        }

                        @Override
                        public void requestError(String error) {
                            logger.error(error);
                            checkSwitch(isChecked);
                        }
                    });
        }
        //
        else {
            startActivity(new Intent(PreventTheftActivity.this, InitGesturePswActivity.class));

            RequestFactory.getGuardRequest().unlockCar(
                    new GuardRequest.UnlockCallBack() {
                        @Override
                        public void unlocked(boolean locked) {
                            if (locked) {
                                logger.debug("请求关闭防盗模式成功");
                            } else {
                                logger.debug("请求关闭防盗模式失败");
                                checkSwitch(isChecked);
                            }
                        }

                        @Override
                        public void requestError(String error) {
                            logger.error(error);
                            checkSwitch(isChecked);
                        }
                    });
        }
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
                        preventTheftBinding.preventTheftSwitch.setChecked(aBoolean);
                    }
                });

//        ObservableFactory.getGuardReceiveObservable()
//                .subscribe(new Action1<Boolean>() {
//                    @Override
//                    public void call(Boolean locked) {
//                        FlowFactory.getSwitchDataFlow().saveGuardSwitch(locked);
//                        theft_switch.setChecked(locked);
//                    }
//                });

        RequestFactory.getGuardRequest().isAntiTheftOpened(
                new GuardRequest.LockStateCallBack() {
                    @Override
                    public void hasLocked(boolean locked) {
                        preventTheftBinding.preventTheftSwitch.setChecked(locked);
                        FlowFactory.getSwitchDataFlow().saveGuardSwitch(locked);
                    }

                    @Override
                    public void requestError(String error) {
                        logger.error(error);
                    }
                });
    }

    public void onEventMainThread(ReceiverData event) {
        if(ReceiverDataFlow.getGuardReceiveData(event)){
            FlowFactory.getSwitchDataFlow().saveGuardSwitch(event.getSwitchValue().equals("1"));
            theft_switch.setChecked(event.getSwitchValue().equals("1"));
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().register(this);
    }
}
