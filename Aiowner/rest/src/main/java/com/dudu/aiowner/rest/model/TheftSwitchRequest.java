package com.dudu.aiowner.rest.model;

/**
 * Created by sunny_zhang on 2016/3/10.
 */
public class TheftSwitchRequest {

    public String phone;

    public String platform;

    public int thiefSwitchState;

    public TheftSwitchRequest(String phone, String platform, int thiefSwitchState) {
        this.phone = phone;
        this.platform = platform;
        this.thiefSwitchState = thiefSwitchState;
    }
}
