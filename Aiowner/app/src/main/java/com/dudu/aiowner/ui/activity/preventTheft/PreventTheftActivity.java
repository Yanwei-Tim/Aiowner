package com.dudu.aiowner.ui.activity.preventTheft;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.commonlib.model.ReceiverData;
import com.dudu.aiowner.databinding.ActivityPreventTheftBinding;
import com.dudu.aiowner.rest.model.TheftStatusResponse;
import com.dudu.aiowner.ui.activity.user.UserInfoActivity;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.aiowner.ui.base.animation.AnimationView;
import com.dudu.workflow.common.FlowFactory;
import com.dudu.workflow.common.RequestFactory;
import com.dudu.workflow.guard.GuardRequest;
import com.dudu.workflow.receiver.ReceiverDataFlow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.greenrobot.event.EventBus;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;

/**
 * Created by sunny_zhang on 2016/2/3.
 */
public class PreventTheftActivity extends BaseActivity {

    private ActivityPreventTheftBinding preventTheftBinding;
    private AnimationView animView;
    private boolean stopAnim = false;
    private Handler handler = new AnimHandler();

    private Logger logger = LoggerFactory.getLogger("PreventTheftActivity");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preventTheftBinding = ActivityPreventTheftBinding.bind(childView);

        EventBus.getDefault().register(this);
    }

    public void theftSwitchClick(View view) {

        boolean isChecked = preventTheftBinding.theftSwitch.isChecked();
        checkSwitch(isChecked);
    }

    private void checkSwitch(boolean isChecked) {

        if (isChecked) {
//            preventTheftBinding.theftSwitch.setVisibility(View.INVISIBLE);
            //播放lock动画
//            startLockAnim();
            //保存开关状态
            FlowFactory.getSwitchDataFlow().saveGuardSwitch(isChecked);
            //请求网络
            stopAnim = false;
            RequestFactory.getGuardRequest().lockCar(
                    new GuardRequest.LockStateCallBack() {
                        @Override
                        public void hasLocked(boolean locked) {
                            if (locked) {
                                Log.d("checkSwitch", "请求打开防盗模式成功");
                            } else {
                                Log.d("checkSwitch", "请求打开防盗模式失败");
                            }
                            stopAnim = true;
                            //设置开关状态
                            preventTheftBinding.theftSwitch.setChecked(isChecked);
                        }

                        @Override
                        public void requestError(String error) {
                            Log.e("checkSwitch", "hasLockedError:" + error);
                            stopAnim = true;
                        }
                    });
        }
        //
        else {
            preventTheftBinding.theftSwitch.setChecked(!isChecked);
            startActivity(new Intent(PreventTheftActivity.this, GesturePswUnlockActivity.class));

            RequestFactory.getGuardRequest().unlockCar(
                    new GuardRequest.UnlockCallBack() {
                        @Override
                        public void unlocked(boolean locked) {
                            if (locked) {
                                Log.d("checkSwitch", "请求关闭防盗模式成功");
                            } else {
                                Log.d("checkSwitch", "请求关闭防盗模式失败");
                            }
                        }

                        @Override
                        public void requestError(String error) {
                            Log.e("checkSwitch", "unlockedError:" + error);
                        }
                    });
        }
    }

    private void clearAnim() {

        if (preventTheftBinding.animLockRl != null && preventTheftBinding.animLockRl.getChildCount() != 0) {
            preventTheftBinding.animLockRl.removeAllViews();
            if (animView != null) {
                animView.stopAnim();
//                显示开关
                preventTheftBinding.theftSwitch.setVisibility(View.VISIBLE);

            }
        }
    }

    BehaviorSubject subject = BehaviorSubject.create();

    private void startLockAnim() {

        animView = new AnimationView(this, "lock", 1, 101);
        animView.setZOrderOnTop(true);
        animView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        preventTheftBinding.animLockRl.addView(animView);
        animView.setOnAnimPlayListener(new AnimationView.OnAnimPlayListener() {
            @Override
            public boolean play() {
                Log.d("checkSwitch", "stopAnim:" + stopAnim);
                if (stopAnim) {
                    subject.onNext(stopAnim);
                }
                return stopAnim;
            }
        });
        subject.subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean o) {
                if (o) {
                    handler.sendEmptyMessage(0);
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

    public void startLookCredentials(View view) {

        startActivity(new Intent(PreventTheftActivity.this, LookCredentialsActivity.class));
    }

    public void userInfo(View view) {

        startActivity(new Intent(PreventTheftActivity.this, UserInfoActivity.class));
    }


    private void reflashSwitch() {

        FlowFactory.getSwitchDataFlow().getGuardSwitch()
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        preventTheftBinding.theftSwitch.setChecked(aBoolean);
                    }
                });

//        RequestFactory.getGuardRequest().isAntiTheftOpened(
//                new GuardRequest.LockStateCallBack() {
//                    @Override
//                    public void hasLocked(boolean locked) {
//                        Log.e("reflashSwitch", "hasLocked:" + locked);
//                        preventTheftBinding.theftSwitch.setChecked(locked);
//                        FlowFactory.getSwitchDataFlow().saveGuardSwitch(locked);
//                    }
//
//                    @Override
//                    public void requestError(String error) {
//                        Log.e("reflashSwitch", "requestError:" + error);
//                    }
//                });
        RequestFactory.getGuardRequest().getTheftStatus(new GuardRequest.TheftStatusCallBack() {

            @Override
            public void getTheftStatus(TheftStatusResponse response) {

                int audit_state = response.audit_state;
                Log.d("checkTheftStates", "audit_state:" + audit_state);
                logger.debug("audit_desc:" + audit_state);
//                long resultCode = response.resultCode;
//                long resultMsg = response.resultMsg;
//                String audit_desc = response.audit_desc;
            }

            @Override
            public void requestError(String error) {
                Log.e("checkTheftStates", "error:" + error);
            }
        });

    }

    public void onEventMainThread(ReceiverData event) {
        if (ReceiverDataFlow.getGuardReceiveData(event)) {
            FlowFactory.getSwitchDataFlow().saveGuardSwitch(event.getSwitchValue().equals("1"));
            preventTheftBinding.theftSwitch.setChecked(event.getSwitchValue().equals("1"));
        }
    }

    @Override
    protected void onResume() {

        observableFactory.getTitleObservable().titleText.set("车辆防盗");
        observableFactory.getTitleObservable().userIcon.set(true);
        observableFactory.getCommonObservable().hasBottomIcon.set(false);
        reflashSwitch();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (animView != null) {
            animView.stopAnim();
        }
        EventBus.getDefault().unregister(this);
    }

    private class AnimHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clearAnim();
        }
    }


}
