package com.dudu.aiowner.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/3/7.
 */
public class License {

    @SerializedName("platform")
    String platform = "Android";

    @SerializedName("phone")
    String phone;

    @SerializedName("driving")
    String driving;

    @SerializedName("driver")
    String driver;


    public License(String phone, String driving, String driver) {
        this.phone = phone;
        this.driving = driving;
        this.driver = driver;
    }
}
