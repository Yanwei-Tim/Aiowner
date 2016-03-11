package com.dudu.aiowner.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunny_zhang on 2016/3/10.
 */
public class UserRegister {
    @SerializedName("phone")
    String phone;

    @SerializedName("platform")
    String platform;

    public UserRegister(String phone, String platform) {
        this.phone = phone;
        this.platform = platform;
    }
}
