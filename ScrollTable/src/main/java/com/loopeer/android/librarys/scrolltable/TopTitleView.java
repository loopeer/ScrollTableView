package com.loopeer.android.librarys.scrolltable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;

public class TopTitleView extends View {

    private static final int DEFAULT_HEIGHT = 100;
    private static final int DEFAULT_WIDTH = 240;
    private static final int DEFAULT_MARGIN = 4;

    private Paint mPaintTextNormal;
    private int mTextNormalColor;
    private int mItemIndicatorColor;
    private float mTextNormal;

    private int mItemHeight = DEFAULT_HEIGHT;
    private int mItemPlaceHeight = DEFAULT_HEIGHT;
    private int mItemWidth = DEFAULT_WIDTH;
    private int mItemMargin = DEFAULT_MARGIN;
    private int column;

    private ArrayList<String> titles;

    public TopTitleView(Context context) {
        this(context, null);
    }

    public TopTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        initPaint();
        titles = new ArrayList<>();
    }

    private void initPaint() {
        mTextNormalColor = ContextCompat.getColor(getContext(), R.color.table_text_secondary_color);
        mItemIndicatorColor = ContextCompat.getColor(getContext(), R.color.table_divider_color);
        mTextNormal = getResources().getDimension(R.dimen.table_default_title_size);

        mPaintTextNormal = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTextNormal.setColor(mTextNormalColor);
        mPaintTextNormal.setTextSize(mTextNormal);

        mItemPlaceHeight = getResources().getDimensionPixelSize(R.dimen.table_top_title_place);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        mItemHeight = sizeHeight;
        setMeasuredDimension(column * mItemWidth + (column - 1) * mItemMargin, mItemHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawItem(canvas);
    }

    private void drawItem(Canvas canvas) {
        for (int columnIndex = 0; columnIndex < column; columnIndex++) {
            String content = titles.get(columnIndex);
            Paint.FontMetrics fontMetrics = mPaintTextNormal.getFontMetrics();
            float fontHeight = fontMetrics.bottom - fontMetrics.top;
            float textWidth = mPaintTextNormal.measureText(content);
            float y = mItemPlaceHeight - (mItemPlaceHeight - fontHeight) / 2 - fontMetrics.bottom;

            float x = (mItemMargin + mItemWidth) * columnIndex + mItemWidth / 2 - textWidth / 2;

            canvas.drawText(content, x, y, mPaintTextNormal);

        }
    }

    public void updateTitles(ArrayList<String> data) {
        titles.clear();
        titles.addAll(data);
        updateView();
    }

    private void updateView() {
        invalidate();
        column = titles.size();
    }

}
