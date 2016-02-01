package com.dudu.aiowner.utils.customFontUtils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by sunny_zhang on 2016/2/1.
 */
public class FZLFontButton extends Button {
    public FZLFontButton(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        AssetManager assetManager = context.getAssets();
        Typeface font = Typeface.createFromAsset(assetManager, "FZLTXHK.TTF");
        setTypeface(font);
    }

    public FZLFontButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FZLFontButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
