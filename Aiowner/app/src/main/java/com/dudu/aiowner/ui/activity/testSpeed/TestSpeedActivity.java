package com.dudu.aiowner.ui.activity.testSpeed;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dudu.aiowner.R;
import com.dudu.aiowner.commonlib.commonutils.RiseNumberTextView;
import com.dudu.aiowner.ui.activity.user.UserInfoActivity;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.aiowner.ui.base.animation.AnimationView;
import com.dudu.workflow.common.ObservableFactory;
import com.dudu.workflow.common.RequestFactory;
import com.dudu.workflow.driving.DrivingRequest;

import java.util.Random;

/**
 * Created by Administrator on 2016/2/2.
 */
public class TestSpeedActivity extends BaseActivity {

    private TextView textSpeedButton;
    private ImageView mImageView;
    private int[] images;
    private FrameLayout count_down;
    private RadioGroup acc_texting_radiogroup;
    private RelativeLayout ani_rl;
    private ImageView acc_testing_anim;
    private AnimationView animationView;
    private RadioButton acc_100km;
    private RadioButton acc_200km;
    private RadioButton acc_300km;
    private AnimationDrawable animationDrawable;
    private RiseNumberTextView acc_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initEvent();
    }

    private void initEvent() {

    }

    private void initView() {
        textSpeedButton = (TextView) findViewById(R.id.start_test_speed);
        count_down = (FrameLayout) findViewById(R.id.count_down);
//        ani_rl = (RelativeLayout) findViewById(R.id.acc_testing_anim);

        acc_testing_anim = (ImageView) findViewById(R.id.acc_testing_anim);
        animationDrawable = (AnimationDrawable) acc_testing_anim.getBackground();
        acc_texting_radiogroup = (RadioGroup) findViewById(R.id.acc_texting_radiogroup);

        acc_100km = (RadioButton) findViewById(R.id.acc_texting_100km_rb);
        acc_200km = (RadioButton) findViewById(R.id.acc_texting_200km_rb);
        acc_300km = (RadioButton) findViewById(R.id.acc_texting_300km_rb);

        // 获取到RiseNumberTextView对象
        acc_result = (RiseNumberTextView) findViewById(R.id.acc_testing_result_tv);
    }

    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_test_speed, null);
    }

    public void startAccTesting(View view) {
        if (!(acc_100km.isChecked() || acc_200km.isChecked() || acc_300km.isChecked())) {
            Toast.makeText(getApplicationContext(), "请选择公里数", Toast.LENGTH_SHORT).show();
        } else if (!animationDrawable.isRunning()) {
            //false为动画循环播放
            animationDrawable.setOneShot(false);
            animationDrawable.start();

            //为了演示用，如果能获取到加速结果数据，则不用这句
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
        ObservableFactory.getAccTestFlow()
                .subscribe(accTestData -> {

                    //获取加速测试结果
                    Log.v("acc", "accTestData.getAccTotalTime()" + accTestData.getAccTotalTime());

                    setupData(Float.valueOf(accTestData.getAccTotalTime()));

                    //加速结果单位
                    accTestData.getAccType();

                    //加速结果时间
                    accTestData.getDateTime();
                });
        observableFactory.getTitleObservable().titleText.set("加速测试");
        observableFactory.getTitleObservable().userIcon.set(true);
        observableFactory.getCommonObservable().hasBottomIcon.set(false);
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (animationView != null) {
            animationView.stopAnim();
        }
        super.onPause();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.start_test_speed:
                    RequestFactory.getDrivingRequest().startacceleratedTesting(1, new DrivingRequest.RequestCallback() {
                        @Override
                        public void requestSuccess(boolean success) {
                            if (success) {
                                Toast.makeText(getApplicationContext(), "车辆已开始测试", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "请求失败，请重试", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    break;
            }
        }
    };

    private void setupData(float resultData) {

        // 设置数据
        acc_result.withNumber(resultData);
        // 设置动画播放时间
        acc_result.setDuration(5000);
        acc_result.start();

        acc_result.setOnEndListener(new RiseNumberTextView.EndListener() {
            @Override
            public void onEndFinish() {
                animationDrawable.stop();
            }
        });
    }
}
