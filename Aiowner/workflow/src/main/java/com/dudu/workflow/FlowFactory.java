package com.dudu.workflow;

import com.dudu.persistence.user.RealmUserDataService;
import com.dudu.workflow.user.UserFlow;

/**
 * Created by Administrator on 2016/2/19.
 */
public class FlowFactory {
    private static FlowFactory mInstance = new FlowFactory();
    private static UserFlow userFlow;

    public static FlowFactory getInstance(){
        return mInstance;
    }

    public void init(){
        userFlow = new UserFlow(new RealmUserDataService());
    }

    public static UserFlow getUserDataFlow() {
        return userFlow;
    }
}