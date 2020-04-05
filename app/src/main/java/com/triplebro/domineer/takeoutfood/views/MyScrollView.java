package com.triplebro.domineer.takeoutfood.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.triplebro.domineer.takeoutfood.interfaces.OnScrollChangedListener;

public class MyScrollView extends ScrollView {

    private OnScrollChangedListener onScrollChangedListener;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnScrollChangedListener(OnScrollChangedListener scrollViewListener) {
        this.onScrollChangedListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (onScrollChangedListener != null) {
            onScrollChangedListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }
}
