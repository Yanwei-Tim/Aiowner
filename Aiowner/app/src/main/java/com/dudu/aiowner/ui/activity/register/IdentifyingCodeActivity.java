package com.dudu.aiowner.ui.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.dudu.aiowner.R;
import com.dudu.aiowner.databinding.ActivityVerifycodeBinding;
import com.dudu.aiowner.ui.activity.login.LoginActivity;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.workflow.common.RequestFactory;
import com.dudu.workflow.user.UserRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sunny_zhang on 2016/1/27.
 */
public class IdentifyingCodeActivity extends BaseActivity {

    private String mCellphone;
    private Logger logger = LoggerFactory.getLogger("IdentifyingCodeActivity");
    private ActivityVerifycodeBinding verifycodeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        verifycodeBinding = ActivityVerifycodeBinding.bind(childView);

        mCellphone = getIntent().getStringExtra("cellphone");
    }

    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_verifycode, null);
    }

    public void startInitPassword(View view) {
        String verifyCode = verifycodeBinding.verifyCodeEt.getText().toString();
        if (verifyCode.isEmpty()) {
            logger.info("请输入验证码");
            return;
        }

//        RequestFactory.getUserRequest().isVerifyCodeValid(mCellphone, "", new UserRequest.VerifyCodeValidCallback() {
//            @Override
//            public void verifyCodeIsValid(boolean success) {
//                if (success) {
//                    Intent intent = new Intent(IdentifyingCodeActivity.this, LoginActivity.class);
//                    intent.putExtra("cellphone", mCellphone);
//                    startActivity(intent);
//                } else {
//                    logger.error("校验失败");
//                }
//            }
//        });
    }

    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("注册");
        super.onResume();
    }
}

