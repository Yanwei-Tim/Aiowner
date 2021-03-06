package com.dudu.aiowner.receiver;

import android.content.Context;
import android.util.Log;

import com.dudu.aiowner.AiownerApplication;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

/**
 * Created by Administrator on 2016/2/16.
 */
public class ReceiverRegister {

    private static final String TAG ="ReceiverRegister";

    public static void registPushManager(String username) {
        // 开启logcat输出，方便debug，发布时请关闭
        final Context context = AiownerApplication.getContext();
        XGPushConfig.enableDebug(context, true);
        XGPushManager.registerPush(context, username, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object o, int i) {
                Log.d(TAG, "注册成功");
            }

            @Override
            public void onFail(Object o, int i, String s) {
                Log.d(TAG, "注册失败" + s);
            }
        });
    }
}
