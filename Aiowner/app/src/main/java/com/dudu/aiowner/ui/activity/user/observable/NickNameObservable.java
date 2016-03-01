package com.dudu.aiowner.ui.activity.user.observable;

import android.databinding.ObservableField;

/**
 * Created by sunny_zhang on 2016/3/1.
 */
public class NickNameObservable {
    public final ObservableField<String> newName = new ObservableField<>();

    public NickNameObservable() {
        this.newName.set("Jack");
    }

}

