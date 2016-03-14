package com.dudu.aiowner.ui.activity.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dudu.aiowner.R;
import com.dudu.aiowner.rest.model.BindRequest;
import com.dudu.aiowner.ui.activity.bind.DeviceBindInfoActivity;
import com.dudu.aiowner.ui.activity.bind.T;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.workflow.bind.BindServiceImpl;
import com.dudu.workflow.bind.SimpleBindListener;
import com.dudu.workflow.common.CommonParams;

/**
 * Created by sunny_zhang on 2016/3/1.
 */
public class DeviceMatchActivity extends BaseActivity {

    private TextView imei;

    @Override
    protected View getChildView() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.activity_device_match, null);
        inflate.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        return inflate;
    }

    @Override
    protected void onResume() {
        observableFactory.getTitleObservable().titleText.set("");
        observableFactory.getTitleObservable().userIcon.set(false);
        observableFactory.getTitleObservable().hasBackGround.set(true);
        observableFactory.getTitleObservable().hasUserBackButton.set(true);
        observableFactory.getCommonObservable().hasBottomIcon.set(false);
        observableFactory.getCommonObservable().hasUserBackground.set(true);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imei = ((TextView) findViewById(R.id.imei));

        //单击开始绑定
        findViewById(R.id.device_match_bind_btn).setOnClickListener(view -> {
                    final String phone = CommonParams.getInstance().getUserName();
                    String obeId = imei.getText().toString();
                    if (!TextUtils.isEmpty(obeId)) {
                        BindServiceImpl.bind(new BindRequest(phone, "android", obeId), new SimpleBindListener() {
                            @Override
                            public void onBind(boolean isSuccess, String msg) {
                                T.show(DeviceMatchActivity.this, msg);
                                DeviceBindInfoActivity.launch(DeviceMatchActivity.this, isSuccess);
                                if (isSuccess) {
                                    //绑定成功
                                    finish();
                                }
                            }

                            @Override
                            public void onFailed(String msg) {
                                T.show(DeviceMatchActivity.this, msg);
                            }
                        });
                    } else {
                        imei.setError(getString(R.string.tip_imei_error));
                    }
                }
        );
    }
}
