package com.dudu.aiowner;

import android.app.Application;
import android.content.Context;

import com.dudu.aiowner.commonlib.CommonLib;
import com.dudu.aiowner.rest.common.Request;
import com.dudu.workflow.CommonParams;
import com.dudu.workflow.RequestFactory;

/**
 * Created by dengjun on 2016/2/12.
 * Description :
 */
public class AiownerApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        CommonLib.getInstance().init(this);
        CommonParams.getInstance().init();
        RequestFactory.getInstance().init();
        Request.getInstance().init();
    }


    public static Context getContext() {
        return context;
    }
}
