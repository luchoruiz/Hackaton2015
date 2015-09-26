package com.android.desafioaudionews.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by usuario on 5/4/15.
 */
public class MSLightTextView extends TextView {

    public MSLightTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        this.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Montserrat-Light-webfont.ttf"));
    }

    public MSLightTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MSLightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText((text), BufferType.EDITABLE);

    }
}

