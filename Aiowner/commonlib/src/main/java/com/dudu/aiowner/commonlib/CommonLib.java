package com.dudu.aiowner.commonlib;

import android.content.Context;


/**
 * Created by dengjun on 2016/1/21.
 * Description :公共库聚合根,需要在application中初始化
 */
public class CommonLib {
    private static CommonLib instance = null;

    private Context context;

    private static ConfigReader mConfigReader;

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
        mConfigReader = new ConfigReader();
        mConfigReader.setIsTest(true);
    }

    public void init(Context context){
       this.context = context;
        mConfigReader = new ConfigReader();
        mConfigReader.readDefaultConfig();
    }

    public ConfigReader getConfigReader(){
        return mConfigReader;
    }

    public static boolean isTest(){
        return mConfigReader.isTest();
    }

    public Context getContext(){
        return context;
    }
}
