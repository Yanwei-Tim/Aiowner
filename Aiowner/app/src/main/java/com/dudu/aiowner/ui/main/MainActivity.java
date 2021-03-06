package com.dudu.aiowner.ui.main;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.dudu.aiowner.R;
import com.dudu.aiowner.databinding.ActivityMainBinding;
import com.dudu.aiowner.rest.model.TheftStatusResponse;
import com.dudu.aiowner.ui.activity.bind.DeviceBindActivity;
import com.dudu.aiowner.ui.activity.bind.T;
import com.dudu.aiowner.ui.activity.drive.DrivingHabitsActivity;
import com.dudu.aiowner.ui.activity.maintenanceAssistant.MaintenanceAssistantActivity;
import com.dudu.aiowner.ui.activity.preventLooting.PreventLootingActivity;
import com.dudu.aiowner.ui.activity.preventLooting.PreventLootingControlActivity;
import com.dudu.aiowner.ui.activity.preventTheft.OwnersCredentialsUploadActivity;
import com.dudu.aiowner.ui.activity.preventTheft.PreventTheftActivity;
import com.dudu.aiowner.ui.activity.testSpeed.TestSpeedActivity;
import com.dudu.aiowner.ui.activity.user.UserInfoActivity;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.aiowner.ui.main.observable.MainObservable;
import com.dudu.workflow.bind.BindServiceImpl;
import com.dudu.workflow.bind.SimpleBindListener;
import com.dudu.workflow.common.CommonParams;
import com.dudu.workflow.common.FlowFactory;
import com.dudu.workflow.common.RequestFactory;
import com.dudu.workflow.guard.GuardRequest;

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
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        observableFactory.setToMainUi();

        mainObservable = new MainObservable();
        activityMainBinding = ActivityMainBinding.bind(childView);
        activityMainBinding.setMainPage(mainObservable);

        activityMainBinding.setTitle(observableFactory.getTitleObservable());

        startAnimation();
        resetData();

        findViewById(R.id.base_view).setBackgroundColor(getResources().getColor(R.color.aiowner));
    }

    private void startAnimation() {

        //android帧动画animation-list
        mImageView = (ImageView) findViewById(R.id.car);
        images = new CarImagesList().getImages();

        //启动动画
        new CarAnimation().play(mImageView, images, 10, this, count_down);
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

    /**防盗入口*/
    public void startPreventTheft(View view) {

        BindServiceImpl.getBindStatus(CommonParams.getInstance().getUserName(), "android", new SimpleBindListener() {
            @Override
            public void onGetBindStatus(boolean isBind, String obeId) {
                if (isBind) {
                    //如果已经绑定
                    RequestFactory.getGuardRequest().getTheftStatus(new GuardRequest.TheftStatusCallBack() {
                        @Override
                        public void requestSucceed(TheftStatusResponse response) {
                            Log.d("checkTheftStates", "audit_state:" + response.audit_state);
                            switch (response.audit_state) {
                                case 0:
                                    startActivity(new Intent(MainActivity.this, OwnersCredentialsUploadActivity.class));
                                    break;
                                case 1:
//                                    startActivity(new Intent(MainActivity.this, PreventTheftActivity.class));
                                    startActivity(new Intent(MainActivity.this, OwnersCredentialsUploadActivity.class));
//                                    startActivity(new Intent(MainActivity.this, OwnersReviewActivity.class));
                                    break;
                                case 2:
                                    startActivity(new Intent(MainActivity.this, PreventTheftActivity.class));
                                    break;
                                case 3:
                                    startActivity(new Intent(MainActivity.this, OwnersCredentialsUploadActivity.class));
                            }
                        }

                        @Override
                        public void requestError(String error) {
                            Log.e("checkTheftStates", "error:" + error);
                        }
                    });
                } else {
                    //未绑定设备逻辑
                    showMaterialDialog(getString(R.string.dialog_default_title), getString(R.string.dialog_content_unbind), getString(R.string.dialog_bind_bt_text), v -> {
                        DeviceBindActivity.launch(MainActivity.this);
                        dismissDialog();
                    }, v -> {
                        dismissDialog();
                    }, dialog -> {

                    });
                }
            }

            @Override
            public void onFailed(String msg) {
                T.show(MainActivity.this, msg);
            }
        });
    }

    /**防劫入口*/
    public void startPreventLooting(View view) {
        FlowFactory.getSwitchDataFlow().getRobberyState()
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean hasRobbed) {
                        openRobberyActivity(hasRobbed);
                    }
                });
//        RequestFactory.getRobberyRequest().isCarRobbed(new RobberyRequest.CarRobberdCallback() {
//            @Override
//            public void hasRobbed(boolean robbed) {
//                openRobberyActivity(robbed);
//            }
//
//            @Override
//            public void requestError(String error) {
//
//            }
//        });
    }

    public void openRobberyActivity(boolean hasRobbed) {
        if (hasRobbed) {
            startActivity(new Intent(MainActivity.this, PreventLootingControlActivity.class));
        } else {
            startActivity(new Intent(MainActivity.this, PreventLootingActivity.class));
        }
    }

    /**测速入口*/
    public void openTestSpeed(View view) {

        startActivity(new Intent(MainActivity.this, TestSpeedActivity.class));
    }

    /**驾驶入口*/
    public void openDrive(View view) {

        startActivity(new Intent(MainActivity.this, DrivingHabitsActivity.class));
    }

    public void openMaintenanceAssistant(View view) {

        startActivity(new Intent(MainActivity.this, MaintenanceAssistantActivity.class));
    }

    public void openUserInfo(View view) {

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
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                mExitTime = System.currentTimeMillis();

            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
