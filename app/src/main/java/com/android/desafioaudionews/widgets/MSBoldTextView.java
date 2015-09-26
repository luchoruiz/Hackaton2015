package com.android.desafioaudionews.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by usuario on 5/4/15.
 */
public class MSBoldTextView extends TextView {

    public MSBoldTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        this.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/montserrat-bold-webfont.ttf"));
    }

    public MSBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MSBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText((text), BufferType.EDITABLE);

    }
}

