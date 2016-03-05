package com.dudu.aiowner.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.dudu.aiowner.R;
import com.dudu.aiowner.commonlib.MultVerify;
import com.dudu.aiowner.databinding.ActivityForgetLoginPswBinding;
import com.dudu.aiowner.ui.base.BaseActivity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sunny_zhang on 2016/1/29.
 */
public class ForgetLoginPswActitivy extends BaseActivity {

    private Logger logger = LoggerFactory.getLogger("ForgetLoginPswActitivy");
    private ActivityForgetLoginPswBinding widget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        widget = ActivityForgetLoginPswBinding.bind(childView);
    }

    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_forget_login_psw, null);
    }

    public void getVerifyCode(View view) {

        String mobiles = widget.forgetPswPhoneEt.getText().toString();
        if (TextUtils.isEmpty(mobiles)) {
            logger.info("请输入手机号码");
        }//
        else if (!(MultVerify.isPhoneNumberValid(mobiles))) {
            logger.info("请输入正确的手机号码");
        }

        //TODO 请求后台发送验证码到手机
    }

    public void startLogin(View view) {
        String newPassword = widget.forgetPswNewPswEt.getText().toString();
        String confirmPassword = widget.forgetPswConfirmPswEt.getText().toString();
        String phone_et = widget.forgetPswPhoneEt.getText().toString();
        if (TextUtils.isEmpty(phone_et)) {
            logger.info("请输入手机号码");
            return;
        }
        if (!(MultVerify.isPhoneNumberValid(phone_et))) {
            logger.info("请输入正确的手机号码");
            return;
        }
        if (TextUtils.isEmpty(widget.forgetPswVerifyCodeEt.getText().toString())) {
            logger.info("请输入验证码");
            return;
        }

        switch (MultVerify.isPasswordValid(newPassword, confirmPassword)) {
            case 0:
                logger.info("请输入新密码");
                break;
            case 1:
                logger.info("密码必须为大于6位的数字和字母的组合，且至少包含一位大写字母");
                break;
            case 2:
                logger.info("请确认密码");
                break;
            case 3:
                logger.info("两次密码不一致");
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
