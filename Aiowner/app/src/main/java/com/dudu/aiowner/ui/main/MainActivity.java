package com.dudu.aiowner.ui.main;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.dudu.aiowner.R;
import com.dudu.aiowner.databinding.ActivityMainBinding;
import com.dudu.aiowner.ui.activity.drive.DrivingHabitsActivity;
import com.dudu.aiowner.ui.activity.maintenanceAssistant.MaintenanceAssistantActivity;
import com.dudu.aiowner.ui.activity.preventLooting.PreventLootingActivity;
import com.dudu.aiowner.ui.activity.preventLooting.PreventLootingControlActivity;
import com.dudu.aiowner.ui.activity.preventTheft.PreventTheftActivity;
import com.dudu.aiowner.ui.activity.testSpeed.SelectCarActivity;
import com.dudu.aiowner.ui.activity.user.UserInfoActivity;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.aiowner.ui.main.observable.MainObservable;
import com.dudu.workflow.RequestFactory;
import com.dudu.workflow.robbery.RobberyRequest;

/**
 * Created by sunny_zhang on 2016/1/27.
 */
public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    private MainObservable mainObservable;
    private ActivityMainBinding activityMainBinding;
    private int oilData;
    private int gasMileageData;
    private int totalMileageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        observableFactory.setToMainUi();

        mainObservable = new MainObservable();
        activityMainBinding = ActivityMainBinding.bind(childView);
        activityMainBinding.setMainPage(mainObservable);

        activityMainBinding.setTitle(observableFactory.getTitleObservable());
        activityMainBinding.setMainPage(mainObservable);

        resetData();

    }

    private void resetData() {

        oilData = Integer.parseInt(mainObservable.remainingOil.get().replace("%", ""));
        gasMileageData = Integer.parseInt(mainObservable.gasMileage.get().replace("lr", ""));
        totalMileageData = Integer.parseInt(mainObservable.totalMileage.get());
    }


    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_main, null);
    }

//    public void startOwnersCredentialsUpload(View view) {
//
//        startActivity(new Intent(MainActivity.this, OwnersCredentialsUploadActivity.class));
//    }

    public void startPreventTheft(View view) {
        startActivity(new Intent(MainActivity.this, PreventTheftActivity.class));
    }

    public void startPreventLooting(View view) {
        RequestFactory.getRobberyRequest().isCarRobbed(new RobberyRequest.CarRobberdCallback() {

            @Override
            public void hasRobbed(boolean success) {
                if (success) {
                    Toast.makeText(getApplication(), "已触发防劫模式，进入防劫控制中心", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, PreventLootingControlActivity.class));
                } else {
                    Toast.makeText(getApplication(), "进入防劫模式设置", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, PreventLootingActivity.class));
                }
            }

            @Override
            public void requestError(String error) {
                Log.d("MainActivity", error);
                Toast.makeText(getApplication(), "请求防劫状态失败", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(MainActivity.this, PreventLootingActivity.class));
            }
        });
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

        String resultOilData = "" + oilData--;
        String resultgasMileageData = "" + gasMileageData--;
        String resulttotalMileageData = "" + totalMileageData--;

        mainObservable.setRemainingOil(resultOilData);
        mainObservable.setGasMileage(resultgasMileageData);
        mainObservable.setTotalMileage(resulttotalMileageData);

        super.onResume();
    }
}
