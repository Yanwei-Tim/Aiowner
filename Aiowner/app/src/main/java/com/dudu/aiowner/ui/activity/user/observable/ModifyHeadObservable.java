package com.dudu.aiowner.ui.activity.user.observable;

import android.databinding.ObservableField;

/**
 * Created by sunny_zhang on 2016/2/22.
 * Description :
 */
public class ModifyHeadObservable {
    public final ObservableField<String> nickName = new ObservableField<>();
    public ModifyHeadObservable(){
        this.nickName.set("Jack");
    }
}

