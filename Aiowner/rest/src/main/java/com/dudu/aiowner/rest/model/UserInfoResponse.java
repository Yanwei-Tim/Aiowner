package com.dudu.aiowner.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/3/10.
 */
public class UserInfoResponse {

    @SerializedName("resultCode")  //头像地址
    public int resultCode;

    @SerializedName("resultMsg")  //帐号
    public String resultMsg;

    @SerializedName("head_url")  //头像地址
    public String head_url;

    @SerializedName("phone")  //帐号
    public String phone;

    @SerializedName("driver_license_url")  //驾驶证地址
    public String driver_license_url;

    @SerializedName("obeId")  //imei码
    public long obeId;

    @SerializedName("nickname")  //昵称
    public String nickname;

    @SerializedName("thief_audit_state")  //防盗审批状态
    public int thief_audit_state;

    @SerializedName("driving_license_url")  //行驶证图片地址
    public String driving_license_url;

    @SerializedName("thief_audit_desc")  //防盗审批说明
    public String thief_audit_desc;

    @SerializedName("protect_thief_state")  //"防盗状态  0:未开启 1:密码开启 2:指纹开启 3:图案密码开启 4:无密码开启"
    public int protect_thief_state;

    @SerializedName("thief_switch_state")   //防盗开关状态
    public int thief_switch_state;

    @SerializedName("protect_thief_password")   //防盗密码信息 手势密码 指纹密码
    public int protect_thief_password;

    @Override
    public String toString() {
        return "resultCode:"+resultCode+"resultMsg"+resultMsg+ "head_url:" + head_url
                + "phone:" + phone + "driver_license_url:" + driver_license_url + "obeId:" + obeId
                + "nickname:" + nickname + "thief_audit_state:" + thief_audit_state
                + "driving_license_url:" + driving_license_url + "thief_audit_desc:" + thief_audit_desc
                + "protect_thief_state:" + protect_thief_state + "thief_switch_state:" + thief_switch_state
                + "protect_thief_password:" + protect_thief_password;
    }
}
