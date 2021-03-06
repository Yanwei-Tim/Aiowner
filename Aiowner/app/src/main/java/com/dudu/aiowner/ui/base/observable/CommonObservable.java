package com.dudu.aiowner.ui.base.observable;

import android.databinding.ObservableBoolean;

/**
 * Created by sunny_zhang on 2016/1/28.
 */
public class CommonObservable {

    public final ObservableBoolean hasTitle = new ObservableBoolean();
    public final ObservableBoolean hasBottomIcon = new ObservableBoolean();
    public final ObservableBoolean hasUserBackground = new ObservableBoolean();

    public CommonObservable() {

        this.hasTitle.set(true);
        this.hasBottomIcon.set(true);
        this.hasUserBackground.set(false);
    }
}
