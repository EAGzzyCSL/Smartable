package com.eagzzycsl.smartable;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentByDay extends Fragment {
    private CompleteByDayView completeByDayView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_day, container, false);
        completeByDayView = (CompleteByDayView) v.findViewById(R.id.calendarView_main);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        //在onResume的时候更新界面
        completeByDayView.updateSimpleByDayViews();
    }
    public void updateSimpleByDayViews(){
        completeByDayView.updateSimpleByDayViews();
    }
}
