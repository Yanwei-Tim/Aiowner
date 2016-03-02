package com.dudu.aiowner.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.databinding.ActivityOldGesturePswBinding;
import com.dudu.aiowner.ui.activity.preventTheft.GestureLockViewGroup;
import com.dudu.aiowner.ui.base.BaseActivity;

/**
 * Created by sunny_zhang on 2016/3/2.
 */
public class OldGesturePswActivity extends BaseActivity {

    private ActivityOldGesturePswBinding oldGesturePswBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        oldGesturePswBinding = ActivityOldGesturePswBinding.bind(childView);
        //设置手势密码
        oldGesturePswBinding.gestureLockViewGroupView.setAnswer(new int[]{1, 2, 3, 4, 5});
        initEvent();
    }

    private void initEvent() {

        oldGesturePswBinding.gestureLockViewGroupView.setOnGestureLockViewListener(
                new GestureLockViewGroup.OnGestureLockViewListener() {
                    @Override
                    public void onBlockSelected(int cId) {
                    }

                    @Override
                    public void onGestureEvent(boolean matched) {

//                        startActivity(new Intent(OldGesturePswActivity.this, GesturePswActivity.class));
//                        注释了不用密码
                        if (matched) {
                            //密码正确
                            startActivity(new Intent(OldGesturePswActivity.this, NewGesturePswActivity.class));

                        } else {
                            //密码错误
                        }
                    }

                    @Override
                    public void onUnmatchedExceedBoundary() {
                        //设置几次错误的回调
                        oldGesturePswBinding.gestureLockViewGroupView.setUnMatchExceedBoundary(5);
                    }
                });
    }

    @Override
    protected View getChildView() {

        return LayoutInflater.from(this).inflate(R.layout.activity_old_gesture_psw, null);
    }

    public void startConfirmIdentifyingCode(View view) {

        startActivity(new Intent(OldGesturePswActivity.this, ConfirmIdentifyingCodeActivity.class));
    }

    @Override
    protected void onResume() {

        observableFactory.getTitleObservable().titleText.set("");
        observableFactory.getTitleObservable().userIcon.set(false);
        observableFactory.getTitleObservable().hasBackGround.set(true);
        observableFactory.getTitleObservable().hasUserBackButton.set(true);
        observableFactory.getCommonObservable().hasBottomIcon.set(false);
        observableFactory.getCommonObservable().hasUserBackground.set(true);
        super.onResume();
    }
}
