package com.dudu.aiowner.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunny_zhang on 2016/3/10.
 */
public class RobberyUploadResponse {

    /**
     * 0	操作成功
     * 50007	method参数为NULL
     * 50008	messageId参数为NULL
     * 50003	不合法的Token
     * 50012	手机号格式错误
     * 50004    参数不合法
     */

    @SerializedName("resultCode")
    public long resultCode;

    /*
     * 发送数据时调用的接口名
     */
    @SerializedName("method")
    public long method;

    /*
     * 消息ID
     */
    @SerializedName("messageId")
    public long messageId;

    @Override
    public String toString() {
        return "resultCode:"+resultCode+", method:"+method+"messageId:"+messageId;
    }
}
