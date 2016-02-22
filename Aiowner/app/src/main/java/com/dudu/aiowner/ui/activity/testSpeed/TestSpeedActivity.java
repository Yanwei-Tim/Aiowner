package com.dudu.aiowner.ui.activity.testSpeed;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.commonlib.commonutils.RiseNumberTextView;
import com.dudu.aiowner.commonlib.model.ReceiverData;
import com.dudu.aiowner.databinding.ActivityTestSpeedBinding;
import com.dudu.aiowner.ui.activity.testSpeed.observable.TestSpeedObservable;
import com.dudu.aiowner.ui.activity.user.UserInfoActivity;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.workflow.common.RequestFactory;
import com.dudu.workflow.driving.DrivingRequest;
import com.dudu.workflow.receiver.ReceiverDataFlow;
import com.dudu.workflow.switchmessage.AccTestData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/2/2.
 */
public class TestSpeedActivity extends BaseActivity {

    private AnimationDrawable animationDrawable;
    private TestSpeedObservable testSpeedObservable;
    private ActivityTestSpeedBinding testSpeedBinding;
    private Logger logger = LoggerFactory.getLogger("TestSpeedActivity");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testSpeedObservable = new TestSpeedObservable();
        testSpeedBinding = ActivityTestSpeedBinding.bind(childView);
        testSpeedBinding.setTestSpeedPage(testSpeedObservable);

        testSpeedBinding.setTitle(observableFactory.getTitleObservable());

        //设置加速测试环动画
        animationDrawable = (AnimationDrawable) testSpeedBinding.accTestingAnim.getBackground();
    }

    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_test_speed, null);
    }

    public void startAccTesting(View view) {
        RequestFactory.getDrivingRequest().startacceleratedTesting(1, new DrivingRequest.RequestCallback() {
            @Override
            public void requestSuccess(boolean success) {
                if (success) {
                    logger.debug("加速测试请求成功");
                } else {
                    logger.debug("加速测试请求失败");
                }
            }
        });
        if (!animationDrawable.isRunning()) {
            //false为动画循环播放
            animationDrawable.setOneShot(false);
            //1、启动加速测试环动画
            animationDrawable.start();

            /**
             * 2、同时启动加速结果数字动画
             *    为了演示用，使用随机数数据
             */
            setupData(new Random().nextFloat());
        }
    }

    public void userInfo(View view) {
        startActivity(new Intent(TestSpeedActivity.this, UserInfoActivity.class));
    }

    public void startHistoryRecords(View view) {
        startActivity(new Intent(TestSpeedActivity.this, HistoryRecordActivity.class));
    }

    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("加速测试");
        observableFactory.getTitleObservable().userIcon.set(true);
        observableFactory.getCommonObservable().hasBottomIcon.set(false);
        super.onResume();
    }

    public void onEventMainThread(ReceiverData event) {
        if(ReceiverDataFlow.getAccTestReceiverData(event)) {
            AccTestData accTestData = ReceiverDataFlow.getReceiveDataFlow(event);
            setupData(Float.valueOf(accTestData.getAccTotalTime()));
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().register(this);
    }

    private void setupData(float resultData) {

        // 设置数据
        testSpeedBinding.accTestingResultTv.withNumber(resultData);
        // 设置动画播放时间
        testSpeedBinding.accTestingResultTv.setDuration(5000);
        testSpeedBinding.accTestingResultTv.start();

        testSpeedBinding.accTestingResultTv.setOnEndListener(new RiseNumberTextView.EndListener() {
            @Override
            public void onEndFinish() {

                animationDrawable.stop();
                testSpeedObservable.setAccTestingData((float) (Math.round(resultData * 100)) / 100);
                testSpeedBinding.historyRecords.setClickable(true);
                testSpeedBinding.acc100kmRb.setClickable(true);
                testSpeedBinding.acc200kmRb.setClickable(true);
                testSpeedBinding.acc300kmRb.setClickable(true);
                observableFactory.getTitleObservable().backBtnClick.set(true);
                observableFactory.getTitleObservable().userIconClick.set(true);
            }
        });
        if (testSpeedBinding.accTestingResultTv.isRunning() || animationDrawable.isRunning()) {
            testSpeedBinding.historyRecords.setClickable(false);
            testSpeedBinding.acc100kmRb.setClickable(false);
            testSpeedBinding.acc200kmRb.setClickable(false);
            testSpeedBinding.acc300kmRb.setClickable(false);
            observableFactory.getTitleObservable().backBtnClick.set(false);
            observableFactory.getTitleObservable().userIconClick.set(false);
        }
    }
}
