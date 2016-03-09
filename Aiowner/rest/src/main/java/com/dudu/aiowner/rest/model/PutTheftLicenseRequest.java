package com.dudu.aiowner.rest.model;

/**
 * Created by Administrator on 2016/3/9.
 */
public class PutTheftLicenseRequest {

    public String phone;
    public String platform;

    public PutTheftLicenseRequest() {
    }

    public PutTheftLicenseRequest(String phone, String platform) {
        this.phone = phone;
        this.platform = platform;
    }

}
