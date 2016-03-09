package com.dudu.aiowner.ui.activity.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dudu.aiowner.R;
import com.dudu.aiowner.rest.model.BindRequest;
import com.dudu.aiowner.rest.model.BindResponse;
import com.dudu.aiowner.ui.activity.bind.BindServiceImpl;
import com.dudu.aiowner.ui.activity.bind.T;
import com.dudu.aiowner.ui.base.BaseActivity;
import com.dudu.workflow.common.CommonParams;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sunny_zhang on 2016/3/1.
 */
public class DeviceMatchAcitivty extends BaseActivity {

    private TextView imei;

    @Override
    protected View getChildView() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.activity_device_match, null);
        inflate.setLayoutParams(new RelativeLayout.LayoutParams(-1,-1));
        return  inflate;
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
        findViewById(R.id.device_match_bind_btn).setOnClickListener(view -> {
            //开始绑定
            final String phone = CommonParams.getInstance().getUserName();
            String obeId = imei.getText().toString();
            if (!TextUtils.isEmpty(obeId)) {
                BindServiceImpl.bind(new BindRequest(phone, "android", obeId), new Callback<BindResponse>() {
                    @Override
                    public void onResponse(Call<BindResponse> call, Response<BindResponse> response) {
                        BindResponse body = response.body();
                        if (body != null) {
                            T.show(DeviceMatchAcitivty.this, body.resultMsg);
                            if (body.resultCode == 0) {
                                //绑定成功
                            } else {

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BindResponse> call, Throwable t) {
                        T.show(DeviceMatchAcitivty.this, t.toString());
                    }
                });
            }
        }
    );
}
}
