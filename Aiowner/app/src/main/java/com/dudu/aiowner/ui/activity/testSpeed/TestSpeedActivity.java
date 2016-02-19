package com.dudu.aiowner.ui.activity.testSpeed;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.activity.user.UserInfoActivity;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.workflow.RequestFactory;
import com.dudu.workflow.driving.DrivingRequest;

/**
 * Created by Administrator on 2016/2/2.
 */
public class TestSpeedActivity extends BaseActivity {

    private TextView textSpeedButton;
    private ImageView mImageView;
    private int[] images;
    private FrameLayout count_down;
    private RadioGroup acc_texting_radiogroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initEvent();

        /*android帧动画animation-list*/
        images = new AccImagesList().getImages();
        new TestingAnimation().play(mImageView, images, 5, this, count_down);

    }

    private void initEvent() {

        textSpeedButton.setOnClickListener(onClickListener);

        int radioButtonId = acc_texting_radiogroup.getCheckedRadioButtonId();
        switch (radioButtonId) {
            case R.id.acc_texting_100km_rb:
                //TODO
                break;
            case R.id.acc_texting_200km_rb:
                //TODO
                break;
            case R.id.acc_texting_300km_rb:
                //TODO
                break;
        }
    }

    private void initView() {
        textSpeedButton = (TextView) findViewById(R.id.start_test_speed);
        count_down = (FrameLayout) findViewById(R.id.count_down);
        mImageView = (ImageView) findViewById(R.id.check);
        acc_texting_radiogroup = (RadioGroup) findViewById(R.id.acc_texting_radiogroup);
    }

    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_test_speed, null);
    }

    public void startAccTesting(View view) {

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
}
