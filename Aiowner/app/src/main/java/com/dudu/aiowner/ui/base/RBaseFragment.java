package com.dudu.aiowner.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dudu.aiowner.R;

import de.greenrobot.event.EventBus;

/**
 * Created by angcyo on 15-08-31-031.
 */
public abstract class RBaseFragment extends Fragment {

    protected RBaseActivity mBaseActivity;
    protected ViewGroup rootView;
    protected boolean isCreate = false;
    protected RBaseViewHolder mViewHolder;
    protected LayoutInflater mLayoutInflater;
    protected ViewGroup mFragmentLayout;
    protected FrameLayout mContainerLayout;//内容布局

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLayoutInflater = inflater;
        rootView = (ViewGroup) inflater.inflate(R.layout.rsen_base_fragment_layout, container, false);

        int contentView = getContentView();
        if (contentView == 0) {
            View view = createContentView();
            if (view != null) {
                rootView.addView(view,
                        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            mViewHolder = new RBaseViewHolder(rootView);
        } else {
            mViewHolder = new RBaseViewHolder(inflater.inflate(contentView, rootView, true));
        }

        initBaseView();
        initView(rootView);
        initAfter();
        isCreate = true;
        initViewData();
        return rootView;
    }

    protected View createContentView() {
        return null;
    }

    public void showToast(String msg) {
        mBaseActivity.showToast(msg);
    }

    private void initBaseView() {
        mFragmentLayout = (ViewGroup) rootView.findViewById(R.id.fragment_layout);
        mContainerLayout = (FrameLayout) rootView.findViewById(R.id.container);
    }

    protected abstract int getContentView();

    protected abstract void initViewData();

    protected void loadData(Bundle savedInstanceState) {

    }

    protected void initView(View rootView) {

    }

    protected void initAfter() {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isCreate = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isCreate) {
            if (isVisibleToUser) {
                onShow();
            } else {
                onHide();
            }
        }
    }

    protected void onShow() {

    }

    protected void onHide() {

    }

    public static RBaseFragment getInstance(Bundle bundle, Class<? extends RBaseFragment> cls) {
        try {
            RBaseFragment fragment = cls.newInstance();
            fragment.setArguments(bundle);
            return fragment;
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void showMaterialDialog(String title, String message,
                                      final View.OnClickListener positiveListener, final View.OnClickListener negativeListener,
                                      DialogInterface.OnDismissListener onDismissListener) {
        MaterialDialog mMaterialDialog = new MaterialDialog(mBaseActivity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("确认", positiveListener)
                .setNegativeButton("取消", negativeListener)
                .setOnDismissListener(onDismissListener);
        mMaterialDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBaseActivity = (RBaseActivity) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mBaseActivity = (RBaseActivity) activity;
    }

    protected void e(String log) {
        Log.e(new Exception().getStackTrace()[0].getClassName(), log);
    }
}
