package com.dudu.aiowner.ui.activity.bind;

import android.os.Bundle;

import com.dudu.aiowner.R;
import com.dudu.aiowner.rest.model.BindRequest;
import com.dudu.aiowner.ui.base.RBaseFragment;
import com.dudu.workflow.bind.BindServiceImpl;
import com.dudu.workflow.bind.SimpleBindListener;
import com.dudu.workflow.common.CommonParams;

/**
 * Created by Robi on 2016-03-1015:27.
 */
public class UnbindFragment extends RBaseFragment {

    public static final String KEY = "key";

    private String imei;

    public static RBaseFragment newInstance(String imei) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY, imei);
        return getInstance(bundle, UnbindFragment.class);
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        super.loadData(savedInstanceState);
        imei = getArguments().getString(KEY, imei);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_unbind_layout;
    }

    @Override
    protected void initViewData() {
        mViewHolder.tV(R.id.imei).setText(imei);

        mViewHolder.v(R.id.unbind).setOnClickListener(v -> {
            BindServiceImpl.unbind(new BindRequest(CommonParams.getInstance().getUserName(), "android", imei), new SimpleBindListener(){
                @Override
                public void onUnBind(boolean isSuccess, String msg) {
                    super.onUnBind(isSuccess, msg);
                    if (isSuccess) {
                        mBaseActivity.finish();
                        showToast(mBaseActivity.getString(R.string.tip_unbind_success));
                    } else {
                        showToast(msg);
                    }
                }

                @Override
                public void onFailed(String msg) {
                    super.onFailed(msg);
                    showToast(msg);
                }
            });
        });
    }
}
