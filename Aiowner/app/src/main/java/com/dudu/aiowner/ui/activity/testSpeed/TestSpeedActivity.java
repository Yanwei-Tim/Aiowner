package com.dudu.aiowner.ui.activity.testSpeed;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        textSpeedButton = (TextView)findViewById(R.id.start_test_speed);
        textSpeedButton.setOnClickListener(onClickListener);
    }

    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_test_speed, null);
    }

    public void startTestSpeed(View view) {

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.start_test_speed:
                    RequestFactory.getDrivingRequest().startacceleratedTesting(1, new DrivingRequest.RequestCallback() {
                        @Override
                        public void requestSuccess(boolean success) {
                            if(success) {
                                Toast.makeText(TestSpeedActivity.this, "车辆已开始测试", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(TestSpeedActivity.this, "请求失败，请重试", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    break;
            }
        }
    };
}
