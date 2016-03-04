package com.dudu.aiowner.ui.activity.register;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.dudu.aiowner.R;
import com.dudu.aiowner.commonlib.MultVerify;
import com.dudu.aiowner.databinding.ActivityRegistPhonenumberBinding;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.aiowner.utils.customFontUtils.FZLFontTextView;
import com.dudu.workflow.common.RequestFactory;
import com.dudu.workflow.user.UserRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sunny_zhang on 2016/1/27.
 */
public class TelephoneNumberActivity extends BaseActivity {

    private String mobiles;
    private String psw;
    private TextView mydialog_phone_tv;
    private Logger logger = LoggerFactory.getLogger("TelephoneNumberActivity");
    private ActivityRegistPhonenumberBinding registPhonenumberBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registPhonenumberBinding = ActivityRegistPhonenumberBinding.bind(childView);
        initView();
    }

    private void initView() {
        mydialog_phone_tv = (TextView) findViewById(R.id.mydialog_phone_tv);
    }

    @Override
    protected View getChildView() {

        return LayoutInflater.from(this).inflate(R.layout.activity_regist_phonenumber, null);
    }


    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("注册");
        super.onResume();
    }

    public void startIdentifyingCode(View v) {

        mobiles = registPhonenumberBinding.registPhoneEt.getText().toString();
        psw = registPhonenumberBinding.registPswEt.getText().toString();

        if (mobiles.isEmpty()) {
            logger.info("请输入手机号码");
        } else if (psw.isEmpty()) {
            logger.info("请输入密码");
        } else if (MultVerify.isPhoneNumberValid(mobiles)) {

//            Dialog dialog = new Dialog(TelephoneNumberActivity.this, R.style.MyDialog);
//            //设置它的ContentView
//            dialog.setContentView(R.layout.mydialog);
//            dialog.show();

            RequestFactory.getUserRequest().requestVerifyCode(mobiles, new UserRequest.RequestVerifyCodeCallback() {

                @Override
                public void requestVerifyCodeResult(boolean success) {
                    if (success) {
                        Intent intent = new Intent(TelephoneNumberActivity.this, IdentifyingCodeActivity.class);
                        intent.putExtra("cellphone", mobiles);
                        startActivity(intent);
                    } else {
                        logger.error("请求验证码失败");
                    }
                }
            });

        } else {
            logger.info("请输入正确的手机号码");
        }
    }
}
