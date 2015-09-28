package com.eagzzycsl.smartable;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Random;


public class SmartActivity extends ActionBarActivity {
    private Toolbar smart_toolbar;
    private RecyclerView recyclerView;
    private AppCompatSpinner spinner_dayAmount;
    private RecyclerView recyclerView_added;
    private float destiny;
    private int magnet_spanCount;
    private int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart);
        //get width of screen and destiny;
        destiny = this.getResources().getDisplayMetrics().density;//dp到像素
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;

        myFindViewById();//findView
        mySetView();//给view设置侦听等
        myIni();//一些初始化操作


    }

    private void myFindViewById() {
        smart_toolbar = (Toolbar) findViewById(R.id.smart_toolbar);
//        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView_added = (RecyclerView) findViewById(R.id.recyclerView_added);
        spinner_dayAmount = (AppCompatSpinner) findViewById(R.id.spinner_dayAmount);
    }

    private void mySetView() {
        setSupportActionBar(smart_toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);//设置toolbar左侧的图标显示
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置将它显示为一个返回键
        class cc {
//            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(8, LinearLayoutManager.HORIZONTAL));
//            recyclerView.setItemAnimator(new DefaultItemAnimator());
//            recyclerView.setAdapter(new LabelAdapter(new ArrayList<String>() {
//
//
//                {
//                    this.add("alpha");
//                    this.add("ruby");
//                    this.add("javaScript");
//                    this.add("php");
//                    this.add("c#");
//                    this.add("java");
//                    this.add("go");
//                    this.add("css");
//                    this.add("html");
//                    this.add("php");
//                    this.add("c#");
//                    this.add("java");
//                    this.add("go");
//                    this.add("php");
//                    this.add("c#");
//                    this.add("java");
//                    this.add("go");
//                    this.add("css");
//                    this.add("html");
//                    this.add("php");
//                    this.add("c#");
//                    this.add("visual basic");
//                    this.add("swift");
//                    this.add("go");
//                    this.add("css");
//                    this.add("html");
//                    this.add("php");
//                    this.add("c#");
//                    this.add("java");
//                    this.add("go");
//                    this.add("php");
//                    this.add("c#");
//                    this.add("java");
//                }
//
//            }, SmartActivity.this));
        }
        spinner_dayAmount.setAdapter(ArrayAdapter.createFromResource(SmartActivity.this, R.array.array_dayAmount, android.R.layout.simple_spinner_item));
        magnet_spanCount = (int) ((screenWidth / destiny) / 90);
        //这个计算方法并不是很好，比如的当宽为720dp的时候，单个100dp，那么放8个其实也是可以的，不一定是放7个；
        recyclerView_added.setLayoutManager(new StaggeredGridLayoutManager(magnet_spanCount, StaggeredGridLayoutManager.VERTICAL));
        recyclerView_added.setItemAnimator(new DefaultItemAnimator());
        AddedAdapter addedAdapter = new AddedAdapter(new ArrayList<String>() {


            {
                this.add("c#");
                this.add("java");
                this.add("c#");
                this.add("java");
                this.add("c#");
                this.add("java");
                this.add("c#");
                this.add("java");
                this.add("c#");
                this.add("java");
                this.add("c#");
                this.add("java");
                this.add("c#");
                this.add("java");


            }
        }, SmartActivity.this);
        recyclerView_added.setAdapter(addedAdapter);
    }


    private void myIni() {

    }

}



// what is viewType?
class AddedAdapter extends RecyclerView.Adapter<AddedAdapter.ViewHolder> {
    private ArrayList<String> bs;
    private Context context;

    public AddedAdapter(ArrayList<String> bs, Context context) {
        this.bs = bs;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_added, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setText(bs.get(position));
    }

    @Override
    public int getItemCount() {
        return bs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (AppCompatTextView) itemView.findViewById(R.id.magnet_added_textView);
        }

        public void setText(String text) {
            this.textView.setText(text);
        }
    }

    public void addBs(String s) {
        bs.add(s);
        notifyItemInserted(0);
    }
}