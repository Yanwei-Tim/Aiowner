package com.dudu.persistence;

import com.dudu.persistence.model.User;

import rx.Observable;

/**
 * Created by Administrator on 2016/2/18.
 */
public interface UserDataService {
    Observable<User> findUser();
    Observable<User> findUser(String username);
    Observable<User> addUser(User username);
}
