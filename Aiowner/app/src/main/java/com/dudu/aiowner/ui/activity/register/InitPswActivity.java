package com.dudu.aiowner.ui.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dudu.aiowner.R;
import com.dudu.aiowner.rest.Request;
import com.dudu.aiowner.rest.model.RegisterResponse;
import com.dudu.aiowner.ui.activity.login.LoginActivity;
import com.dudu.aiowner.ui.base.BaseActivity;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by sunny_zhang on 2016/1/27.
 */
public class InitPswActivity extends BaseActivity implements View.OnClickListener {
    private EditText initpsw_psw_et;
    private EditText initpsw_repsw_et;
    private TextView initpsw_confirm_btn;
    private String mCellphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initpsw_confirm_btn.setOnClickListener(this);
        mCellphone = getIntent().getStringExtra("cellphone");
    }

    private void initView() {
        initpsw_psw_et = (EditText) findViewById(R.id.initpsw_psw_et);
        initpsw_repsw_et = (EditText) findViewById(R.id.initpsw_repsw_et);
        initpsw_confirm_btn = (TextView) findViewById(R.id.initpsw_confirm_btn);
    }

    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_init_psw, null);
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

    @Override
    public void onClick(View v) {

        String password = initpsw_psw_et.getText().toString();
        String affPassword = initpsw_repsw_et.getText().toString();

        if (password == null || password.trim().equals("")) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            Toast.makeText(this, "密码不能少于6位", Toast.LENGTH_SHORT).show();
        } else if (!(password.equals(affPassword))) {
            Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
        } else {
            Request.getInstance().getRegisterService().registerWithPhone(mCellphone, "", password, "method", "messageId", new Callback<RegisterResponse>() {
                @Override
                public void success(RegisterResponse registerResponse, Response response) {
                    startActivity(new Intent(InitPswActivity.this, LoginActivity.class));
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(InitPswActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
