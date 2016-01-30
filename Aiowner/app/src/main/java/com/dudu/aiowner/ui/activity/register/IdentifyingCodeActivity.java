package com.dudu.aiowner.ui.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.aiowner.ui.observable.TitleBarObservable;

/**
 * Created by sunny_zhang on 2016/1/27.
 */
public class IdentifyingCodeActivity extends BaseActivity {

    private TitleBarObservable titleBarObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_identifyingcode, null);
    }

    public void startInitPassword(View view) {
        startActivity(new Intent(IdentifyingCodeActivity.this, InitPasswordActivity.class));
    }

    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("注册");
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

