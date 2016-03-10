package com.dudu.workflow.bind;

/**
 * Created by Robi on 2016-03-1010:26.
 */
public interface IBindListener {
    void onBind(boolean isSuccess, String msg);
    void onUnBind(boolean isSuccess, String msg);
    void onGetBindStatus(boolean isBind, String imei);
    void onFailed(String msg);
}
