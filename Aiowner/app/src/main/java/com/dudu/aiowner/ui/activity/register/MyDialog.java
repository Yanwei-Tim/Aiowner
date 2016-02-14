package com.dudu.aiowner.ui.activity.register;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.dudu.aiowner.R;

/**
 * Created by Administrator on 2016/2/14.
 */
public class MyDialog extends Dialog {
    Context mContext;
    public MyDialog(Context context) {
        super(context);
        this.mContext=context;
    }

    public MyDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.mydialog);
    }
}
