package com.dudu.aiowner.ui.activity.drive;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.base.BaseActivity;

public class DrvingHabitsInfoAcitity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_drving_habits_info_acitity, null);
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
}
