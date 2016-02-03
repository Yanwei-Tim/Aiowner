package com.dudu.aiowner.ui.main.observable;

import android.databinding.ObservableField;

/**
 * Created by dengjun on 2016/2/1.
 * Description :
 */
public class MainObservable {
    public final ObservableField<String> remainingOil = new ObservableField<>();
    public final ObservableField<String> gasMileage = new ObservableField<>();
    public final ObservableField<String> totalMileage = new ObservableField<>();


    public MainObservable() {
        remainingOil.set("0%");
        gasMileage.set("0lr");
        totalMileage.set("0");
    }

    public void setRemainingOil(String remainingOil){
        this.remainingOil.set(remainingOil + "%");
    }

    public void setGasMileage(String gasMileage){
        this.gasMileage.set(gasMileage + "lr");
    }

    public void setTotalMileage(String totalMileage){
        this.totalMileage.set(totalMileage);
    }
}