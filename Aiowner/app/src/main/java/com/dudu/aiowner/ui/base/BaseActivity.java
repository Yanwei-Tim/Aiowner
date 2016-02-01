package com.dudu.aiowner.ui.base;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.dudu.aiowner.R;
import com.dudu.aiowner.databinding.ActivityCommonBinding;
import com.dudu.aiowner.ui.base.observable.ObservableFactory;
import com.dudu.aiowner.utils.ActivitiesManager;

/**
 * Created by sunny_zhang on 2016/1/27.
 */
public abstract class BaseActivity extends Activity {

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
