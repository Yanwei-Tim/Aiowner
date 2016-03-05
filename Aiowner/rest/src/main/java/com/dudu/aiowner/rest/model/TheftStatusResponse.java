package com.dudu.aiowner.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/2/13.
 */
public class TheftStatusResponse {

    /**
     * 10007  	  参数异常
     * 40200	未绑定设备
     */

    @SerializedName("resultCode")
    public int resultCode;

    @SerializedName("resultMsg")
    public String resultMsg;

    @SerializedName("protect_thief_password")
    public String protect_thief_password;   //密码信息

    @SerializedName("driving_license_url")
    public String driving_license_url;      //行驶证图片路径

    @SerializedName("audit_state")
    public int audit_state;     //0:未认证 1：审核中 2：审核通过 3：审核驳回

    @SerializedName("driver_license_url")
    public String driver_license_url;       //驾驶证图片路径

    @SerializedName("thief_open_time")
    public long thief_open_time;        //防盗开启时间

    @SerializedName("protect_thief_state")
    public int protect_thief_state;     //防盗状态  0:未开启 1:密码开启 2:指纹开启 3:图案密码开启 4:无密码开启

    @SerializedName("audit_desc")
    public String audit_desc;       //审核驳回的说明

    @SerializedName("thief_switch_state")
    public int thief_switch_state;       //防盗开关 0关 1开
}
