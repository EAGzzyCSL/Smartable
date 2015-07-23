package com.eagzzycsl.smartable;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by eagzzycsl on 7/20/15.
 */
public class FragmentByDay extends Fragment {
    private CompleteByDayView completeByDayView;
    private AppCompatButton button_test;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_day, container, false);
        completeByDayView = (CompleteByDayView) v.findViewById(R.id.calendarView_main);
//        button_test = (AppCompatButton) v.findViewById(R.id.Button_test);
//        completeByDayView.setBusiness(
//                new Business[]{new Business("1:00-2:00", new MyTime(1, 0), new MyTime(2, 0))
//                        , new Business("4:50:6:00", new MyTime(4, 50), new MyTime(6, 0))
//                        , new Business("21:53-23:38", new MyTime(21, 53), new MyTime(23, 38))
//                        , new Business("18:00-20:00", new MyTime(18, 0), new MyTime(20, 0))
//                }
//        );
        return v;
    }
}
