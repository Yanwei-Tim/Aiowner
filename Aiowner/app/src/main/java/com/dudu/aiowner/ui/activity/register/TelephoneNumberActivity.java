package com.dudu.aiowner.ui.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dudu.aiowner.R;
import com.dudu.aiowner.commonlib.MultVerify;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.workflow.RequestFactory;
import com.dudu.workflow.user.UserRequest;

/**
 * Created by sunny_zhang on 2016/1/27.
 */
public class TelephoneNumberActivity extends BaseActivity {

    private EditText regist_phonenumber_et;
    private String mobiles;
    private TextView mydialog_phone_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
        regist_phonenumber_et = (EditText) findViewById(R.id.regist_input_phonenumber_et);
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
        mobiles = regist_phonenumber_et.getText().toString();

        if (mobiles.isEmpty()) {
            Toast.makeText(getApplicationContext(), "请输入手机号码", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(TelephoneNumberActivity.this, "请求验证码失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
        }
    }
}
