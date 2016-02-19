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

import rx.functions.Action1;

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
        reflashSwitch();
        super.onResume();
    }

    private void reflashSwitch(){
//        looting_switch.setChecked(RobberyFlow.getRobbeyState());
        ObservableFactory.getRobberyFlow()
                .subscribe(new Action1<ReceiverData>() {
                    @Override
                    public void call(ReceiverData receiverData) {
                        looting_switch.setChecked(!receiverData.getSwitch0Value().equals("1"));
                    }
                });
        RequestFactory.getRobberyRequest().isCarRobbed(new RobberyRequest.CarRobberdCallback() {
            @Override
            public void hasRobbed(boolean robbed) {
//                RobberyFlow.saveRobbeyState(robbed);
                looting_switch.setChecked(!robbed);
            }

            @Override
            public void requestError(String error) {

            }
        });
    }

    private void initEvent() {
        looting_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    RequestFactory.getRobberyRequest().closeAntiRobberyMode(new RobberyRequest.CloseRobberyModeCallback() {
                        @Override
                        public void closeSuccess(boolean success) {
                            if (success) {
//                                RobberyFlow.saveRobbeyState(false);
//                                looting_switch.setBackgroundResource(R.drawable.theft_lock_on);
                                Toast.makeText(getApplicationContext(), "请求关闭防劫模式成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "请求关闭防劫模式失败", Toast.LENGTH_SHORT).show();
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
}
