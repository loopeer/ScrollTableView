package com.loopeer.android.librarys.scrolltable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class LeftTitleView extends View {

    private Paint mPaintTextNormal;
    private Paint mPaintItemIndicatorCircle;
    private Paint mPaintItemIndicatorLine;

    private int mTextNormalColor;
    private int mItemIndicatorColor;
    private float mTextNormal;

    private int mItemHeight;
    private int mItemWidth;
    private int mItemMargin;
    private int row;
    private int mItemIndicatorCircleRadius;
    private int mItemIndicatorLineWidth;

    ArrayList<String> titles;

    public LeftTitleView(Context context) {
        this(context, null);
    }

    public LeftTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeftTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        initData();
        initPaint();
        titles = new ArrayList<>();
    }

    private void initData() {
        mItemHeight =  getResources().getDimensionPixelSize(R.dimen.table_item_height);
        mItemWidth = getResources().getDimensionPixelSize(R.dimen.table_item_width);
        mItemMargin = getResources().getDimensionPixelSize(R.dimen.table_item_margin);
        mItemIndicatorCircleRadius = getResources().getDimensionPixelSize(R.dimen.table_header_circle_radius);
        mItemIndicatorLineWidth = getResources().getDimensionPixelSize(R.dimen.table_header_indicator_width);
    }

    private void initPaint() {
        mTextNormalColor = ContextCompat.getColor(getContext(), R.color.table_text_secondary_color);
        mItemIndicatorColor = ContextCompat.getColor(getContext(), R.color.table_divider_color);
        mTextNormal = getResources().getDimension(R.dimen.table_default_title_size);

        mPaintTextNormal = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTextNormal.setColor(mTextNormalColor);
        mPaintTextNormal.setTextSize(mTextNormal);

        mPaintItemIndicatorCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintItemIndicatorCircle.setColor(mItemIndicatorColor);
        mPaintItemIndicatorCircle.setStyle(Paint.Style.FILL);


        mPaintItemIndicatorLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintItemIndicatorLine.setColor(mItemIndicatorColor);
        mPaintItemIndicatorLine.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        mItemWidth = sizeWidth;
        setMeasuredDimension(mItemWidth, row * mItemHeight + (row + 1) * mItemMargin);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawItem(canvas);
    }

    private void drawItem(Canvas canvas) {
        for (int rowIndex = 0; rowIndex < row; rowIndex++) {
            String content = titles.get(rowIndex);
            Paint.FontMetrics fontMetrics = mPaintTextNormal.getFontMetrics();
            float fontHeight = fontMetrics.bottom - fontMetrics.top;
            float textWidth = mPaintTextNormal.measureText(content);
            float y = rowIndex * (mItemHeight + mItemMargin) + mItemHeight - (mItemHeight - fontHeight) / 2 - fontMetrics.bottom - mItemHeight / 2 + getResources().getDimension(R.dimen.table_default_margin_top) - mItemMargin / 2 + mItemMargin;

            float x = mItemWidth - textWidth - 2 * mItemIndicatorCircleRadius - mItemIndicatorLineWidth - 12;

            canvas.drawText(content, x, y, mPaintTextNormal);

            canvas.drawCircle(mItemWidth - mItemIndicatorLineWidth - mItemIndicatorCircleRadius,
                    rowIndex * (mItemHeight + mItemMargin) + getResources().getDimension(R.dimen.table_default_margin_top) - mItemMargin / 2 + mItemMargin,
                    mItemIndicatorCircleRadius, mPaintItemIndicatorCircle);

            canvas.drawRect(mItemWidth - mItemIndicatorLineWidth,
                    rowIndex * (mItemHeight + mItemMargin) + getResources().getDimension(R.dimen.table_default_margin_top) - mItemMargin / 2 - mItemMargin / 2 + mItemMargin,
                    mItemWidth,
                    rowIndex * (mItemHeight + mItemMargin) + getResources().getDimension(R.dimen.table_default_margin_top) - mItemMargin / 2 + mItemMargin / 2 + mItemMargin,
                    mPaintItemIndicatorLine);
        }
    }

    public void updateTitles(ArrayList<String> data) {
        titles.clear();
        titles.addAll(data);
        updateView();
    }

    private void updateView() {
        invalidate();
        row = titles.size();
    }

    public void setItemHeight(int height) {
        mItemHeight = height;
        invalidate();
    }

    public void setItemWidth(int width) {
        mItemWidth = width;
        invalidate();
    }

}
