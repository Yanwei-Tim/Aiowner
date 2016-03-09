package com.dudu.aiowner.ui.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.activity.bind.T;

public abstract class RBaseActivity extends BaseActivity {

    protected LayoutInflater mLayoutInflater;
    protected FrameLayout mContainerLayout;//内容包裹布局
    protected RBaseViewHolder mViewHolder;
    protected RBaseActivity mBaseActivity;

    /**
     * 获取ActionBar的高度
     */
    public static float getActionBarHeight(Context context) {
        TypedArray actionbarSizeTypedArray = context.obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        float h = actionbarSizeTypedArray.getDimension(0,
                context.getResources().getDimension(R.dimen.abc_action_bar_default_height_material));
        return h;
    }

    /**
     * 获取状态栏的高度
     */
    public static float getStatusBarHeight(Context context) {
        float result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimension(resourceId);
//            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mBaseActivity = this;
        super.onCreate(savedInstanceState);
        mLayoutInflater = LayoutInflater.from(this);
        initBaseView();

        initView(savedInstanceState);
        initAfter();

        initEvent();
    }

    protected void initEvent() {

    }

    protected void initAfter() {

    }

    @Override
    protected View getChildView() {
        mContainerLayout = new FrameLayout(this);
        mContainerLayout.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        return mContainerLayout;
    }

    protected abstract void initView(Bundle savedInstanceState);

    private void initBaseView() {
        /*设置内容布局*/
        int contentView = getContentView();
        if (contentView == 0) {
            View view = createContentView();
            if (view != null) {
                mContainerLayout.addView(view,
                        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            mViewHolder = new RBaseViewHolder(mContainerLayout);
        } else {
            mViewHolder = new RBaseViewHolder(mLayoutInflater.inflate(contentView, mContainerLayout, true));
        }
    }

    protected View createContentView() {
        return null;
    }

    @LayoutRes
    protected abstract int getContentView();


    public void launchActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    public void launchActivity(Class c, Bundle args) {
        Intent intent = new Intent(this, c);
        intent.putExtras(args);
        startActivity(intent);
    }

    public void showToast(String msg) {
        T.show(this, msg);
    }
}
