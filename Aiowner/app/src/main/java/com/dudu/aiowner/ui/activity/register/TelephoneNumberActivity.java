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
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.aiowner.utils.RegisterVerifyUtils.RegisterVerify;

/**
 * Created by sunny_zhang on 2016/1/27.
 */
public class TelephoneNumberActivity extends BaseActivity implements View.OnClickListener {

    private TextView regist_btn;
    private EditText regist_phonenumber_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        regist_btn.setOnClickListener(this);
    }

    private void initView() {
        regist_phonenumber_et = (EditText) findViewById(R.id.regist_input_phonenumber_et);
        regist_btn = (TextView) findViewById(R.id.regist_btn);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {

        String mobiles = regist_phonenumber_et.getText().toString();

        if (mobiles == null || mobiles.trim().equals("")) {
            Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
        } else if (RegisterVerify.isPhoneNumberValid(mobiles)) {
            startActivity(new Intent(TelephoneNumberActivity.this, IdentifyingCodeActivity.class));
        } else {
            Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
        }
    }
}
