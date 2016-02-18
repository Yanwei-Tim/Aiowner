package com.dudu.persistence.model;

/**
 * Created by Administrator on 2016/2/18.
 */
public class User {

    private String userName;

    public User(String userName){
        this.userName = userName;
    }

    public User(RealmUser realmUser){
        this.userName = realmUser.getUserName();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
