package com.dudu.workflow.driving;

/**
 * Created by Administrator on 2016/2/17.
 */
public interface DrivingRequest {

    public void startacceleratedTesting(int type, RequestCallback callback);

    public interface RequestCallback{
        public void requestSuccess(boolean success);
    }
}
