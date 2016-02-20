package com.dudu.aiowner.ui.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.workflow.common.RequestFactory;
import com.dudu.workflow.user.UserRequest;

/**
 * Created by sunny_zhang on 2016/1/27.
 */
public class IdentifyingCodeActivity extends BaseActivity {

    private String mCellphone;
    private EditText verifyCode_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCellphone = getIntent().getStringExtra("cellphone");
        verifyCode_et = (EditText) findViewById(R.id.verifyCode_et);
    }

    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_identifyingcode, null);
    }

    public void startInitPassword(View view) {
        String verifyCode = verifyCode_et.getText().toString();
        if (verifyCode.isEmpty()) {
            Toast.makeText(getApplicationContext(), "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        //根据演示需求，注册演示到此，需点击返回登录
        if (verifyCode.length() < 20) {
            Toast.makeText(getApplicationContext(), "请输入正确的验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestFactory.getUserRequest().isVerifyCodeValid(mCellphone, "", new UserRequest.VerifyCodeValidCallback() {
            @Override
            public void verifyCodeIsValid(boolean success) {
                if (success) {
                    Intent intent = new Intent(IdentifyingCodeActivity.this, InitPswActivity.class);
                    intent.putExtra("cellphone", mCellphone);
                    startActivity(intent);
                } else {
                    Toast.makeText(IdentifyingCodeActivity.this, "校验失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("注册");
        super.onResume();
    }
}

