package com.dudu.aiowner.ui.main;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.databinding.ActivityMainBinding;
import com.dudu.aiowner.ui.activity.drive.DrivingHabitsActivity;
import com.dudu.aiowner.ui.activity.maintenanceAssistant.MaintenanceAssistantActivity;
import com.dudu.aiowner.ui.activity.preventLooting.InsuranceCertificationActivity;
import com.dudu.aiowner.ui.activity.preventTheft.OwnersCredentialsUploadActivity;
import com.dudu.aiowner.ui.activity.testSpeed.SelectCarActivity;
import com.dudu.aiowner.ui.activity.user.UserInfoActivity;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.aiowner.ui.main.observable.MainObservable;


/**
 * Created by sunny_zhang on 2016/1/27.
 */
public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    private MainObservable mainObservable;
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        observableFactory.setToMainUi();

        mainObservable = new MainObservable();
        activityMainBinding = ActivityMainBinding.bind(childView);
        activityMainBinding.setMainPage(mainObservable);

        activityMainBinding.setTitle(observableFactory.getTitleObservable());
        activityMainBinding.setMainPage(mainObservable);
    }



    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_main, null);
    }

    public void startOwnersCredentialsUpload(View view) {

        startActivity(new Intent(MainActivity.this, OwnersCredentialsUploadActivity.class));
    }

    public void startPreventLooting(View view) {

        startActivity(new Intent(MainActivity.this, InsuranceCertificationActivity.class));
    }

    public void startTestSpeed(View view) {

        startActivity(new Intent(MainActivity.this, SelectCarActivity.class));
    }

    public void startDrive(View view) {

        startActivity(new Intent(MainActivity.this, DrivingHabitsActivity.class));
    }

    public void startMaintenanceAssistant(View view) {

        startActivity(new Intent(MainActivity.this, MaintenanceAssistantActivity.class));
    }

    public void userInfo(View view) {
        startActivity(new Intent(MainActivity.this, UserInfoActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
