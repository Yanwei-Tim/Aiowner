package com.dudu.aiowner.ui.activity.preventTheft;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.activity.user.UserInfoActivity;
import com.dudu.aiowner.ui.base.BaseActivity;

/**
 * Created by sunny_zhang on 2016/2/4.
 */
public class ConfirmDigitalPswActivity extends BaseActivity {
    @Override
    protected View getChildView() {

        return LayoutInflater.from(this).inflate(R.layout.activity_confirm_digital_psw, null);
    }

    public void startSetPreventTheftPswOk(View view) {

        startActivity(new Intent(ConfirmDigitalPswActivity.this, SetPreventTheftPswOkActivity.class));
    }


    @Override
    protected void onResume() {

        observableFactory.getTitleObservable().titleText.set("车辆防盗");
        observableFactory.getTitleObservable().userIcon.set(true);
        observableFactory.getCommonObservable().hasBottomIcon.set(false);
        super.onResume();
    }
    public void userInfo(View view) {
        startActivity(new Intent(ConfirmDigitalPswActivity.this, UserInfoActivity.class));
    }
}
