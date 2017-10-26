package com.gitzzp.ecode.baselib.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author gitzzp
 * @date 17/10/25 18:58
 * @describe 重写textView 使其在左侧使用时 如果长度过长 会压缩自己 不会挤出其他控件
 */
public class WrapWidthTextView extends TextView {
    public WrapWidthTextView(Context context) {
        super(context);
    }

    public WrapWidthTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapWidthTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Layout layout = getLayout ();
        if (layout != null) {
            int width = (int) Math.ceil (getMaxLineWidth (layout))
                    + getCompoundPaddingLeft () + getCompoundPaddingRight ();
            int height = getMeasuredHeight ();
            setMeasuredDimension (width, height);
        }
    }

    private float getMaxLineWidth (Layout layout) {
        float max_width = 0.0f;
        int lines = layout.getLineCount ();
        for (int i = 0; i < lines; i++) {
            if (layout.getLineWidth (i) > max_width) {
                max_width = layout.getLineWidth (i);
            }
        }
        return max_width;
    }
}
