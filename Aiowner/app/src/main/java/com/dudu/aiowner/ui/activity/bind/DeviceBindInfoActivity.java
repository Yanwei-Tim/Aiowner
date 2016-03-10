package com.dudu.aiowner.ui.activity.bind;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dudu.aiowner.ui.base.RBaseActivity;

/**
 * Created by Robi on 2016-03-1011:47.
 */
public class DeviceBindInfoActivity extends RBaseActivity {

    public static final String KEY = "key";
    public static final String KEY_IMEI = "KEY_IMEI";
    public static final String KEY_TYPE = "KEY_TYPE";

    public static final String TYPE_BIND = "TYPE_BIND";
    public static final String TYPE_UNBIND = "TYPE_UNBIND";
    /**
     * 传递过来的绑定状态,是否成功
     */
    private boolean isSuccess;
    private String type;
    private String imei;

    public static void launch(Context context, boolean isSuccess) {
        Intent intent = new Intent(context, DeviceBindInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY, isSuccess);
        bundle.putString(KEY_TYPE, TYPE_BIND);

        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void launch(Context context, String imei) {
        Intent intent = new Intent(context, DeviceBindInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(KEY_IMEI, imei);
        bundle.putString(KEY_TYPE, TYPE_UNBIND);

        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void init() {
        Bundle extras = getIntent().getExtras();
        type = extras.getString(KEY_TYPE, TYPE_BIND);

        if (type.equalsIgnoreCase(TYPE_BIND)) {
            isSuccess = extras.getBoolean(KEY);
        } else if (type.equalsIgnoreCase(TYPE_UNBIND)) {
            imei = extras.getString(KEY_IMEI);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (type.equalsIgnoreCase(TYPE_BIND)) {
            //绑定界面
            replaceFragment(BindFragment.newInstance(isSuccess));
        } else if (type.equalsIgnoreCase(TYPE_UNBIND)) {
            //解绑界面
            replaceFragment(UnbindFragment.newInstance(imei));
        }
    }

    @Override
    protected int getContentView() {
        return 0;
    }
}
