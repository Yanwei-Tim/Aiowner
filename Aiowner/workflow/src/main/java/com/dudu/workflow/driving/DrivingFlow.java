package com.dudu.workflow.driving;

import com.dudu.aiowner.commonlib.commonutils.DataJsonTranslation;
import com.dudu.aiowner.commonlib.model.ReceiverData;
import com.dudu.workflow.switchmessage.AccTestData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;

/**
 * Created by Administrator on 2016/2/20.
 */
public class DrivingFlow {
    private Logger logger = LoggerFactory.getLogger("DrivingFlow");

    public Observable<AccTestData> getReceiveDataFlow(Observable<ReceiverData> observable) {
        return observable
                .filter(receiverData ->
                        receiverData.getTitle().equals(ReceiverData.ACCELERATEDTEST_VALUE))
                .map(data ->
                        (AccTestData) DataJsonTranslation.jsonToObject(data.getContent(), AccTestData.class));
    }

    public void saveDrivingDataFlow(Observable<AccTestData> observable){
        observable.subscribe();
    }

}
