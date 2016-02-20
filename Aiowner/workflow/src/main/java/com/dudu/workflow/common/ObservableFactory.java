package com.dudu.workflow.common;

import com.dudu.aiowner.commonlib.commonutils.RxBus;
import com.dudu.aiowner.commonlib.model.ReceiverData;
import com.dudu.workflow.driving.DrivingFlow;
import com.dudu.workflow.guard.GuardFlow;
import com.dudu.workflow.robbery.RobberyFlow;
import com.dudu.workflow.switchmessage.AccTestData;

import rx.Observable;

/**
 * Created by Administrator on 2016/2/19.
 */
public class ObservableFactory {

    private static GuardFlow guardFlow = new GuardFlow();
    private static RobberyFlow robberyFlow = new RobberyFlow();
    private static DrivingFlow drivingFlow = new DrivingFlow();

    public static void init(){
        drivingFlow.saveDrivingDataFlow(getAccTestFlow());
        guardFlow.saveGuardDataFlow(getGuardReceiveObservable());
        robberyFlow.saveRobberyDataFlow(getRobberyFlow());
    }

    private static Observable<ReceiverData> getReceiverObservable() {
        return RxBus.getInstance().asObservable()
                .filter(event -> event instanceof ReceiverData)
                .map(receiverData -> (ReceiverData) receiverData);
    }

    public static Observable<Boolean> getGuardReceiveObservable() {
        return guardFlow.getGuardDataFlow(getReceiverObservable());
    }

    public static Observable<ReceiverData> getRobberyFlow() {
        return robberyFlow.getRobberyFlow(getReceiverObservable());
    }

    public static Observable<AccTestData> getAccTestFlow() {
        return drivingFlow.getReceiveDataFlow(getReceiverObservable());
    }
}
