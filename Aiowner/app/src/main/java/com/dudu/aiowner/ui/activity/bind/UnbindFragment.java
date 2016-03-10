package com.dudu.aiowner.ui.activity.bind;

import android.os.Bundle;

import com.dudu.aiowner.R;
import com.dudu.aiowner.rest.model.BindRequest;
import com.dudu.aiowner.ui.base.RBaseFragment;
import com.dudu.workflow.bind.BindServiceImpl;
import com.dudu.workflow.bind.SimpleBindListener;

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
            BindServiceImpl.unbind(new BindRequest(), new SimpleBindListener(){
                @Override
                public void onUnBind(boolean isSuccess, String msg) {
                    super.onUnBind(isSuccess, msg);
                    showToast(msg);
                    if (isSuccess) {
                        mBaseActivity.finish();
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
