package com.talentica.bookshelfapp.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.talentica.bookshelfapp.R;

/**
 * Created by rohanr on 8/25/16.
 */
public class CustomEditText extends EditText {

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        //Typeface.createFromAsset doesn't work in the layout editor. Skipping...
        if (isInEditMode()) {
            return;
        }

        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.CustomTextStyle);
        String fontName = styledAttrs.getString(R.styleable.CustomTextStyle_typeface);
        styledAttrs.recycle();

        if (fontName != null) {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontName);
            setTypeface(typeface);
        }
    }
}
