package com.dudu.aiowner.rest.model;

/**
 * Created by Robi on 2016-03-0919:07.
 */
public class BindRequest {
    /*
    * {
"phone":"13344446666",
"platform":"android",
"obeId":"2222222222222222"
}
    * */
    public String phone;
    public String platform;
    public String obeId;

    public BindRequest() {
    }

    public BindRequest(String phone, String platform, String obeId) {
        this.phone = phone;
        this.platform = platform;
        this.obeId = obeId;
    }
}
