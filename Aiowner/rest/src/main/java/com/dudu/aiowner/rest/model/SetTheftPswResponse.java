package com.dudu.aiowner.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/3/10.
 */
public class SetTheftPswResponse {

    /**
     * 0	    操作成功
     * 50007	method参数为NULL
     * 50008	messageId参数为NULL
     * 50003	不合法的Token
     * 50012	手机号格式错误
     * 50004	参数不合法
     */

    @SerializedName("resultCode")
    public int resultCode;

    @SerializedName("method")
    public String method;

    @SerializedName("methodId")
    public int methodId;
}
