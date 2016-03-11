package com.dudu.aiowner.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunny_zhang on 2016/3/10.
 */
public class SetTheftSwitchResponse {

    /**
     * 200      操作成功
     * 400      操作失败
     */

    @SerializedName("resultCode")
    public int resultCode;
}
