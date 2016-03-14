package com.dudu.aiowner.ui.base;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.dudu.aiowner.R;
import com.dudu.aiowner.databinding.ActivityCommonBinding;
import com.dudu.aiowner.ui.base.observable.ObservableFactory;
import com.dudu.aiowner.utils.ActivitiesManager;
import com.dudu.aiowner.utils.ResUtil;

import java.lang.reflect.Field;

/**
 * Created by sunny_zhang on 2016/1/27.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public MaterialDialog mMaterialDialog;
    protected ActivityCommonBinding baseBinding;

    protected ObservableFactory observableFactory;

    protected View childView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        ActivitiesManager.getInstance().addActivity(this);

        baseBinding = DataBindingUtil.setContentView(this, R.layout.activity_common);

        childView = getChildView();

        baseBinding.mainContainer.addView(childView);

        observableFactory = ObservableFactory.creatInstance();

        baseBinding.setTitle(observableFactory.getTitleObservable());

        baseBinding.setCommon(observableFactory.getCommonObservable());
    }

    protected void showMaterialDialog(String title, String message,
                                      final View.OnClickListener positiveListener, final View.OnClickListener negativeListener,
                                      DialogInterface.OnDismissListener onDismissListener) {
        showMaterialDialog(title, message, getString(R.string.dialog_positive_text), positiveListener, negativeListener, onDismissListener);
    }

    protected void showMaterialDialog(String title, String message, String positiveButtonText,
                                      final View.OnClickListener positiveListener, final View.OnClickListener negativeListener,
                                      DialogInterface.OnDismissListener onDismissListener) {
        mMaterialDialog = new MaterialDialog(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText, positiveListener)
                .setNegativeButton(getString(R.string.dialog_negative_text), negativeListener)
                .setOnDismissListener(onDismissListener);
        mMaterialDialog.setCanceledOnTouchOutside(false);
        mMaterialDialog.show();
        try {
            Field mPositiveButton = mMaterialDialog.getClass().getDeclaredField("mPositiveButton");
            mPositiveButton.setAccessible(true);
            ((Button) mPositiveButton.get(mMaterialDialog)).setTextColor(getResources().getColor(ResUtil.getThemeColorAccent(this)));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    protected void dismissDialog() {
        if (mMaterialDialog != null) {
            mMaterialDialog.dismiss();
            mMaterialDialog = null;
        }
    }

    protected abstract View getChildView();

    public void backLastPage(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivitiesManager.getInstance().removeActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
