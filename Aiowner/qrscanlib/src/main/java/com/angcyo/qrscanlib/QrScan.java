package com.angcyo.qrscanlib;

import android.app.Activity;

import com.yo.libs.app.DimensCodeTools;

/**
 * Created by Robi on 2016-03-0911:43.
 */
public class QrScan {
    public static void launch(Activity activity) {
        DimensCodeTools.startScan(activity);
    }

    public static void launch(Activity activity, int qrWidth, int qrHeight) {
        DimensCodeTools.starScanWithQRSize(activity, qrWidth, qrHeight);
    }
}
