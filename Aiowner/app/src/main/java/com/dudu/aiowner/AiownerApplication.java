package com.dudu.aiowner;

import android.app.Application;
import android.content.Context;

import com.dudu.aiowner.commonlib.CommonLib;
import com.dudu.aiowner.receiver.ReceiverRegister;
import com.dudu.aiowner.rest.common.Request;
import com.dudu.workflow.common.CommonParams;
import com.dudu.workflow.common.FlowFactory;
import com.dudu.workflow.common.ObservableFactory;
import com.dudu.workflow.common.RequestFactory;

/**
 * Created by dengjun on 2016/2/12.
 * Description :
 */
public class AiownerApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        CommonLib.getInstance().init(this);
        FlowFactory.getInstance().init();
        CommonParams.getInstance().init();
        RequestFactory.getInstance().init();
        ObservableFactory.init();
        Request.getInstance().init();
        ReceiverRegister.registPushManager(CommonParams.getInstance().getUserName());
    }

    public static Context getContext() {
        return context;
    }
}
