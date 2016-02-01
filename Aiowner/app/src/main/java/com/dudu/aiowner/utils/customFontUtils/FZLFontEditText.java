package com.dudu.aiowner.utils.customFontUtils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by sunny_zhang on 2016/2/1.
 */
public class FZLFontEditText extends EditText {
    public FZLFontEditText(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        AssetManager assetManager = context.getAssets();
        Typeface font = Typeface.createFromAsset(assetManager, "FZLTXHK.TTF");
        setTypeface(font);
    }

    public FZLFontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FZLFontEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
