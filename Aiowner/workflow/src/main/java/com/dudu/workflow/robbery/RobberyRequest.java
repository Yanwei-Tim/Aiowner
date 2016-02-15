package com.dudu.workflow.robbery;

/**
 * Created by Administrator on 2016/2/15.
 */
public interface RobberyRequest {
    public void getCarInsuranceAuthState(String cellphone);
    public void requestCarInsuranceAuth();
    public void robberySwitch(String cellphone,int type, int on_off,SwitchCallback callback);
    public void getRobberyState(String cellphone,RobberStateCallback callback);

    public interface SwitchCallback{
        void switchSuccess(boolean success);
        void requestError(String error);
    }

    public interface RobberStateCallback{
        void switchsState(boolean robbery,boolean flashRateTimes,boolean emergencyCutoff,boolean stepOnTheGas);
        void requestError(String error);
    }
}
