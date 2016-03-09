package com.dudu.workflow.guard;

import com.dudu.aiowner.rest.model.TheftStatusResponse;

/**
 * Created by Administrator on 2016/2/16.
 */
public interface GuardRequest {
    public void isAntiTheftOpened(LockStateCallBack callBack);

    public void lockCar(LockStateCallBack callBack);

    public void unlockCar(UnlockCallBack callBack);

    public void getTheftStatus(TheftStatusCallBack callBack);

    public void getTheftUploadResult(UploadLicenceCallBack callBack);

    public void getTheftLicenseResult(TheftLicenceCallBack callBack);

    public interface LockStateCallBack {
        public void hasLocked(boolean locked);

        public void requestError(String error);
    }

    public interface UnlockCallBack {
        public void unlocked(boolean locked);

        public void requestError(String error);
    }

    public interface TheftStatusCallBack {
        public void getTheftStatus(TheftStatusResponse response);

        public void requestError(String error);
    }

    public interface UploadLicenceCallBack {
        public void uploadSucceed(boolean succeed);

        public void uploadError(String error);
    }

    public interface TheftLicenceCallBack {
        public void LicenceSucceed(boolean succeed);

        public void LicenceError(String error);
    }

}
