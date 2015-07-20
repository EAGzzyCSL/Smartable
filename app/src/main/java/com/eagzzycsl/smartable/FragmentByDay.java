package com.eagzzycsl.smartable;

import android.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by eagzzycsl on 7/20/15.
 */
public class FragmentByDay extends Fragment {
    private CalendarView calendarView;
    private AppCompatButton button_test;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_day, container, false);
        calendarView = (CalendarView) v.findViewById(R.id.calendarView_main);
        button_test = (AppCompatButton) v.findViewById(R.id.Button_test);
        calendarView.setBusiness(
                new Business[]{new Business("1:00-2:00", new MyTime(1, 0), new MyTime(2, 0))
                        , new Business("4:50:6:00", new MyTime(4, 50), new MyTime(6, 0))
                        , new Business("21:53-23:38", new MyTime(21, 53), new MyTime(23, 38))
                        , new Business("18:00-20:00", new MyTime(18, 0), new MyTime(20, 0))
                }
        );
        button_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.setBusiness(
                        new Business[]{new Business("5:43-6:52", new MyTime(5, 43), new MyTime(6, 52))
                                , new Business("0:20-0.40", new MyTime(0, 20), new MyTime(0, 40))
                                , new Business("3:05-4:18", new MyTime(3, 5), new MyTime(4, 18))
                                , new Business("6:20-18:40", new MyTime(6, 20), new MyTime(18, 40))
                        }
                );
            }
        });
        return v;
    }
}
