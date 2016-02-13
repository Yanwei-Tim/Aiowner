package com.dudu.aiowner.ui.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.dudu.aiowner.R;
import com.dudu.aiowner.rest.Request;
import com.dudu.aiowner.rest.model.RequestResponse;
import com.dudu.aiowner.ui.base.BaseActivity;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by sunny_zhang on 2016/1/27.
 */
public class IdentifyingCodeActivity extends BaseActivity {

    private String mCellphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCellphone = getIntent().getStringExtra("cellphone");

    }

    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_identifyingcode, null);
    }

    public void startInitPassword(View view) {
        Request.getInstance().getRegisterService().getSecurityCode(mCellphone, "method", "messageId", "register", new Callback<RequestResponse>() {
            @Override
            public void success(RequestResponse requestResponse, Response response) {
                Intent intent = new Intent(IdentifyingCodeActivity.this, InitPswActivity.class);
                intent.putExtra("cellphone", mCellphone);
                startActivity(intent);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(IdentifyingCodeActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
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

