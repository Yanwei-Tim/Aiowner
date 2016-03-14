package com.dudu.aiowner.ui.activity.bind;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.base.RBaseFragment;

/**
 * Created by Robi on 2016-03-1011:54.
 */
public class BindFragment extends RBaseFragment {

    public static final String KEY = "key";
    /**
     * 传递过来的绑定状态,是否成功
     */
    private boolean isSuccess;

//    public static RBaseFragment newInstance(boolean isSuccess) {
//        RBaseFragment fragment = new BindFragment();
//        Bundle bundle = new Bundle();
//        bundle.putBoolean(KEY, isSuccess);
//        fragment.setArguments(bundle);
//        return fragment;
//    }

    public static RBaseFragment newInstance(boolean isSuccess) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY, isSuccess);
        return getInstance(bundle, BindFragment.class);
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        isSuccess = getArguments().getBoolean(KEY);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_device_bind_status;
    }

    @Override
    protected void initViewData() {
        String tip1, tip2, ok;
        int imgId;
        if (isSuccess) {
            tip1 = getString(R.string.tip_bind_success);
            tip2 = getString(R.string.tip_bind_success2);
            imgId = R.drawable.ok_icon;
            ok = mBaseActivity.getString(R.string.tip_bt_bind_success);
        } else {
            tip1 = getString(R.string.tip_bind_failed);
            tip2 = getString(R.string.tip_bind_failed2);
            imgId = R.drawable.theft_icon_through;
            ok = mBaseActivity.getString(R.string.tip_bt_bind_failed);
        }

        mViewHolder.tV(R.id.tvBindTip).setText(tip1);
        mViewHolder.tV(R.id.tvBindTip2).setText(tip2);
        mViewHolder.imgV(R.id.img).setImageResource(imgId);
        TextView okButton = mViewHolder.tV(R.id.ok);
        okButton.setText(ok);

        okButton.setOnClickListener(v -> {
            mBaseActivity.onBackPressed();
        });
    }

    @Override
    protected void initView(View rootView) {
        rootView.setBackgroundResource(android.R.color.white);
    }
}
