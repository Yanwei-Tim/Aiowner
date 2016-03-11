package com.dudu.aiowner.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/3/9.
 */
public class PutTheftLicenseResponse {

    @SerializedName("resultCode")
    public int resultCode;

    @SerializedName("resultMsg")
    public String resultMsg;

    public PutTheftLicenseResponse(int resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }
}
