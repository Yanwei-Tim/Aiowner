package com.dudu.aiowner.commonlib;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/2/13.
 */
public class MultVerify {

    public static boolean isPhoneNumberValid(String mobiles) {

        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();
    }

    public static boolean isPasswordValid(String password) {
        Pattern p = Pattern.compile("^(?=.*?[A-Z])(?=.*?[0-9])[a-zA-Z0-9]{7,}$");

        Matcher m = p.matcher(password);

        return m.matches();
    }

    public static int isPasswordValid(String newPassword, String confirmPassword) {

        if (TextUtils.isEmpty(newPassword)) {
            return 0;
        }
        if (!(MultVerify.isPasswordValid(newPassword))){
            return 1;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            return 2;
        }
        if (!(newPassword.equals(confirmPassword))) {
            return 3;
        }
        return -1;
    }
}


