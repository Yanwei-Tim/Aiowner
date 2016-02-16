package com.dudu.aiowner.commonlib;

import android.content.Context;

import com.dudu.aiowner.commonlib.xml.ConfigReader;


/**
 * Created by dengjun on 2016/1/21.
 * Description :公共库聚合根,需要在application中初始化
 */
public class CommonLib {
    private static CommonLib instance = null;

    private Context context;

    public static CommonLib getInstance(){
        if (instance == null){
            synchronized (CommonLib.class){
                if (instance == null){
                    instance = new CommonLib();
                }
            }
        }
        return instance;
    }

    private CommonLib() {

    }

    public void init(){
        ConfigReader.getInstance().setIsTest(true);
        ConfigReader.getInstance().initTestConfig();
    }

    public void init(Context context){
       this.context = context;
        ConfigReader.getInstance().readDefaultConfig();
    }

    public Context getContext(){
        return context;
    }
}
