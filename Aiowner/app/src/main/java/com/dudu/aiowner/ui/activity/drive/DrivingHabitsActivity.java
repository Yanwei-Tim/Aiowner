package com.dudu.aiowner.ui.activity.drive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.databinding.ActivityDrivingHabitsBinding;
import com.dudu.aiowner.ui.activity.drive.adapter.DrivingHabitsInfoAdatper;
import com.dudu.aiowner.ui.base.BaseActivity;

/**
 * Created by Administrator on 2016/2/2.
 */
public class DrivingHabitsActivity extends BaseActivity {
    private ActivityDrivingHabitsBinding activityDrivingHabitsBinding;
    private DrivingHabitsInfoAdatper drivingHabitsInfoAdatper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drivingHabitsInfoAdatper = new DrivingHabitsInfoAdatper(null);

        activityDrivingHabitsBinding = ActivityDrivingHabitsBinding.bind(childView);

        activityDrivingHabitsBinding.recyclerView.setHasFixedSize(true);
        activityDrivingHabitsBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        activityDrivingHabitsBinding.recyclerView.setAdapter(drivingHabitsInfoAdatper);
    }

    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_driving_habits, null);
    }

    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("驾驶习惯");
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

    public void startDriveHabitsInfoAcitity(View view){
        startActivity(new Intent(DrivingHabitsActivity.this, DrvingHabitsInfoAcitity.class));
    }
}
