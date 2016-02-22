package com.dudu.aiowner.ui.activity.testSpeed.observable;

import android.databinding.ObservableField;

/**
 * Created by sunny_zhang on 2016/2/22.
 * Description :
 */
public class TestSpeedObservable {

    public final ObservableField<String> accTestingResult = new ObservableField<>();

    public TestSpeedObservable() {
        accTestingResult.set("0.00s");
    }

    public void setAccTestingData(float accTestingResult) {
        this.accTestingResult.set(accTestingResult + "s");
    }
}

