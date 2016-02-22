package com.dudu.aiowner.ui.activity.preventTheft;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.databinding.ActivityInitGesturePswBinding;
import com.dudu.aiowner.ui.activity.preventTheft.observable.InitGesturePswObservable;
import com.dudu.aiowner.ui.activity.user.UserInfoActivity;
import com.dudu.aiowner.ui.base.BaseActivity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sunny_zhang on 2016/2/4.
 */
public class InitGesturePswActivity extends BaseActivity {

    private Logger logger = LoggerFactory.getLogger("PreventTheftActivity");
    private ActivityInitGesturePswBinding initGesturePswBinding;
    private InitGesturePswObservable initGesturePswObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initGesturePswObservable = new InitGesturePswObservable();

        initGesturePswBinding = ActivityInitGesturePswBinding.bind(childView);
        initGesturePswBinding.setTitle(observableFactory.getTitleObservable());
        initGesturePswBinding.setInitGesturePswPage(initGesturePswObservable);
        initGesturePswBinding.gestureLockViewGroupView.setAnswer(new int[]{1, 2, 3, 4, 5});

        initEvent();

    }

    private void initEvent() {
        initGesturePswBinding.gestureLockViewGroupView.setOnGestureLockViewListener(
                new GestureLockViewGroup.OnGestureLockViewListener() {
                    @Override
                    public void onBlockSelected(int cId) {
                    }

                    @Override
                    public void onGestureEvent(boolean matched) {
                        if (matched) {
                            //解锁成功
                            startActivity(new Intent(InitGesturePswActivity.this, PreventTheftActivity.class));

                        } else {
                            //解锁失败
                        }
                    }

                    @Override
                    public void onUnmatchedExceedBoundary() {
                        //设置几次错误的回调
                        initGesturePswBinding.gestureLockViewGroupView.setUnMatchExceedBoundary(5);
                    }
                });

    }

    @Override
    protected View getChildView() {

        return LayoutInflater.from(this).inflate(R.layout.activity_init_gesture_psw, null);
    }

    public void startUnlockSuccess(View view) {

        startActivity(new Intent(InitGesturePswActivity.this, UnlockSuccessActivity.class));
    }

    public void startIndentifyingDigitalPsw(View view) {

        startActivity(new Intent(InitGesturePswActivity.this, InitDigitalPswActivity.class));
    }

    public void startFogetPreventTheftPsw(View view) {

//        startActivity(new Intent(InitGesturePswActivity.this, ForgetPreventTheftPswActitivy.class));
    }

    public void userInfo(View view) {
        startActivity(new Intent(InitGesturePswActivity.this, UserInfoActivity.class));
    }

    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("车辆防盗");
        observableFactory.getTitleObservable().userIcon.set(true);
        observableFactory.getCommonObservable().hasBottomIcon.set(false);
        super.onResume();
    }
}
