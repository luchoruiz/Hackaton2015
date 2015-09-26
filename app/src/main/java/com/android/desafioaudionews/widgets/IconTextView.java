package com.android.desafioaudionews.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.android.desafioaudionews.R;

/**
 * Created by Lucho on 25/09/2015.
 */
public class IconTextView extends TextView {

        private static int defaultTextColor = Color.WHITE;
        private static float DEFAULT_TEXT_SIZE = 20f;

        public IconTextView(Context context) {
            super(context);
            init();
        }

    private void init() {
        this.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/iconfont.ttf"));
        this.setTextColor(defaultTextColor);
    }

    public IconTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        setAttr(context,attrs);
    }

    public IconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        setAttr(context, attrs);

    }


    private void setAttr(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.mIconTextView, 0, 0);
        if (a.hasValue(R.styleable.mIconTextView_textIconSize)) {
            float textSize = a.getDimension(R.styleable.mIconTextView_textIconSize,DEFAULT_TEXT_SIZE);
            this.setTextSize(textSize);
        }
        if (a.hasValue(R.styleable.mIconTextView_textColor)) {
            int textColor = a.getColor(R.styleable.mIconTextView_textColor,defaultTextColor);
            this.setTextColor(textColor);

        }


    }
    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText((text), BufferType.EDITABLE);
    }
}
