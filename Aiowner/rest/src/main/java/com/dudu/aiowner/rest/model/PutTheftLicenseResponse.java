package com.dudu.aiowner.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/3/9.
 */
public class PutTheftLicenseResponse {

    @SerializedName("resultCode")
    public int resultCode;

    public PutTheftLicenseResponse(int resultCode) {
        this.resultCode = resultCode;
    }
}
