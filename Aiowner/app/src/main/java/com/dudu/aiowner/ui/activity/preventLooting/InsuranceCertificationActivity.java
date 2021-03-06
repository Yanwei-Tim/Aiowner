package com.dudu.aiowner.ui.activity.preventLooting;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.activity.user.UserInfoActivity;
import com.dudu.aiowner.ui.base.BaseActivity;

/**
 * Created by Administrator on 2016/2/2.
 */
public class InsuranceCertificationActivity extends BaseActivity {
    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_insurance_certification, null);
    }

    public void userInfo(View view) {
        startActivity(new Intent(InsuranceCertificationActivity.this, UserInfoActivity.class));
    }

    public void startPreventLootingActivity(View view) {
        startActivity(new Intent(InsuranceCertificationActivity.this, PreventLootingActivity.class));
    }

    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("车辆防劫");
        observableFactory.getTitleObservable().userIcon.set(true);
        observableFactory.getCommonObservable().hasBottomIcon.set(false);
        super.onResume();
    }
}
