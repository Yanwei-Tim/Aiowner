package com.dudu.aiowner.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.activity.register.TelephoneNumberActivity;

/**
 * Created by sunny_zhang on 2016/1/29.
 */
public class HomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void startRegister(View view) {

        startActivity(new Intent(HomeActivity.this, TelephoneNumberActivity.class));
    }

}
