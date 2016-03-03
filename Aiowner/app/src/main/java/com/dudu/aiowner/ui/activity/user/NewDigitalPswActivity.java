package com.dudu.aiowner.ui.activity.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.activity.user.widget.NumericKeyboard;
import com.dudu.aiowner.ui.activity.user.widget.PasswordTextView;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.aiowner.utils.DigitalPswUtils.Consts;
import com.dudu.aiowner.utils.DigitalPswUtils.MyPrefs;

/**
 * Created by sunny_zhang on 2016/3/2.
 */
public class NewDigitalPswActivity extends BaseActivity {
    private NumericKeyboard nk;// 数字键盘布局
    // 密码框
    private PasswordTextView et_pwd1, et_pwd2, et_pwd3, et_pwd4;
    private int type = 0;
    private TextView tv_info;//提示信息
    //声明字符串保存每一次输入的密码
    private String input;
    private StringBuffer fBuffer = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWidget();// 初始化控件
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
     * 初始化控件
     */
    private void initWidget() {
        nk = (NumericKeyboard) findViewById(R.id.nk);// 数字键盘
        // 密码框
        et_pwd1 = (PasswordTextView) findViewById(R.id.et_pwd1);
        et_pwd2 = (PasswordTextView) findViewById(R.id.et_pwd2);
        et_pwd3 = (PasswordTextView) findViewById(R.id.et_pwd3);
        et_pwd4 = (PasswordTextView) findViewById(R.id.et_pwd4);
        tv_info = (TextView) findViewById(R.id.tv_info);//提示信息
    }

    /**
     * 事件处理
     */
    private void initListener() {
        // 设置点击的按钮回调事件
        nk.setOnNumberClick(new NumericKeyboard.OnNumberClick() {
            @Override
            public void onNumberReturn(int number) {
                // 设置显示密码
                setText(number + "");
            }
        });

        //监听最后一个密码框的文本改变事件回调
        et_pwd4.setOnTextChangedListener(new PasswordTextView.OnTextChangedListener() {
            @Override
            public void textChanged(String content) {
                input = et_pwd1.getTextContent() + et_pwd2.getTextContent() +
                        et_pwd3.getTextContent() + et_pwd4.getTextContent();
                //判断类型
                if (type == Consts.SETTING_PASSWORD) {//设置密码
                    //重新输入密码
                    tv_info.setText(getString(R.string.please_input_pwd_again));
                    type = Consts.SURE_SETTING_PASSWORD;
                    fBuffer.append(input);//保存第一次输入的密码
                    clearText();//清除输入
                } else if (type == Consts.LOGIN_PASSWORD) {//登录

                } else if (type == Consts.SURE_SETTING_PASSWORD) {//确认密码
                    //判断两次输入的密码是否一致
                    if (input.equals(fBuffer.toString())) {//一致
                        showToastMsg(getString(R.string.setting_pwd_success));
                        //保存密码到文件中
                        MyPrefs.getInstance().initSharedPreferences(NewDigitalPswActivity.this);
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
        if (TextUtils.isEmpty(et_pwd1.getTextContent())) {
            et_pwd1.setTextContent(text);
        } else if (TextUtils.isEmpty(et_pwd2.getTextContent())) {
            et_pwd2.setTextContent(text);
        } else if (TextUtils.isEmpty(et_pwd3.getTextContent())) {
            et_pwd3.setTextContent(text);
        } else if (TextUtils.isEmpty(et_pwd4.getTextContent())) {
            et_pwd4.setTextContent(text);
        }
    }

    /**
     * 清除输入的内容--重输
     */
    private void clearText() {
        et_pwd1.setTextContent("");
        et_pwd2.setTextContent("");
        et_pwd3.setTextContent("");
        et_pwd4.setTextContent("");
    }

    /**
     * 删除刚刚输入的内容
     */
    private void deleteText() {
        // 从右往左依次删除
        if (!TextUtils.isEmpty(et_pwd4.getTextContent())) {
            et_pwd4.setTextContent("");
        } else if (!TextUtils.isEmpty(et_pwd3.getTextContent())) {
            et_pwd3.setTextContent("");
        } else if (!TextUtils.isEmpty(et_pwd2.getTextContent())) {
            et_pwd2.setTextContent("");
        } else if (!TextUtils.isEmpty(et_pwd1.getTextContent())) {
            et_pwd1.setTextContent("");
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
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
