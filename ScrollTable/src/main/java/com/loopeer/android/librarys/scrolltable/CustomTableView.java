package com.loopeer.android.librarys.scrolltable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;

public class CustomTableView extends View {
    private static final String TAG = "CustomTableView";

    private Paint mPaintTextNormal;
    private Paint mPaintItemBg;

    private int mTextUnableColor;
    private int mTextNormalColor;
    private int mTextSelectColor;
    private int mItemBgNormalColor;
    private int mItemBgSelectColor;
    private float mTextNormal;

    private int mItemHeight;
    private int mItemWidth;
    private int mItemMargin;
    private ArrayList<ArrayList<String>> datas;

    private int row = 20;
    private int column = 10;

    private ArrayList<Position> selectPositions;
    private OnPositionClickListener mPositionChangeListener;

    public CustomTableView(Context context) {
        this(context, null);
    }

    public CustomTableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        
        init(context, attrs, defStyleAttr);
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.table_divider_color));
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        initData();
        initPaint();
        datas = new ArrayList<>();
        selectPositions = new ArrayList<>();
    }

    private void initData() {
        mItemHeight =  getResources().getDimensionPixelSize(R.dimen.table_item_height);
        mItemWidth = getResources().getDimensionPixelSize(R.dimen.table_item_width);
        mItemMargin = getResources().getDimensionPixelSize(R.dimen.table_item_margin);
    }

    private void initPaint() {
        mTextNormalColor = ContextCompat.getColor(getContext(), R.color.table_text_normal_color);
        mTextUnableColor =  ContextCompat.getColor(getContext(), R.color.table_text_unable_color);
        mTextSelectColor =  ContextCompat.getColor(getContext(), R.color.table_text_select_color);
        mItemBgNormalColor = ContextCompat.getColor(getContext(), R.color.table_item_bg_normal_color);
        mItemBgSelectColor = ContextCompat.getColor(getContext(), R.color.table_item_bg_select_color);
        mTextNormal = getResources().getDimension(R.dimen.table_default_text_size);

        mPaintTextNormal = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTextNormal.setColor(mTextNormalColor);
        mPaintTextNormal.setTextSize(mTextNormal);

        mPaintItemBg = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintItemBg.setColor(mItemBgNormalColor);
        mPaintItemBg.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(column * mItemWidth + (column - 1) * mItemMargin, row * mItemHeight + row * mItemMargin);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawItem(canvas);

    }

    private void drawItem(Canvas canvas) {
        for (int rowIndex = 0; rowIndex < row; rowIndex++) {
            for (int columnIndex = 0; columnIndex < column; columnIndex++) {
                adJustSelectPaintColor(columnIndex, rowIndex);

                float left =  mItemWidth * columnIndex + mItemMargin * columnIndex;
                float right = left + mItemWidth;
                float top = mItemHeight * rowIndex + mItemMargin * (rowIndex + 1);
                float bottom = top + mItemHeight;
                canvas.drawRect(left, top, right, bottom, mPaintItemBg);

                String content = getShowData(rowIndex, columnIndex);
                Paint.FontMetrics fontMetrics = mPaintTextNormal.getFontMetrics();
                float fontHeight = fontMetrics.bottom - fontMetrics.top;
                float textWidth = mPaintTextNormal.measureText(content);
                float y = top + mItemHeight - (mItemHeight - fontHeight) / 2 - fontMetrics.bottom;
                float x = left + mItemWidth / 2 - textWidth / 2;

                canvas.drawText(content, x, y, mPaintTextNormal);
            }
        }
    }

    private String getShowData(int rowIndex, int columnIndex) {
        if (datas.size() > rowIndex) {
            return datas.get(rowIndex).size() > columnIndex ? datas.get(rowIndex).get(columnIndex) : "";
        } else {
            return "";
        }
    }

    private void adJustSelectPaintColor(int columnIndex, int rowIndex) {
        if (selectPositions != null && selectPositions.contains(new Position(columnIndex, rowIndex))) {
            mPaintTextNormal.setColor(mTextSelectColor);
            mPaintItemBg.setColor(mItemBgSelectColor);
        } else {
            mPaintTextNormal.setColor(mTextNormalColor);
            mPaintItemBg.setColor(mItemBgNormalColor);
        }
    }

    public void setRowAndColumn(int row, int column) {
        this.row = row;
        this.column = column;
        invalidate();
    }

    public void setDatas(ArrayList<ArrayList<String>> data) {
        datas.clear();
        for (ArrayList<String> rowArrayString : data) {
            datas.add(rowArrayString);
        }
        invalidate();
    }

    public void setOnPositionChangeListener(OnPositionClickListener listener) {
        mPositionChangeListener = listener;
    }

    public void setSelectPositions(ArrayList<Position> positions) {
        selectPositions.clear();
        selectPositions.addAll(positions);
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent event) {
        Position position = getPositionFromLocation(event.getX(), event.getY());
        if (position == null) {
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_UP:
                if (mPositionChangeListener != null) {
                    mPositionChangeListener.onPositionClick(position);
                }
                break;
        }
        return true;
    }

    public Position getPositionFromLocation(float x, float y) {

        if (x > getWidth()) {
            return null;
        }

        if (y > getHeight()) {
            return null;
        }

        int yPosition = (int) y / (mItemHeight + mItemMargin);

        int xPosition = (int) (x / (mItemWidth + mItemMargin));

        return new Position(xPosition, yPosition);
    }

    public interface OnPositionClickListener {
        void onPositionClick(Position position);
    }

    public void setItemHeight(int height) {
        mItemHeight = height;
        invalidate();
    }

    public void setItemWidth(int width) {
        mItemWidth = width;
        invalidate();
    }

    public void setItemMargin(int margin) {
        mItemMargin = margin;
        invalidate();
    }

    public void setColors(int... colors) {
        if (colors.length > 0) mTextNormalColor = colors[0];
        if (colors.length > 1) mTextSelectColor = colors[1];
        if (colors.length > 2) mTextUnableColor = colors[2];
        invalidate();
    }

    public void setTextNormalColor(int color) {
        mTextNormalColor = color;
        invalidate();
    }

    public void setTextSelectColor(int color) {
        mTextSelectColor = color;
        invalidate();
    }

    public void setTextUnableColor(int color) {
        mTextUnableColor = color;
        invalidate();
    }

    public void setUpAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        if (attrs == null) return;
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScrollTableView, defStyleAttr, 0);
        if (a == null) return;

        setItemHeight(a.getDimensionPixelSize(R.styleable.ScrollTableView_itemHeight, mItemHeight));
        setItemWidth(a.getDimensionPixelSize(R.styleable.ScrollTableView_itemWidth, mItemWidth));
        setItemMargin(a.getDimensionPixelSize(R.styleable.ScrollTableView_dataMargin, mItemMargin));
        setTextNormalColor(a.getColor(R.styleable.ScrollTableView_textNormalColor, mTextNormalColor));
        setTextSelectColor(a.getColor(R.styleable.ScrollTableView_textSelectColor, mTextSelectColor));
        setTextUnableColor(a.getColor(R.styleable.ScrollTableView_textUnableColor, mTextUnableColor));

    }
}
