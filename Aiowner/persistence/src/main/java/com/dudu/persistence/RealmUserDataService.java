package com.dudu.persistence;

import com.dudu.persistence.model.RealmUser;
import com.dudu.persistence.model.User;
import com.dudu.persistence.rx.RealmObservable;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/2/18.
 */
public class RealmUserDataService implements UserDataService {

    public RealmUserDataService() {
    }

    @Override
    public Observable<User> findUser() {
        return RealmObservable.object(new Func1<Realm, RealmUser>() {
            @Override
            public RealmUser call(Realm realm) {
                RealmResults<RealmUser> userList = realm.where(RealmUser.class).findAll();
                if(userList.size()>0) {
                    return userList.first();
                }else{
                    return null;
                }
            }
        }).map(new Func1<RealmUser, User>() {
            @Override
            public User call(RealmUser realmUser) {
                return userFromRealm(realmUser);
            }
        });
    }

    @Override
    public Observable<User> findUser(final String username) {
        return RealmObservable.object(new Func1<Realm, RealmUser>() {
            @Override
            public RealmUser call(Realm realm) {
                return realm.where(RealmUser.class).equalTo("username", username).findFirst();
            }
        }).map(new Func1<RealmUser, User>() {
            @Override
            public User call(RealmUser realmUser) {
                return userFromRealm(realmUser);
            }
        });
    }

    @Override
    public Observable<User> addUser(final User user) {
        return RealmObservable.object(new Func1<Realm, RealmUser>() {
            @Override
            public RealmUser call(Realm realm) {
                RealmUser realmUser = new RealmUser();
                realmUser.setUserName(user.getUserName());
                return realm.copyToRealm(realmUser);
            }
        }).map(new Func1<RealmUser, User>() {
            @Override
            public User call(RealmUser realmUser) {
                // map to UI object
                return new User(realmUser);
            }
        });
    }

    private static User userFromRealm(RealmUser realmUser) {
        return new User(realmUser.getUserName());
    }
}
