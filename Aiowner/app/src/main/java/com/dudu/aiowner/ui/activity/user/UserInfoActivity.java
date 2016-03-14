package com.dudu.aiowner.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.rest.model.UserInfoResponse;
import com.dudu.aiowner.ui.activity.bind.DeviceBindActivity;
import com.dudu.aiowner.ui.activity.bind.DeviceBindInfoActivity;
import com.dudu.aiowner.ui.activity.bind.T;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.workflow.bind.BindServiceImpl;
import com.dudu.workflow.bind.SimpleBindListener;
import com.dudu.workflow.common.CommonParams;
import com.dudu.workflow.common.RequestFactory;
import com.dudu.workflow.user.UserRequest;

/**
 * Created by Administrator on 2016/2/2.
 */
public class UserInfoActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View getChildView() {

        return LayoutInflater.from(this).inflate(R.layout.activity_user_info, null);
    }

    public void startModifyHeadPortraitAndNickname(View view) {

        startActivity(new Intent(UserInfoActivity.this, ModifyHeadActivity.class));
    }

    public void startModifyPsw(View view) {

        startActivity(new Intent(UserInfoActivity.this, ModifyPswActivity.class));
    }

    public void startGesturePsw(View view) {
        startActivity(new Intent(UserInfoActivity.this, TheftPswActivity.class));
    }

    public void startUserFeedback(View view) {
        startActivity(new Intent(UserInfoActivity.this, UserFeedbackActivity.class));
    }

    /**设备匹配*/
    public void startDeviceMatch(View view) {
//        startActivity(new Intent(UserInfoActivity.this, DeviceMatchAcitivty.class));
//        QrScan.launch(this, 500,500);

        BindServiceImpl.getBindStatus(CommonParams.getInstance().getUserName(), "android", new SimpleBindListener() {
            @Override
            public void onGetBindStatus(boolean isBind, String obeId) {
                if (isBind) {
                    //如果已经绑定
                    DeviceBindInfoActivity.launch(UserInfoActivity.this, obeId);
                } else {
                    showMaterialDialog(getString(R.string.dialog_default_title), getString(R.string.dialog_content_unbind), getString(R.string.dialog_bind_bt_text), v -> {
                        DeviceBindActivity.launch(UserInfoActivity.this);
                        dismissDialog();
                    }, v -> {
                        dismissDialog();
                    }, dialog -> {

                    });
                }
            }

            @Override
            public void onFailed(String msg) {
                T.show(UserInfoActivity.this, msg);
            }
        });


//        DeviceBindActivity.launch(this);
    }

    @Override
    protected void onResume() {

        RequestFactory.getUserRequest().userInfo(new UserRequest.UserInfoCallback() {
            @Override
            public void requestSuccess(UserInfoResponse response) {
                Log.d("UserInfoRequestSuccess", "---------"+response.toString());
            }

            @Override
            public void requestError(String error) {
                Log.d("UserInfoRequestError", error);
            }
        });

        observableFactory.getTitleObservable().titleText.set("");
        observableFactory.getTitleObservable().userIcon.set(false);
        observableFactory.getTitleObservable().userTitleLogo.set(true);
        observableFactory.getTitleObservable().hasBackGround.set(true);
        observableFactory.getTitleObservable().hasUserBackButton.set(true);
        observableFactory.getCommonObservable().hasBottomIcon.set(false);
        observableFactory.getCommonObservable().hasUserBackground.set(true);
        super.onResume();
    }
}
