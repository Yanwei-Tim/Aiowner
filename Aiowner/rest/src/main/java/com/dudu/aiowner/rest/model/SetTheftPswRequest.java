package com.dudu.aiowner.rest.model;

/**
 * Created by sunny_zhang on 2016/3/10.
 */
public class SetTheftPswRequest {

    public String phone;    //用户名

    public String platform;     //平台

    public String password;     //密码

    public int protectThiefState;       //防盗密码类型

    public SetTheftPswRequest(String phone, String platform, String password, int protectThiefState) {
        this.phone = phone;
        this.platform = platform;
        this.password = password;
        this.protectThiefState = protectThiefState;
    }
}
