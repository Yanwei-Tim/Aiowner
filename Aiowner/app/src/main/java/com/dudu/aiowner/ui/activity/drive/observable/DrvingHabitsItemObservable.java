package com.dudu.aiowner.ui.activity.drive.observable;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.dudu.aiowner.R;
import com.dudu.aiowner.commonlib.CommonLib;
import com.dudu.aiowner.ui.activity.drive.DrvingHabitsInfoAcitity;

/**
 * Created by dengjun on 2016/2/11.
 * Description :
 */
public class DrvingHabitsItemObservable {
    private Context mContext;

    public final ObservableField<String> driveHabitsType = new ObservableField<>();
    public final ObservableField<String> driveHabitsTime = new ObservableField<>();
    public final ObservableField<String> driveHabitsDate = new ObservableField<>();

    public final ObservableField<Drawable> driveHabitsTypeImage = new ObservableField<>();
    public final ObservableField<Drawable> driveHabitsTimeImage = new ObservableField<>();


    public DrvingHabitsItemObservable() {
        mContext = CommonLib.getInstance().getContext();

        driveHabitsType.set("乐驰者");
        driveHabitsTime.set("13:00");
        driveHabitsDate.set("2016/02/11");
        driveHabitsTypeImage.set(mContext.getResources().getDrawable(R.drawable.green_ellipse));
        driveHabitsTimeImage.set(mContext.getResources().getDrawable(R.drawable.green_rect));
    }

    public void startDriveHabitsInfoAcitity(View view){
        Intent intent = new Intent(mContext, DrvingHabitsInfoAcitity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
  
}
