package com.dudu.aiowner.ui.activity.user;

<<<<<<< HEAD
<<<<<<< HEAD:Aiowner/app/src/main/java/com/dudu/aiowner/ui/activity/user/GesturePswActivity.java
/**
 * Created by Administrator on 2016/2/2.
 */
public class GesturePswActivity {
=======
=======
>>>>>>> 579f22a0aafce27064d4cc20effa448db2a10642
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.ui.base.BaseActivity;

/**
 * Created by sunny_zhang on 2016/3/2.
 */
public class ConfirmIdentifyingCodeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View getChildView() {

        return LayoutInflater.from(this).inflate(R.layout.acitivty_confirm_identifyingcode, null);
    }

    public void startNewGesturePsw(View view) {

        startActivity(new Intent(ConfirmIdentifyingCodeActivity.this, NewGesturePswActivity.class));
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
<<<<<<< HEAD
>>>>>>> 52ada4f... 增加防盗防劫-查看上传资料页面；个人中心-手势设置、密码设置相关页面:Aiowner/app/src/main/java/com/dudu/aiowner/ui/activity/user/ConfirmIdentifyingCodeActivity.java
=======
>>>>>>> 579f22a0aafce27064d4cc20effa448db2a10642
}