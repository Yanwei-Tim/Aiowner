package com.dudu.workflow.guard;

import com.dudu.aiowner.commonlib.model.ReceiverData;
import com.dudu.workflow.common.FlowFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;

/**
 * Created by Administrator on 2016/2/17.
 * 本地保存/获取防盗模式开关状态
 */
public class GuardFlow {

    private Logger logger = LoggerFactory.getLogger("GuardFlow");

    public Observable<Boolean> getGuardDataFlow(Observable<ReceiverData> observable) {
        return observable
                .filter(receiverData ->
                        receiverData.getTitle().equals(ReceiverData.THEFT_VALUE))
                .map(theftReceiverData ->
                        theftReceiverData.getSwitchValue().equals("1"));
    }

    public void saveGuardDataFlow(Observable<Boolean> observable) {
        observable.subscribe(locked -> {
            FlowFactory.getSwitchDataFlow()
                    .saveRobberyState(locked);

        });
    }
}
