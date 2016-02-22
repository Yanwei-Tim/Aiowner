package com.dudu.workflow.receiver;

import com.dudu.aiowner.commonlib.commonutils.DataJsonTranslation;
import com.dudu.aiowner.commonlib.model.ReceiverData;
import com.dudu.workflow.common.FlowFactory;
import com.dudu.workflow.switchmessage.AccTestData;

/**
 * Created by Administrator on 2016/2/22.
 */
public class ReceiverDataFlow {

    public static boolean getGuardReceiveData(ReceiverData receiverData){
        return receiverData.getTitle().equals(ReceiverData.THEFT_VALUE);
    }

    public static void saveGuardReceiveData(ReceiverData receiverData){
        if (getGuardReceiveData(receiverData)){
            FlowFactory.getSwitchDataFlow().saveRobberyState(receiverData.getSwitchValue().equals("1"));
        }
    }

    public static boolean getRobberyReceiveData(ReceiverData receiverData){
        return receiverData.getTitle().equals(ReceiverData.ROBBERY_VALUE);
    }

    public static void saveRobberyReceiveData(ReceiverData receiverData){
        if (getRobberyReceiveData(receiverData)){
            FlowFactory.getSwitchDataFlow().saveRobberyState(receiverData.getSwitchValue().equals("1"));
        }
    }

    public static boolean getAccTestReceiverData(ReceiverData receiverData){
        return receiverData.getTitle().equals(ReceiverData.ACCELERATEDTEST_VALUE);
    }

    public static AccTestData getReceiveDataFlow(ReceiverData receiverData) {
        return (AccTestData) DataJsonTranslation.jsonToObject(receiverData.getContent(), AccTestData.class);
    }

}
