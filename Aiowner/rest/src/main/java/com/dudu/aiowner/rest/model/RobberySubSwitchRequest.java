package com.dudu.aiowner.rest.model;

/**
 * Created by sunny_zhang on 2016/3/10.
 */
public class RobberySubSwitchRequest {

    public String phone;

    public String platform;

    public int[] robberySwitchs;

    public RobberySubSwitchRequest(String phone, String platform, int[] robberySwitchs) {
        this.phone = phone;
        this.platform = platform;
        this.robberySwitchs = robberySwitchs;
    }
}
