package com.eagzzycsl.smartable;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import common.ClickAdjustScroll;
import common.ScrollAdjustClick;
import view.CompleteByDayView;
import view.SwitchInWeekView;

public class FragmentByDay extends Fragment implements ScrollAdjustClick, ClickAdjustScroll {
    private CompleteByDayView completeByDayView;
    private SwitchInWeekView switchInWeekView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_day, container, false);
        completeByDayView = (CompleteByDayView) v.findViewById(R.id.completeByDayView_byDay);
        completeByDayView.setScrollAdjustClick(this);
        switchInWeekView = (SwitchInWeekView) v.findViewById(R.id.switchInWeekView);
        switchInWeekView.setClickAdjustScroll(this);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        //似乎没有必要，看来是当时忘了删掉了，
        // 不过需要注意的是如果从别的fragment切回来的时候会回到当天，这个bug目前不太清楚怎么导致的
        //在onResume的时候更新界面
//        completeByDayView.updateSimpleByDayViews();
    }

    public void updateSimpleByDayViews() {

        completeByDayView.updateSimpleByDayViews();
    }

    @Override
    public void adjustClick(int value) {
        switchInWeekView.changeSelect(value);
    }

    @Override
    public void adjustScroll(int value) {
        completeByDayView.changeNowPager(value);
    }
}
