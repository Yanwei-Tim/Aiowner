package com.dudu.aiowner.rest.model;

/**
 * Created by Robi on 2016-03-0919:07.
 */
public class BindResponse {
        /*
        * {"resultCode":0,"resultMsg":"成功"}
                40025          用户已经绑定过
                40024          obe已经被使用
                40017          设备编码不存在
        * */

    public int resultCode;
    public String resultMsg;
}
