package com.dudu.aiowner.ui.activity.preventTheft;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.databinding.ActivityGesturePswUnlockBinding;
import com.dudu.aiowner.ui.activity.preventTheft.observable.GesturePswUnlockObservable;
import com.dudu.aiowner.ui.activity.user.UserInfoActivity;
import com.dudu.aiowner.ui.base.BaseActivity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sunny_zhang on 2016/2/4.
 */
public class GesturePswUnlockActivity extends BaseActivity {

    private Logger logger = LoggerFactory.getLogger("PreventTheftActivity");
    private ActivityGesturePswUnlockBinding gesturePswUnlockBinding;
    private GesturePswUnlockObservable gesturePswUnlockObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gesturePswUnlockObservable = new GesturePswUnlockObservable();

        gesturePswUnlockBinding = ActivityGesturePswUnlockBinding.bind(childView);
        gesturePswUnlockBinding.setTitle(observableFactory.getTitleObservable());
        gesturePswUnlockBinding.setGesturePswUnlockPage(gesturePswUnlockObservable);

        //设置手势密码
        gesturePswUnlockBinding.gestureLockViewGroupView.setAnswer(new int[]{1, 2, 3, 4, 5});

        initEvent();

    }

    private void initEvent() {
        gesturePswUnlockBinding.gestureLockViewGroupView.setOnGestureLockViewListener(
                new GestureLockViewGroup.OnGestureLockViewListener() {
                    @Override
                    public void onBlockSelected(int cId) {
                    }

                    @Override
                    public void onGestureEvent(boolean matched) {


//                        startActivity(new Intent(GesturePswUnlockActivity.this, PreventTheftActivity.class));
//                        注释了不用密码
                        if (matched) {
                            //解锁成功
                            startActivity(new Intent(GesturePswUnlockActivity.this, PreventTheftActivity.class));

                        } else {
                            //解锁失败
                        }
                    }

                    @Override
                    public void onUnmatchedExceedBoundary() {
                        //设置几次错误的回调
                        gesturePswUnlockBinding.gestureLockViewGroupView.setUnMatchExceedBoundary(5);
                    }
                });

    }

    @Override
    protected View getChildView() {

        return LayoutInflater.from(this).inflate(R.layout.activity_gesture_psw_unlock, null);
    }

    public void startUnlockSuccess(View view) {

        startActivity(new Intent(GesturePswUnlockActivity.this, UnlockSuccessActivity.class));
    }

    public void startIndentifyingDigitalPsw(View view) {

        startActivity(new Intent(GesturePswUnlockActivity.this, DigitalPswUnlockActivity.class));
    }

    public void startFogetPreventTheftPsw(View view) {

//        startActivity(new Intent(GesturePswUnlockActivity.this, ForgetPreventTheftPswActitivy.class));
    }

    public void userInfo(View view) {
        startActivity(new Intent(GesturePswUnlockActivity.this, UserInfoActivity.class));
    }

    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("车辆防盗");
        observableFactory.getTitleObservable().userIcon.set(true);
        observableFactory.getCommonObservable().hasBottomIcon.set(false);
        super.onResume();
    }
}
