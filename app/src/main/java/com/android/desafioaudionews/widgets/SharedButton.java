package com.android.desafioaudionews.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;
import android.widget.LinearLayout;

import com.android.desafioaudionews.R;

/**
 * Created by Lucho on 25/09/2015.
 */
public class SharedButton extends LinearLayout implements Checkable {
    IconTextView icon;
    View dividerView;
    // DEFAULT COLORS

    int defaultSelectedIconColor;
    int defaultSelectedDividerColor;
    int defaultUnSelectedIconColor = Color.GRAY;;
    int defaultUnSelectedDividerColor = Color.TRANSPARENT;
    //FINAL COLORS
    int selectedIconColor;
    int selectedDividerColor;
    int unSelectedIconColor;
    int unSelectedDividerColor;
    //DEFAULT SIZES
    private static float DEFAULT_TEXT_SIZE = 20f;

    private OnCheckedChangeListener mOnCheckedChangeListener;
    private boolean mChecked;


    public SharedButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        // get attr
        setAttr(context, attrs);

    }

    private void setAttr(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.mSharedButton, 0, 0);
        if (a.hasValue(R.styleable.mSharedButton_textSize)) {
            float iconSize = a.getDimension(R.styleable.mSharedButton_textSize,DEFAULT_TEXT_SIZE);
            setIconSize(iconSize);
        }
        if (a.hasValue(R.styleable.mSharedButton_iconColorSelected)) {
            selectedIconColor = a.getColor(R.styleable.mSharedButton_iconColorSelected,defaultSelectedIconColor);

        }
        if (a.hasValue(R.styleable.mSharedButton_iconColorUnSelected)) {
            unSelectedIconColor = a.getColor(R.styleable.mSharedButton_iconColorUnSelected,defaultUnSelectedIconColor);
        }
        if (a.hasValue(R.styleable.mSharedButton_dividerColorSelected)) {
            selectedDividerColor = a.getColor(R.styleable.mSharedButton_dividerColorSelected,defaultSelectedDividerColor);
        }
        if (a.hasValue(R.styleable.mSharedButton_dividerColorUnSelected)) {
            unSelectedDividerColor = a.getColor(R.styleable.mSharedButton_dividerColorUnSelected,defaultUnSelectedDividerColor);
        }
        if (a.hasValue(R.styleable.mSharedButton_unSelecteManually)) {
        }
        if (a.hasValue(R.styleable.mSharedButton_checked)) {
            this.setChecked(a.getBoolean(R.styleable.mSharedButton_checked,false));
        }else{
            this.setChecked(false);
        }

    }

    public SharedButton(Context context) {
        super(context);
        init();

    }

    public SharedButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        // get attr
        setAttr(context,attrs);

    }

    private void init() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li;
        li = (LayoutInflater)getContext().getSystemService(infService);
        li.inflate(R.layout.custom_shared_button, this, true);
        icon = (IconTextView) findViewById(R.id.iconButton);
        dividerView = (View) findViewById(R.id.dividerView);

        defaultSelectedIconColor = getResources().getColor(R.color.primary);
        defaultSelectedDividerColor = getResources().getColor(R.color.primary);
        defaultUnSelectedDividerColor = Color.TRANSPARENT;
    }

    public void setIcon(){

    }

    public void setText(){

    }

    public void setIconSize(float size){
        icon.setTextSize(size);

    }
    //this method is used to set icon through fonts
    public void setText(char text){
        icon.setText(String.valueOf(text));
    }


    @Override
    public void setChecked(boolean checked) {
        mChecked = checked;
        if(checked){
            icon.setTextColor(selectedIconColor);
            dividerView.setBackgroundColor(selectedDividerColor);

        }else{
            icon.setTextColor(unSelectedIconColor);
            dividerView.setBackgroundColor(unSelectedDividerColor);
        }
        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener.onCheckedChanged(this, mChecked);
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                toggle();
                break;
            default:
        }
        return true;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }

    public static interface OnCheckedChangeListener {
        void onCheckedChanged(SharedButton buttonView, boolean isChecked);
    }
}

