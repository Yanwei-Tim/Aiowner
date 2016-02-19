package com.dudu.aiowner.ui.activity.preventTheft;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.activity.user.UserInfoActivity;
import com.dudu.aiowner.ui.base.BaseActivity;

/**
 * Created by sunny_zhang on 2016/1/29.
 */
public class ForgetPreventTheftPswActitivy extends BaseActivity {

    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.acitivty_forget_prevent_theft_psw, null);
    }

    public void startGesturePsw(View view) {
        startActivity(new Intent(ForgetPreventTheftPswActitivy.this, SetGesturePswActivity.class));
    }

    public void userInfo(View view) {
        startActivity(new Intent(ForgetPreventTheftPswActitivy.this, UserInfoActivity.class));
    }

    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("忘记密码");
        super.onResume();
    }
}
