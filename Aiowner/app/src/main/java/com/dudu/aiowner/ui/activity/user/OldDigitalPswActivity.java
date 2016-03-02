package com.dudu.aiowner.ui.activity.user;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.base.BaseActivity;

/**
 * Created by sunny_zhang on 2016/2/4.
 */
public class OldDigitalPswActivity extends BaseActivity {
    @Override
    protected View getChildView() {

        return LayoutInflater.from(this).inflate(R.layout.activity_old_digital_psw, null);
    }

    public void startNewDigitalPsw(View view) {

        startActivity(new Intent(OldDigitalPswActivity.this, NewDigitalPswActivity.class));
    }

    public void startConfirmIdentifyingCode(View view) {

        startActivity(new Intent(OldDigitalPswActivity.this, ConfirmIdentifyingCodeActivity.class));
    }

    @Override
    protected void onResume() {

        observableFactory.getTitleObservable().titleText.set("");
        observableFactory.getTitleObservable().userIcon.set(false);
        observableFactory.getTitleObservable().hasBackGround.set(true);
        observableFactory.getTitleObservable().hasUserBackButton.set(true);
        observableFactory.getCommonObservable().hasBottomIcon.set(false);
        observableFactory.getCommonObservable().hasUserBackground.set(true);
        super.onResume();
    }

}
