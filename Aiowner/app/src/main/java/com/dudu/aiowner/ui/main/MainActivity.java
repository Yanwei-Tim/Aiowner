package com.dudu.aiowner.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.databinding.ActivityMainBinding;
import com.dudu.aiowner.ui.activity.preventTheft.PreventTheftActivity;
import com.dudu.aiowner.ui.base.observable.ObservableFactory;
import com.dudu.aiowner.ui.main.observable.MainObservable;
import com.dudu.aiowner.utils.ActivitiesManager;

/**
 * Created by sunny_zhang on 2016/1/27.
 */
public class MainActivity extends Activity {
    private MainObservable mainObservable;
    private ActivityMainBinding activityMainBinding;
    protected ObservableFactory observableFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitiesManager.getInstance().addActivity(this);
        mainObservable = new MainObservable();
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        observableFactory = ObservableFactory.creatInstance();
        activityMainBinding.setTitle(observableFactory.getTitleObservable());
        activityMainBinding.setMainPage(mainObservable);
    }

    public void startPreventTheft(View view) {

        startActivity(new Intent(MainActivity.this, PreventTheftActivity.class));
    }

    public void startPreventLooting(View view) {

        startActivity(new Intent(MainActivity.this, PreventTheftActivity.class));
    }

    public void startTestSpeed(View view) {

        startActivity(new Intent(MainActivity.this, PreventTheftActivity.class));
    }

    public void startDrive(View view) {

        startActivity(new Intent(MainActivity.this, PreventTheftActivity.class));
    }

    public void startMaintenanceAssistant(View view) {

        startActivity(new Intent(MainActivity.this, PreventTheftActivity.class));
    }

    public void userInfo(View view){
        startActivity(new Intent(MainActivity.this, PreventTheftActivity.class));
    }

    public void backLastPage(View view) {
        finish();
    }

    @Override
    protected void onResume() {

        observableFactory.getTitleObservable().titleText.set("");
        observableFactory.getTitleObservable().titleLogo.set(true);
        observableFactory.getTitleObservable().userIcon.set(true);
        super.onResume();
    }
}
