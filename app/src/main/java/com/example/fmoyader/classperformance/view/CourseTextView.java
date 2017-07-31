package com.example.fmoyader.classperformance.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by fmoyader on 31/7/17.
 */

public class CourseTextView extends android.support.v7.widget.AppCompatTextView {
    public CourseTextView(Context context) {
        super(context);
    }

    public CourseTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CourseTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        String string = text.toString();
        if (string.length() > 24) {
            string = string.substring(0, 21) + "...";
        }
        super.setText(string, type);
    }
}
