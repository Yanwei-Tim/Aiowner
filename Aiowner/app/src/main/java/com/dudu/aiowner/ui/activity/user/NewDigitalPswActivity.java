package com.dudu.aiowner.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.dudu.aiowner.R;
import com.dudu.aiowner.databinding.ActivityNewDigitalPswBinding;
import com.dudu.aiowner.ui.activity.user.widget.NumericKeyboard;
import com.dudu.aiowner.ui.activity.user.widget.PasswordTextView;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.aiowner.utils.DigitalPswUtils.Consts;
import com.dudu.aiowner.utils.DigitalPswUtils.MyPrefs;

/**
 * Created by sunny_zhang on 2016/3/2.
 */
public class NewDigitalPswActivity extends BaseActivity {
    // 密码框
    private int type = 0;
    //声明字符串保存每一次输入的密码
    private String input;
    private StringBuffer fBuffer = new StringBuffer();

    private ActivityNewDigitalPswBinding widget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        widget = ActivityNewDigitalPswBinding.bind(childView);
        initListener();// 事件处理


    }

    @Override
    protected View getChildView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_new_digital_psw, null);
    }

    @Override
    protected void onResume() {

        observableFactory.getTitleObservable().titleText.set("");
        observableFactory.getTitleObservable().userIcon.set(false);
        observableFactory.getTitleObservable().hasBackGround.set(true);
        observableFactory.getTitleObservable().hasUserBackButton.set(true);
        observableFactory.getCommonObservable().hasBottomIcon.set(false);
        observableFactory.getCommonObservable().hasUserBackground.set(true);
        super.onResume();
    }

    /**
     * 事件处理
     */
    private void initListener() {

        // 设置点击的按钮回调事件
        widget.nk.setOnNumberClick(new NumericKeyboard.OnNumberClick() {
            @Override
            public void onNumberReturn(int number) {
                // 设置显示密码
                setText(number + "");
            }
        });

        //监听最后一个密码框的文本改变事件回调
        widget.etPwd4.setOnTextChangedListener(new PasswordTextView.OnTextChangedListener() {
            @Override
            public void textChanged(String content) {
                input = widget.etPwd1.getTextContent() + widget.etPwd2.getTextContent() +
                        widget.etPwd3.getTextContent() + widget.etPwd4.getTextContent();
                //判断类型

                if (type == Consts.OLD_PASSWORD) {//输入原密码
                    widget.tvInfo.setText(getString(R.string.please_input_new_pwd));
                    widget.forgetPswTv.setVisibility(View.INVISIBLE);
                    type = Consts.NEW_PASSWORD;
                    clearText();//清除输入
                } else if (type == Consts.NEW_PASSWORD) {//输入新密码
                    //重新输入密码
                    widget.tvInfo.setText(getString(R.string.please_input_pwd_again));
                    widget.forgetPswTv.setVisibility(View.INVISIBLE);
                    type = Consts.SURE_SETTING_PASSWORD;
                    fBuffer.append(input);//保存第一次输入的密码
                    clearText();//清除输入
                } else if (type == Consts.LOGIN_PASSWORD) {//登录

                } else if (type == Consts.SURE_SETTING_PASSWORD) {//确认密码
                    //判断两次输入的密码是否一致
                    if (input.equals(fBuffer.toString())) {//一致
                        //保存密码到文件中
                        MyPrefs.getInstance().initSharedPreferences(NewDigitalPswActivity.this);
                        startActivity(new Intent(NewDigitalPswActivity.this, SetTheftGesturePswOk.class));
                    } else {//不一致
                        showToastMsg(getString(R.string.not_equals));
                        clearText();//清除输入
                    }
                }
            }
        });
    }

    /**
     * 设置显示的密码
     *
     * @param text
     */
    private void setText(String text) {
        // 从左往右依次显示
        if (TextUtils.isEmpty(widget.etPwd1.getTextContent())) {
            widget.etPwd1.setTextContent(text);
        } else if (TextUtils.isEmpty(widget.etPwd2.getTextContent())) {
            widget.etPwd2.setTextContent(text);
        } else if (TextUtils.isEmpty(widget.etPwd3.getTextContent())) {
            widget.etPwd3.setTextContent(text);
        } else if (TextUtils.isEmpty(widget.etPwd4.getTextContent())) {
            widget.etPwd4.setTextContent(text);
        }
    }

    /**
     * 清除输入的内容--重输
     */
    private void clearText() {
        widget.etPwd1.setTextContent("");
        widget.etPwd2.setTextContent("");
        widget.etPwd3.setTextContent("");
        widget.etPwd4.setTextContent("");
    }

    /**
     * 删除刚刚输入的内容
     */
    private void deleteText() {
        // 从右往左依次删除
        if (!TextUtils.isEmpty(widget.etPwd4.getTextContent())) {
            widget.etPwd4.setTextContent("");
        } else if (!TextUtils.isEmpty(widget.etPwd3.getTextContent())) {
            widget.etPwd3.setTextContent("");
        } else if (!TextUtils.isEmpty(widget.etPwd2.getTextContent())) {
            widget.etPwd2.setTextContent("");
        } else if (!TextUtils.isEmpty(widget.etPwd1.getTextContent())) {
            widget.etPwd1.setTextContent("");
        }
    }

    /**
     * 按钮的点击事件处理
     * @param v
     */
//	public void doClick(View v){
//		//判断点击的按钮
//		switch (v.getId()) {
//		case R.id.btn_again://重输
//			clearText();//清除所有输入的内容
//			break;
//		case R.id.btn_delete://删除
//			deleteText();//删除刚刚输入的内容
//			break;
//
//		default:
//			break;
//		}
//	}

    /**
     * 显示Toast提示信息
     *
     * @param text
     */
    private void showToastMsg(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}
