package com.dudu.aiowner.ui.base.observable;

/**
 * Created by sunny_zhang on 2016/1/28.
 */
public class ObservableFactory {

    private TitleBarObservable titleBarObservable;

    private CommonObservable commonObservable;

    public ObservableFactory() {

        titleBarObservable = new TitleBarObservable();
        commonObservable = new CommonObservable();
    }

    public static ObservableFactory creatInstance() {

        return new ObservableFactory();
    }

    public TitleBarObservable getTitleObservable() {

        return titleBarObservable;
    }

    public CommonObservable getCommonObservable() {

        return commonObservable;
    }

    public void setToMainUi(){
        titleBarObservable.userIcon.set(true);
        this.titleBarObservable.userCommon.set(false);
        titleBarObservable.hasBackButton.set(false);
        titleBarObservable.titleLogo.set(true);
        commonObservable.hasBottomIcon.set(false);
    }
}
