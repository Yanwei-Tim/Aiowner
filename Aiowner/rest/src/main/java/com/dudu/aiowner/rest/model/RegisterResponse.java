package com.dudu.aiowner.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/2/13.
 */
public class RegisterResponse extends RequestResponse {
    /**
     * 0	操作成功
     * 50007	method参数为NULL
     * 50008	messageId参数为NULL
     * 50003	不合法的Token
     * 50009	    帐号已被使用
     * 50010	验证码错误
     * 50011	验证码过期
     * 50012	手机号格式错误
     * 50013	密码超过长度
     * 50014	密码包含非法字符
     */
    @SerializedName("resultCode")
    private long resultCode;
}
