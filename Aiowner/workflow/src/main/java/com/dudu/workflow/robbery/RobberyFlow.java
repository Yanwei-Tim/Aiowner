package com.dudu.workflow.robbery;

import com.dudu.aiowner.commonlib.model.ReceiverData;
import com.dudu.workflow.common.CommonParams;
import com.dudu.workflow.common.FlowFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;

/**
 * Created by Administrator on 2016/2/17.
 * 本地保存/获取防劫模式开关状态
 */
public class RobberyFlow {

    private Logger logger = LoggerFactory.getLogger("RobberyFlow");

    public Observable<ReceiverData> getRobberyFlow(Observable<ReceiverData> observable) {
        return observable
                .filter(receiverData ->
                        receiverData.getTitle().equals(ReceiverData.ROBBERY_VALUE));
    }

    public void saveRobberyDataFlow(Observable<ReceiverData> observable) {
        observable.subscribe(receiverData -> {
            FlowFactory.getSwitchDataFlow()
                    .saveRobberyState(receiverData.getSwitch0Value().equals("1"));
            FlowFactory.getSwitchDataFlow()
                    .saveRobberySwitch(CommonParams.HEADLIGHT, receiverData.getSwitch1Value().equals("1"));
            FlowFactory.getSwitchDataFlow()
                    .saveRobberySwitch(CommonParams.PARK, receiverData.getSwitch2Value().equals("1"));
            FlowFactory.getSwitchDataFlow()
                    .saveRobberySwitch(CommonParams.GUN, receiverData.getSwitch3Value().equals("1"));
        });
    }
}
