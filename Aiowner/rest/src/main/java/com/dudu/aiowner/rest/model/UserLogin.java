package com.dudu.aiowner.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/3/5.
 */
public class UserLogin {

    @SerializedName("phone")
    String phone;

    @SerializedName("password")
    String password;

    @SerializedName("platform")
    String platform;

    public UserLogin(String phone, String password, String platform) {
        this.phone = phone;
        this.password = password;
        this.platform = platform;
    }
}
