package com.eagzzycsl.smartable;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

import common.Business;
import common.MyTime;
import common.MyUtil;
import database.DatabaseManager;

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
    private AppCompatCheckBox checkbox_noAlert;
    private AppCompatCheckBox checkBox_wholeDay;
    private AppCompatEditText editText_title;
    private String opt;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_add);
        myFindViewById();//findView
        mySetView();//给view设置侦听等
        Bundle bundle = getIntent().getExtras();
        opt = bundle.getString("opt");

        switch (opt) {
            case "add": {
                //算法有问题，很容易出现25小时的问题
                //还有一个是小时的那个下标问题到底哪边处理的好？目前这边处理好了
                timeStart = new MyTime(bundle.getInt("year"),
                        bundle.getInt("month") + 1,
                        bundle.getInt("day"),
                        bundle.getInt("hour"),
                        0);
                timeEnd = new MyTime(bundle.getInt("year"),
                        bundle.getInt("month") + 1,
                        bundle.getInt("day"),
                        bundle.getInt("hour") + 1,
                        0);
                getSupportActionBar().setTitle("新建事项");
                break;
            }
            case "edit": {
                //需要修改
//                location_title = bundle.getString("location_title");
//                title = bundle.getString("item_value");
//                editText_location.setText(location_title);
                editText_title.setText(title);

                break;
            }
            case "edit_withId": {


                //需要修改
                int id = Integer.valueOf(bundle.getString("id"));
//                Business_id=id;
                DatabaseManager dm = DatabaseManager.getInstance(SingleAddActivity.this);

                Business bs = dm.getBusiness(id);
                timeStart = bs.getStart();
                timeEnd = bs.getEnd();
                editText_title.setText(bs.getTitle());

                dm.close();
                break;
            }
        }
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
        switch (id) {
            case R.id.action_settings: {

                return true;
            }
            case android.R.id.home: {
                // 此处可能会涉及到安卓的activity的栈的东西
                finish();
                return true;
            }
        }


        return super.onOptionsItemSelected(item);
    }

    private void myFindViewById() {
        toolbar_singleAdd = (Toolbar) findViewById(R.id.toolbar_singleAdd);
        editText_title = (AppCompatEditText) findViewById(R.id.main_editText_title);
        textView_startDate = (AppCompatTextView) findViewById(R.id.textView_startDate);
        textView_startTime = (AppCompatTextView) findViewById(R.id.textView_startTime);
        textView_endDate = (AppCompatTextView) findViewById(R.id.textView_endDate);
        textView_endTime = (AppCompatTextView) findViewById(R.id.textView_endTime);

        recyclerView_label = (RecyclerView) findViewById(R.id.recyclerView_label);
        recyclerView_pos = (RecyclerView) findViewById(R.id.recyclerView_pos);
        recyclerView_alert = (RecyclerView) findViewById(R.id.recyclerView_alert);
        checkBox_wholeDay = (AppCompatCheckBox) findViewById(R.id.checkBox_wholeDay);

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
        OptionWhenAddBusinessAdapter test=new OptionWhenAddBusinessAdapter(new ArrayList<String>() {
            {
                this.add("宿舍");
                this.add("教室");
                this.add("其他");
                this.add("a");
                this.add("b");
                this.add("c");
                this.add("d");
                this.add("e");
                this.add("f");
                this.add("g");
                this.add("+");
            }
        },SingleAddActivity.this, recyclerView_pos, OptionType.POS);
        test.setLayoutManager(recyclerView_pos.getLayoutManager());
        recyclerView_pos.setAdapter(test);

        recyclerView_alert.setAdapter(new OptionWhenAddBusinessAdapter(new ArrayList<String>() {
            {

                this.add("提前\n5min");
                this.add("提前\n30min");
                this.add("当日\n8:00");
                this.add("选择\n其他");

            }

        }, SingleAddActivity.this, recyclerView_alert, OptionType.ALERT));
        checkBox_wholeDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    textView_startTime.setVisibility(View.INVISIBLE);
                    textView_endTime.setVisibility(View.INVISIBLE);
                } else {
                    textView_startTime.setVisibility(View.VISIBLE);
                    textView_endTime.setVisibility(View.VISIBLE);
                }
            }
        });

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


