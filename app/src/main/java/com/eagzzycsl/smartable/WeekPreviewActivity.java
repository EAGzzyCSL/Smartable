package com.eagzzycsl.smartable;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import common.Business;
import common.MyTime;
import view.ByWeekView;

/**
 * Created by eagzzycsl on 8/2/15.
 */
public class WeekPreviewActivity extends ActionBarActivity {
    private Toolbar weekPre_toolbar;
    private FloatingActionButton weekPre_fab_different;
    private ByWeekView byWeekView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekpreview);

        myFindViewById();//findView
        mySetView();//给view设置侦听等
        myIni();//一些初始化操作
        System.out.println(this.getLocalClassName() + "#######");
    }

    private void myFindViewById() {
        weekPre_toolbar = (Toolbar) findViewById(R.id.weekPre_toolbar);
        weekPre_fab_different = (FloatingActionButton) findViewById(R.id.weekPre_fab_different);
        byWeekView = (ByWeekView) this.findViewById(R.id.byWeekView);
    }

    private void mySetView() {
        setSupportActionBar(weekPre_toolbar);//用toolbar替代actionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);//设置toolbar左侧的图标显示
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置将它显示为一个返回键
        weekPre_fab_different.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WeekPreviewActivity.this, "new", Toast.LENGTH_SHORT).show();
                ArrayList<Business> bs = new ArrayList<>();
                bs.add(new Business("8:00-12:00", new MyTime(8, 0), new MyTime(12, 0), 1));
                bs.add(new Business("15:00-18:00", new MyTime(15, 0), new MyTime(18, 0), 2));
                bs.add(new Business("13:00-21:00", new MyTime(13, 0), new MyTime(21, 0), 3));
                bs.add(new Business("11:00-12:00", new MyTime(11, 0), new MyTime(12, 0), 4));
                bs.add(new Business("6:00-10:00", new MyTime(6, 0), new MyTime(10, 0), 5));
                bs.add(new Business("8:00-13:00", new MyTime(8, 0), new MyTime(13, 0), 6));
                bs.add(new Business("13:00-16:00", new MyTime(13, 0), new MyTime(16, 0), 7));

                byWeekView.updateBusiness(bs);
            }
        });
    }

    private void myIni() {
        ArrayList<Business> bs = new ArrayList<>();
        bs.add(new Business("1:00-10:00", new MyTime(1, 0), new MyTime(10, 0), 1));
        bs.add(new Business("8:00-12:00", new MyTime(8, 0), new MyTime(12, 0), 2));
        bs.add(new Business("6:00-8:00", new MyTime(6, 0), new MyTime(8, 0), 3));
        bs.add(new Business("18:00-22:00", new MyTime(18, 0), new MyTime(22, 0), 4));
        bs.add(new Business("13:00-17:00", new MyTime(13, 0), new MyTime(17, 0), 5));
        bs.add(new Business("15:00-19:00", new MyTime(15, 0), new MyTime(19, 0), 6));
        bs.add(new Business("20:00-24:00", new MyTime(20, 0), new MyTime(24, 0), 7));

        byWeekView.setBusiness(bs);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home: {
                // 此处可能会涉及到安卓的activity的栈的东西
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
