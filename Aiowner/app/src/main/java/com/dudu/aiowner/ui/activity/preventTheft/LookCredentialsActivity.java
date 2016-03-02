package com.dudu.aiowner.ui.activity.preventTheft;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.base.BaseActivity;

/**
 * Created by sunny_zhang on 2016/3/2.
 */
public class LookCredentialsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View getChildView() {

        return LayoutInflater.from(this).inflate(R.layout.activity_owners_credentials_upload, null);
    }

    public void startOwnersCredentialsUploadOk(View view) {

        startActivity(new Intent(LookCredentialsActivity.this, PreventTheftActivity.class));
    }

    @Override
    protected void onResume() {

        observableFactory.getTitleObservable().titleText.set("查看证件");
        observableFactory.getTitleObservable().userIcon.set(true);
        observableFactory.getCommonObservable().hasBottomIcon.set(false);
        super.onResume();
    }
}
