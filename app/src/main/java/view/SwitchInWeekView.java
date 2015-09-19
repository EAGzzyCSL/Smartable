package view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.eagzzycsl.smartable.R;

import java.util.Calendar;

import common.ClickAdjustScroll;


public class SwitchInWeekView extends FrameLayout {
    private TextView[] weekSwView_day = new TextView[8];
    private Calendar calendar = Calendar.getInstance();//一个日历，用来提供日期
    private int nowIndex = 1;

    class Listener_weekSwView implements OnTouchListener {
        private int vSelected;

        public Listener_weekSwView(int vSelected) {
            this.vSelected = vSelected;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    int tmp=vSelected-nowIndex;
                    clickAdjustScroll.adjustScroll(tmp);
                    calendar.add(Calendar.DAY_OF_MONTH,tmp);
                    setNowIndex(this.vSelected);
                    break;
            }
            return true;

        }
    }
    private ClickAdjustScroll clickAdjustScroll;
    public void setClickAdjustScroll(ClickAdjustScroll clickAdjustScroll){
        this.clickAdjustScroll=clickAdjustScroll;
    }

    public SwitchInWeekView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.switchinweekview, this);
        weekSwView_day[1] = (TextView) findViewById(R.id.weekSwView_day1);
        weekSwView_day[2] = (TextView) findViewById(R.id.weekSwView_day2);
        weekSwView_day[3] = (TextView) findViewById(R.id.weekSwView_day3);
        weekSwView_day[4] = (TextView) findViewById(R.id.weekSwView_day4);
        weekSwView_day[5] = (TextView) findViewById(R.id.weekSwView_day5);
        weekSwView_day[6] = (TextView) findViewById(R.id.weekSwView_day6);
        weekSwView_day[7] = (TextView) findViewById(R.id.weekSwView_day7);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            dayOfWeek = 7;
        } else {
            dayOfWeek = dayOfWeek - 1;
        }
        setNowIndex(dayOfWeek);
        setDayText();
        for (int i = 1; i <= 7; i++) {
            weekSwView_day[i].setOnTouchListener(new Listener_weekSwView(i));
        }
    }

    private void setNowIndex(int v) {
        weekSwView_day[nowIndex].setBackgroundResource(0);
        nowIndex = v;
        weekSwView_day[nowIndex].setBackgroundResource(R.drawable.circle);
        ((AppCompatActivity)getContext()).getSupportActionBar().setTitle(calendar.get(Calendar.MONTH) + "月");
    }

    public void changeSelect(int value) {
        calendar.add(Calendar.DAY_OF_MONTH, value);
        int tmp = nowIndex + value;
        if (tmp > 7) {
            tmp -= 7;
            setNowIndex(tmp);
            setDayText();
        } else if(tmp<1) {
            tmp+=7;
            setNowIndex(tmp);
            setDayText();
        }else{

            setNowIndex(tmp);
        }

    }

    private void setDayText() {
        calendar.add(Calendar.DAY_OF_MONTH, 1 - nowIndex);
        for (int i = 1; i <= 7; i++) {
            weekSwView_day[i].setText("" + calendar.get(Calendar.DAY_OF_MONTH));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        calendar.add(Calendar.DAY_OF_MONTH, nowIndex - 8);
    }
}
