package com.loopeer.android.librarys.scrolltable;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class VerticalScrollView extends ScrollView implements IScroller{

    private IScroller scroller;

    public VerticalScrollView(Context context) {
        this(context, null);
    }

    public VerticalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (scroller != null) {
            scroller.onScrollXY(l, t);
        }
    }

    public void setIScroller(IScroller scroller) {
        this.scroller = scroller;
    }

    @Override
    public void onScrollXY(int offsetX, int offsetY) {
        scrollTo(offsetX, offsetY);
    }
}
