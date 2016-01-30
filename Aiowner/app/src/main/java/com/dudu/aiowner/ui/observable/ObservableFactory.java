package com.dudu.aiowner.ui.observable;

/**
 * Created by sunny_zhang on 2016/1/28.
 */
public class ObservableFactory {

    private static ObservableFactory observableFactory;

    private TitleBarObservable titleBarObservable;

    private CommonObservable commonObservable;

    public ObservableFactory() {

    }

    public static ObservableFactory getInstance() {

        if (observableFactory == null) {
            observableFactory = new ObservableFactory();
        }
        return observableFactory;
    }

    public TitleBarObservable getTitleObservable() {

        if (titleBarObservable == null) {
            titleBarObservable = new TitleBarObservable();
        }
        return titleBarObservable;
    }

    public CommonObservable getCommonObservable() {

        if (commonObservable == null) {
            commonObservable = new CommonObservable();
        }
        return commonObservable;
    }
}
