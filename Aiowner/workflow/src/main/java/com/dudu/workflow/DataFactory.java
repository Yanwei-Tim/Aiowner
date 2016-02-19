package com.dudu.workflow;

import com.dudu.persistence.user.RealmUserDataService;
import com.dudu.workflow.user.UserFlow;

/**
 * Created by Administrator on 2016/2/18.
 */
public class DataFactory {

    private static DataFactory mInstance = new DataFactory();

    private static UserFlow userFlow;

    public static DataFactory getInstance() {
        return mInstance;
    }

    public void init(){
        userFlow = new UserFlow(new RealmUserDataService());
    }

    public static UserFlow getUserDataFlow() {
        return userFlow;
    }
}
