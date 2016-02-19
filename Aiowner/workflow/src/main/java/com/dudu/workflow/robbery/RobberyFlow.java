package com.dudu.workflow.robbery;

import com.dudu.aiowner.commonlib.model.ReceiverData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/2/17.
 * 本地保存/获取防劫模式开关状态
 */
public class RobberyFlow {

    private Logger logger = LoggerFactory.getLogger("RobberyFlow");

    public Observable<ReceiverData> getRobberyFlow(Observable<ReceiverData> observable) {
        return observable
                .filter(new Func1<ReceiverData, Boolean>() {
                    @Override
                    public Boolean call(ReceiverData data) {
                        return data.getTitle().equals(ReceiverData.ROBBERY_VALUE);
                    }
                });
    }
}
