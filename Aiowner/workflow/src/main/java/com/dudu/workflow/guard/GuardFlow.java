package com.dudu.workflow.guard;

import com.dudu.aiowner.commonlib.model.ReceiverData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/2/17.
 * 本地保存/获取防盗模式开关状态
 */
public class GuardFlow {

    private Logger logger = LoggerFactory.getLogger("GuardFlow");

    public Observable<Boolean> getGuardDataFlow(Observable<ReceiverData> observable) {
        return observable
                .filter(new Func1<ReceiverData,Boolean>() {
                    @Override
                    public Boolean call(ReceiverData data) {
                        return data.getTitle().equals(ReceiverData.THEFT_VALUE);
                    }
                })
                .map(new Func1<ReceiverData, Boolean>() {
                    @Override
                    public Boolean call(ReceiverData receiverData) {
                        return receiverData.getSwitchValue().equals("1");
                    }
                });
    }
}
