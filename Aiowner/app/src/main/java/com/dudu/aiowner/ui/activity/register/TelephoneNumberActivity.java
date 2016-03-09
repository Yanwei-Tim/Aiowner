package com.dudu.aiowner.ui.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.commonlib.MultVerify;
import com.dudu.aiowner.databinding.ActivityRegistPhoneBinding;
import com.dudu.aiowner.ui.base.BaseActivity;
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
    private Logger logger = LoggerFactory.getLogger("TelephoneNumberActivity");
    private ActivityRegistPhoneBinding registPhoneBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registPhoneBinding = ActivityRegistPhoneBinding.bind(childView);

    }

    @Override
    protected View getChildView() {

        return LayoutInflater.from(this).inflate(R.layout.activity_regist_phone, null);
    }


    @Override
    protected void onResume() {

        observableFactory.getTitleObservable().titleText.set("注册");
        super.onResume();
    }

    public void startIdentifyingCode(View v) {

        mobiles = registPhoneBinding.registPhoneEt.getText().toString();
        psw = registPhoneBinding.registPswEt.getText().toString();

        if (mobiles.isEmpty()) {
            logger.info("请输入手机号码");
        } else if (psw.isEmpty()) {
            logger.info("请输入密码");
        } else if (MultVerify.isPhoneNumberValid(mobiles)) {

//            Dialog dialog = new Dialog(TelephoneNumberActivity.this, R.style.MyDialog);
//            //设置它的ContentView
//            dialog.setContentView(R.layout.mydialog);
//            dialog.show();

//            RequestFactory.getUserRequest().requestVerifyCode(mobiles, new UserRequest.RequestVerifyCodeCallback() {
//
//                @Override
//                public void requestVerifyCodeResult(boolean success) {
//                    if (success) {
//                        Intent intent = new Intent(TelephoneNumberActivity.this, IdentifyingCodeActivity.class);
//                        intent.putExtra("cellphone", mobiles);
//                        startActivity(intent);
//                    } else {
//                        logger.error("请求验证码失败");
//                    }
//                }
//            });
//
        } else {
            logger.info("请输入正确的手机号码");
        }
    }
}
