package com.dudu.workflow;

import com.dudu.aiowner.commonlib.commonutils.RxBus;
import com.dudu.aiowner.commonlib.model.ReceiverData;
import com.dudu.workflow.guard.GuardFlow;
import com.dudu.workflow.robbery.RobberyFlow;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/2/19.
 */
public class ObservableFactory {

    private static GuardFlow guardFlow = new GuardFlow();
    private static RobberyFlow robberyFlow = new RobberyFlow();

    private static Observable<ReceiverData> getReceiverObservable(){
        return RxBus.getInstance().asObservable()
                .filter(new Func1<Object, Boolean>() {
                    @Override
                    public Boolean call(Object event) {
                        return event instanceof ReceiverData;
                    }
                })
                .map(new Func1<Object, ReceiverData>() {
                    @Override
                    public ReceiverData call(Object event) {
                        return (ReceiverData) event;
                    }
                });
    }

    public static Observable<Boolean> getGuardReceiveObservable(){
        return guardFlow.getGuardDataFlow(getReceiverObservable());
    }

    public static Observable<ReceiverData> getRobberyFlow(){
        return robberyFlow.getRobberyFlow(getReceiverObservable());
    }
}
