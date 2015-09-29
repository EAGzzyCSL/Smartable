package com.eagzzycsl.smartable;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class SmartAddActivity extends AppCompatActivity {
    private RecyclerView recyclerView_smartAdd;
    private Toolbar toolbar_smartAdd;

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


        recyclerView_smartAdd.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        recyclerView_smartAdd.setItemAnimator(new DefaultItemAnimator());
        recyclerView_smartAdd.setAdapter(new SmartAddOneItemAdapter(new ArrayList<Integer>() {
            {
                this.add(0);

            }
        }, SmartAddActivity.this,recyclerView_smartAdd));

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


        }
        return super.onOptionsItemSelected(item);
    }


}
