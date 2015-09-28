package com.eagzzycsl.smartable;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

import common.MyTime;
import common.MyUtil;

public class SingleAddActivity extends AppCompatActivity implements OptionType {
    private Toolbar toolbar_singleAdd;
    private AppCompatTextView textView_startDate;
    private AppCompatTextView textView_startTime;
    private AppCompatTextView textView_endDate;
    private AppCompatTextView textView_endTime;
    private MyTime timeStart = new MyTime();
    private MyTime timeEnd = new MyTime();
    private RecyclerView recyclerView_label;
    private RecyclerView recyclerView_pos;
    private RecyclerView recyclerView_alert;
    private OptionWhenAddBusinessAdapter labelAdapter;
    private OptionWhenAddBusinessAdapter posAdapter;
    private OptionWhenAddBusinessAdapter alertAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_add);
        myFindViewById();//findView
        mySetView();//给view设置侦听等
        myIni();//一些初始化操作
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_single_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void myFindViewById() {
        toolbar_singleAdd = (Toolbar) findViewById(R.id.toolbar_singleAdd);
        textView_startDate = (AppCompatTextView) findViewById(R.id.textView_startDate);
        textView_startTime = (AppCompatTextView) findViewById(R.id.textView_startTime);
        textView_endDate = (AppCompatTextView) findViewById(R.id.textView_endDate);
        textView_endTime = (AppCompatTextView) findViewById(R.id.textView_endTime);

        recyclerView_label = (RecyclerView) findViewById(R.id.recyclerView_label);
        recyclerView_pos = (RecyclerView) findViewById(R.id.recyclerView_pos);
        recyclerView_alert = (RecyclerView) findViewById(R.id.recyclerView_alert);

    }

    private void mySetView() {
        setSupportActionBar(toolbar_singleAdd);// 把toolbar设置为actionbar，这样toolbar就和actionbar一样用了，起码目前我没有发现有大的影响或者差别
        getSupportActionBar().setDisplayShowHomeEnabled(true);// 设置toolbar左侧的图标显示
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);// 设置将它显示为一个返回键
        getSupportActionBar().setHomeButtonEnabled(true);// 设置它可以响应事件

        textView_startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyDatePickerDialog(SingleAddActivity.this, textView_startDate, timeStart)
                        .show();
            }
        });
        textView_endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyDatePickerDialog(SingleAddActivity.this, textView_endDate, timeEnd)
                        .show();
            }
        });
        textView_startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyTimePickerDialog(SingleAddActivity.this, textView_startTime, timeStart)
                        .show();
            }
        });
        textView_endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyTimePickerDialog(SingleAddActivity.this, textView_endTime, timeEnd)
                        .show();
            }
        });
        recyclerView_label.setLayoutManager(new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL));
        recyclerView_label.setItemAnimator(new DefaultItemAnimator());
        recyclerView_pos.setLayoutManager(new StaggeredGridLayoutManager(4, LinearLayoutManager.VERTICAL));
        recyclerView_pos.setItemAnimator(new DefaultItemAnimator());
        recyclerView_alert.setLayoutManager(new StaggeredGridLayoutManager(4, LinearLayoutManager.VERTICAL));
        recyclerView_alert.setItemAnimator(new DefaultItemAnimator());
        recyclerView_label.setAdapter(new OptionWhenAddBusinessAdapter(new ArrayList<String>() {
            {
                this.add("作业");
                this.add("与人为乐");
                this.add("与己为乐");
            }
        }, SingleAddActivity.this, recyclerView_label, OptionType.LABEL));
        recyclerView_pos.setAdapter(new OptionWhenAddBusinessAdapter(new ArrayList<String>() {
            {
                this.add("宿舍");
                this.add("教室");
                this.add("其他");
                this.add("+");
            }
        }, SingleAddActivity.this, recyclerView_pos, OptionType.POS));
        recyclerView_alert.setAdapter(new OptionWhenAddBusinessAdapter(new ArrayList<String>() {
            {

                this.add("提前\n5min");
                this.add("提前\n30min");
                this.add("当日\n8:00");
                this.add("选择\n其他");

            }

        }, SingleAddActivity.this, recyclerView_alert, OptionType.ALERT));

    }

    private void myIni() {
        textView_startDate.setText(MyPickerDialog.getDate(timeStart.getYear(),
                timeStart.getMonth(), timeStart.getDay()));
        textView_endDate.setText(MyPickerDialog.getDate(timeEnd.getYear(),
                timeEnd.getMonth(), timeEnd.getDay()));
        textView_startTime.setText(MyPickerDialog.getMoment(timeStart.getHour(), timeStart.getMinute()));
        textView_endTime.setText(MyPickerDialog.getMoment(timeEnd.getHour(), timeEnd.getMinute()));
    }
}


