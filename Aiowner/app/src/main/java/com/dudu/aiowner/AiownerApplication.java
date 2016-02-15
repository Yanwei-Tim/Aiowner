package com.dudu.aiowner;

import android.app.Application;

import com.dudu.aiowner.commonlib.CommonLib;
import com.dudu.workflow.RequestFactory;

/**
 * Created by dengjun on 2016/2/12.
 * Description :
 */
public class AiownerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CommonLib.getInstance().init(this);
        RequestFactory.getInstance().init();
    }
}
