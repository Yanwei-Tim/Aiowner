package com.dudu.aiowner.ui.observable;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

/**
 * Created by sunny_zhang on 2016/1/28.
 */
public class TitleBarObservable {

    public final ObservableBoolean userCommon = new ObservableBoolean();

    public final ObservableBoolean userIcon = new ObservableBoolean();

    public final ObservableField<String> titleText = new ObservableField<>();

    public TitleBarObservable() {

        this.userCommon.set(true);
        this.userIcon.set(false);
        this.titleText.set("Aiowner");
    }
}
