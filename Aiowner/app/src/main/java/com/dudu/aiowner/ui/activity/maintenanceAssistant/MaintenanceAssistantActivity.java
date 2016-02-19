package com.dudu.aiowner.ui.activity.maintenanceAssistant;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.activity.login.ForgetLoginPswActitivy;
import com.dudu.aiowner.ui.activity.preventTheft.SetDigitalPswActivity;
import com.dudu.aiowner.ui.activity.user.UserInfoActivity;
import com.dudu.aiowner.ui.base.BaseActivity;

/**
 * Created by Administrator on 2016/2/2.
 */
public class MaintenanceAssistantActivity extends BaseActivity {

    private int[] images;
    private FrameLayout count_down;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          /*android帧动画animation-list*/
        mImageView = (ImageView) findViewById(R.id.health_index_water_iv);
        images = new WaterImagesList().getImages();
//
        new WaterAnimation().play(mImageView, images, 5, this, count_down);
    }

    @Override
    protected View getChildView() {

        return LayoutInflater.from(this).inflate(R.layout.activity_maintenance_assistant, null);
    }

    public void userInfo(View view) {
        startActivity(new Intent(MaintenanceAssistantActivity.this, UserInfoActivity.class));
    }

    public void startDigitalPassword(View view) {

        startActivity(new Intent(MaintenanceAssistantActivity.this, SetDigitalPswActivity.class));
    }

    public void startFogetPassword(View view) {

        startActivity(new Intent(MaintenanceAssistantActivity.this, ForgetLoginPswActitivy.class));
    }


    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("保养助手");
        observableFactory.getTitleObservable().userIcon.set(true);
        observableFactory.getCommonObservable().hasBottomIcon.set(false);
        super.onResume();
    }
}
