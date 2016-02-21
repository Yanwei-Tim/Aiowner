package com.dudu.aiowner.ui.main;


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

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
import com.dudu.workflow.common.FlowFactory;
import com.dudu.workflow.common.RequestFactory;
import com.dudu.workflow.robbery.RobberyRequest;

import rx.functions.Action1;

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

    private int[] images;
    private FrameLayout count_down;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        observableFactory.setToMainUi();

        mainObservable = new MainObservable();
        activityMainBinding = ActivityMainBinding.bind(childView);
        activityMainBinding.setMainPage(mainObservable);

        activityMainBinding.setTitle(observableFactory.getTitleObservable());
        activityMainBinding.setMainPage(mainObservable);

         /*android帧动画animation-list*/
        mImageView = (ImageView) findViewById(R.id.car);
        images = new CarImagesList().getImages();

        new CarAnimation().play(mImageView, images, 5, this, count_down);

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
        FlowFactory.getSwitchDataFlow().getRobberyState()
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean hasRobbed) {
                        openRobberyActivity(hasRobbed);
                    }
                });
        RequestFactory.getRobberyRequest().isCarRobbed(new RobberyRequest.CarRobberdCallback() {
            @Override
            public void hasRobbed(boolean robbed) {
                openRobberyActivity(robbed);
            }

            @Override
            public void requestError(String error) {

            }
        });
    }

    public void openRobberyActivity(boolean hasRobbed){
        if (hasRobbed) {
            startActivity(new Intent(MainActivity.this, PreventLootingControlActivity.class));
        } else {
            startActivity(new Intent(MainActivity.this, PreventLootingActivity.class));
        }
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
