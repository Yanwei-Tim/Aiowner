package com.dudu.aiowner.ui.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dudu.aiowner.R;
import com.dudu.aiowner.commonlib.MultVerify;
import com.dudu.aiowner.ui.activity.login.LoginActivity;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.workflow.common.RequestFactory;
import com.dudu.workflow.user.UserRequest;

/**
 * Created by sunny_zhang on 2016/1/27.
 */
public class InitPswActivity extends BaseActivity {
    private EditText initpsw_psw_et;
    private EditText initpsw_repsw_et;
    private TextView initpsw_confirm_btn;
    private String mCellphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
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
    public void startLogin(View v) {

        String newPassword = initpsw_psw_et.getText().toString();
        String confirmPassword = initpsw_repsw_et.getText().toString();

        if (TextUtils.isEmpty(newPassword)) {
            Toast.makeText(getApplicationContext(), "请输入新密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!(MultVerify.isPasswordValid(newPassword))){
            Toast.makeText(getApplicationContext(), "密码必须为大于6位的数字和字母的组合，且至少包含一位大写字母", Toast.LENGTH_SHORT).show();
            return;
        }
//        if (newPassword.length() < 6) {
//            Toast.makeText(getApplicationContext(), "密码不能少于6位", Toast.LENGTH_SHORT).show();
//            return;
//        }
        if (TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(getApplicationContext(), "请确认密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!(newPassword.equals(confirmPassword))) {
            Toast.makeText(getApplicationContext(), "两次密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestFactory.getUserRequest().settingPassword(mCellphone, "securityCode", newPassword, new UserRequest.RegisterCallback() {
                @Override
                public void registerSuccess(boolean success) {
                    if (success) {
                        startActivity(new Intent(InitPswActivity.this, LoginActivity.class));
                    }else{
                        Toast.makeText(InitPswActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
}
