package com.dudu.aiowner.ui.activity.register;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dudu.aiowner.R;
import com.dudu.aiowner.commonlib.MultVerify;
import com.dudu.aiowner.ui.base.BaseActivity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sunny_zhang on 2016/1/27.
 */
public class InitPswActivity extends BaseActivity {
    private EditText initpsw_psw_et;
    private EditText initpsw_repsw_et;
    private TextView initpsw_confirm_btn;
    private String mCellphone;
    private Logger logger = LoggerFactory.getLogger("InitPswActivity");

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
            logger.info("请输入新密码");
            return;
        }
        if(!(MultVerify.isPasswordValid(newPassword))){
            logger.info("密码必须为大于6位的数字和字母的组合，且至少包含一位大写字母");
            return;
        }
//        if (newPassword.length() < 6) {
//            Toast.makeText(getApplicationContext(), "密码不能少于6位", Toast.LENGTH_SHORT).show();
//            return;
//        }
        if (TextUtils.isEmpty(confirmPassword)) {
            logger.info("请确认密码");
            return;
        }
        if (!(newPassword.equals(confirmPassword))) {
            logger.info("两次密码不一致");
            return;
        }
//        RequestFactory.getUserRequest().settingPassword(mCellphone, "securityCode", newPassword, new UserRequest.RegisterCallback() {
//                @Override
//                public void registerSuccess(boolean success) {
//                    if (success) {
//                        startActivity(new Intent(InitPswActivity.this, LoginActivity.class));
//                    }else{
//                        logger.info("注册失败");
//                    }
//                }
//            });
    }
}
