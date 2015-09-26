package com.loopeer.android.librarys.scrolltable;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import java.util.ArrayList;

public class ScrollTableView extends LinearLayout implements CustomTableView.OnPositionClickListener {

    private IHorizontalScrollView scrollHeaderHorizontal;
    private IHorizontalScrollView scrollHorizontal;
    private LeftTitleView headerVertical;
    private TopTitleView headerHorizontal;
    private CustomTableView contentView;
    private static final String[] topTitles = new String[] {"场地一", "场地二", "场地三", "场地四", "场地五", "场地六", "场地七", "场地八", "场地九", "场地十", "场地十一"};
    private ArrayList<Position> selectPositions;

    public ScrollTableView(Context context) {
        this(context, null);
    }

    public ScrollTableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollTableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.view_container, this);
        setUpView();
        setUpData();
        selectPositions = new ArrayList<>();
    }

    private void setUpView() {
        scrollHeaderHorizontal = (IHorizontalScrollView) findViewById(R.id.scroll_header_horizontal);
        scrollHorizontal = (IHorizontalScrollView) findViewById(R.id.scroll_horizontal);
        headerVertical = (LeftTitleView) findViewById(R.id.header_vertical);
        headerHorizontal = (TopTitleView) findViewById(R.id.header_horizontal);
        contentView = (CustomTableView) findViewById(R.id.content_view);
        scrollHorizontal.setIScroller(scrollHeaderHorizontal);
        scrollHeaderHorizontal.setIScroller(scrollHorizontal);
    }


    private void setUpData() {
        ArrayList<String> leftTitles = createLeftTitle();
        ArrayList<String> topTitles = createTopTitles();
        headerVertical.updateTitles(leftTitles);
        headerHorizontal.updateTitles(topTitles);
        contentView.setRowAndColumn(leftTitles.size(), topTitles.size());
        contentView.setOnPositionChangeListener(this);
    }

    private ArrayList<String> createTopTitles() {
        ArrayList<String> results = new ArrayList<>();
        for (String string : topTitles) {
            results.add(string);
        }
        return results;
    }

    private ArrayList<String> createLeftTitle() {
        ArrayList<String> results = new ArrayList<>();
        for (int i = 9; i < 23; i++) {
            results.add(i + ":00");
        }
        return results;
    }


    @Override
    public void onPositionClick(Position position) {
        if (selectPositions.contains(position)) {
            selectPositions.remove(position);
        } else {
            selectPositions.add(position);
        }
        contentView.setSelectPositions(selectPositions);
    }
}
