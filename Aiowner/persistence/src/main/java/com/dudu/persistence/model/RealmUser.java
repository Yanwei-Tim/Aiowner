package com.dudu.persistence.model;

import io.realm.RealmObject;

/**
 * Created by Administrator on 2016/2/18.
 */
public class RealmUser extends RealmObject {
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
