package com.dudu.aiowner.ui.activity.preventTheft;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.databinding.ActivityOwnersCredentialsUploadBinding;
import com.dudu.aiowner.ui.activity.user.UserInfoActivity;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.workflow.common.RequestFactory;
import com.dudu.workflow.guard.GuardRequest;

/**
 * Created by sunny_zhang on 2016/2/2.
 */
public class OwnersCredentialsUploadActivity extends BaseActivity {

    private ActivityOwnersCredentialsUploadBinding ownersCredentialsUploadBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ownersCredentialsUploadBinding = ActivityOwnersCredentialsUploadBinding.bind(childView);

    }

    @Override
    protected View getChildView() {

        return LayoutInflater.from(OwnersCredentialsUploadActivity.this).inflate(R.layout.activity_owners_credentials_upload, null);
    }

    public void userInfo(View view) {
        startActivity(new Intent(OwnersCredentialsUploadActivity.this, UserInfoActivity.class));
    }

    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("车辆防盗");
        observableFactory.getTitleObservable().userIcon.set(true);
        observableFactory.getCommonObservable().hasBottomIcon.set(false);
        super.onResume();
    }

    public void startOwnersCredentialsUploadOk(View view) {
        RequestFactory.getGuardRequest().getTheftLicenseResult(new GuardRequest.TheftLicenceCallBack() {
            @Override
            public void LicenceSucceed(boolean succeed) {
                Log.d("LicenceSucceed", "认证成功！");
            }

            @Override
            public void LicenceError(String error) {
                Log.d("LicenceError", "认证失败！");
            }
        });

    }

    public void chooseDrivingLicense(View view) {

        Log.d("upload", "上传驾驶证图片");

        RequestFactory.getGuardRequest().getTheftUploadResult(new GuardRequest.UploadLicenceCallBack() {
            @Override
            public void uploadSucceed(boolean succeed) {
                Log.d("uploadSucceed", "上传成功！");
                startActivity(new Intent(OwnersCredentialsUploadActivity.this, OwnersCredentialsUploadOkActivity.class));
            }

            @Override
            public void uploadError(String error) {
                Log.d("uploadError", "上传失败！");
            }
        });
    }

    public void chooseTravelLicense(View view){

        Log.d("upload", "上传行驶证图片");

    }
}
