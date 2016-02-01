package com.dudu.aiowner.utils.customFontUtils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by sunny_zhang on 2016/1/30.
 */
public class DINRegularFontTextView extends TextView {

    public DINRegularFontTextView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        AssetManager assetManager = context.getAssets();
        Typeface font = Typeface.createFromAsset(assetManager, "DINNextLTPro-Regular.otf");
        setTypeface(font);
    }

    public DINRegularFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DINRegularFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
}
