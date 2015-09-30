package com.eagzzycsl.smartable;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import algorithm.SmartArrange;
import common.Affair;
import common.MyTime;

public class SmartAddActivity extends AppCompatActivity {
    private RecyclerView recyclerView_smartAdd;
    private Toolbar toolbar_smartAdd;
    private SmartAddOneItemAdapter smartAddOneItemAdapter;
    private LinearLayoutManager smartAddLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_add);
        myFindViewById();//findView


        mySetView();//给view设置侦听等

    }

    private void myFindViewById() {
        toolbar_smartAdd = (Toolbar) findViewById(R.id.toolbar_smartAdd);
        recyclerView_smartAdd = (RecyclerView) findViewById(R.id.recyclerView_smartAdd);
    }

    private void mySetView() {
        setSupportActionBar(toolbar_smartAdd);//用toolbar替代actionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);//设置toolbar左侧的图标显示
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置将它显示为一个返回键


        smartAddLinearLayoutManager = new LinearLayoutManager(SmartAddActivity.this);
//        smartAddLinearLayoutManager.setStackFromEnd(true);

        recyclerView_smartAdd.setLayoutManager(smartAddLinearLayoutManager);
        recyclerView_smartAdd.setItemAnimator(new DefaultItemAnimator());

        smartAddOneItemAdapter = new SmartAddOneItemAdapter(new ArrayList<Affair>() {
            {
                this.add(new Affair());

            }
        }, SmartAddActivity.this, recyclerView_smartAdd);
        recyclerView_smartAdd.setAdapter(smartAddOneItemAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_smart_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home: {
                finish();
                return true;
            }
            case R.id.action_settings: {

                return true;
            }
            case R.id.smartAdd_action_done: {
                ArrayList<Affair> aTmp = smartAddOneItemAdapter.getAffairs();
                for (int i = 0; i < aTmp.size(); i++) {
                    aTmp.get(i).setNo(i);
//                    System.out.println("title:" + aTmp.get(i).getTitle());
//                    System.out.println("startDAy:" + aTmp.get(i).getStartDay());
//                    System.out.println("endDay:" + aTmp.get(i).getEndDay());
//                    System.out.println("kind:" + aTmp.get(i).getKind());
//                    System.out.println("pos:" + aTmp.get(i).getLocation());
//                    System.out.println("takes:" + aTmp.get(i).getTakes());
                }
                aTmp.remove(aTmp.size() - 1);
                new SmartArrange(7, aTmp).doArrange();

                Intent intent = new Intent(SmartAddActivity.this, WeekPreviewActivity.class);
                ArrayList<String> titles = new ArrayList<String>();
                ArrayList<Integer> dayHourTake = new ArrayList<Integer>();

                for (int i = 0; i < aTmp.size(); i++) {
//                    System.out.println("~~~" + aTmp.get(i).isMeet());
                    titles.add(aTmp.get(i).getTitle());
                    dayHourTake.add(aTmp.get(i).getDay());
                    dayHourTake.add(aTmp.get(i).getHour());
                    dayHourTake.add(aTmp.get(i).getTakes());

                }

                intent.putStringArrayListExtra("titles", titles);
                intent.putIntegerArrayListExtra("dayHourTake", dayHourTake);
                startActivity(intent);
                finish();
                return true;
            }


        }
        return super.onOptionsItemSelected(item);
    }


}
