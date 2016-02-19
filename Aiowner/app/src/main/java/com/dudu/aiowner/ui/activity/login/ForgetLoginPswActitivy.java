package com.dudu.aiowner.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dudu.aiowner.R;
import com.dudu.aiowner.commonlib.MultVerify;
import com.dudu.aiowner.ui.base.BaseActivity;

/**
 * Created by sunny_zhang on 2016/1/29.
 */
public class ForgetLoginPswActitivy extends BaseActivity {

    private EditText forgetPsw_phone_et;
    private EditText forgetPsw_verifyCode_et;
    private EditText forgetPsw_newPsw_et;
    private EditText forgetPsw_confirmPsw_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {

        forgetPsw_phone_et = (EditText) findViewById(R.id.forgetPsw_phone_et);
        forgetPsw_verifyCode_et = (EditText) findViewById(R.id.forgetPsw_verifyCode_et);
        forgetPsw_newPsw_et = (EditText) findViewById(R.id.forgetPsw_newPsw_et);
        forgetPsw_confirmPsw_et = (EditText) findViewById(R.id.forgetPsw_confirmPsw_et);
    }

    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.acitivty_forget_login_psw, null);
    }

    public void getVerifyCode(View view) {

        String mobiles = forgetPsw_phone_et.getText().toString();
        if (TextUtils.isEmpty(mobiles)) {
            Toast.makeText(getApplicationContext(), "请输入手机号码", Toast.LENGTH_SHORT).show();
        }//
        else if (!(MultVerify.isPhoneNumberValid(mobiles))) {
            Toast.makeText(getApplicationContext(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
        }

        //TODO 请求后台发送验证码到手机
    }

    public void startLogin(View view) {
        String newPassword = forgetPsw_newPsw_et.getText().toString();
        String confirmPassword = forgetPsw_confirmPsw_et.getText().toString();
        String phone_et = forgetPsw_phone_et.getText().toString();
        if (TextUtils.isEmpty(phone_et)) {
            Toast.makeText(getApplicationContext(), "请输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!(MultVerify.isPhoneNumberValid(phone_et))) {
            Toast.makeText(getApplicationContext(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(forgetPsw_verifyCode_et.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (MultVerify.isPasswordValid(newPassword, confirmPassword)) {
            case 0:
                Toast.makeText(getApplicationContext(), "请输入新密码", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(getApplicationContext(), "密码必须为大于6位的数字和字母的组合，且至少包含一位大写字母", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(getApplicationContext(), "请确认密码", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(getApplicationContext(), "两次密码不一致", Toast.LENGTH_SHORT).show();
                break;
            default:
                startActivity(new Intent(ForgetLoginPswActitivy.this, LoginActivity.class));
        }
    }

    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("忘记密码");
        super.onResume();
    }
}
