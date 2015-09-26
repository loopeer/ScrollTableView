package com.loopeer.android.librarys.scrolltable;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class IHorizontalScrollView extends HorizontalScrollView implements IScroller{

    private IScroller scroller;

    public IHorizontalScrollView(Context context) {
        this(context, null);
    }

    public IHorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
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
