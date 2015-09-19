package com.eagzzycsl.smartable;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import common.Const;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout main_drawLayout;//侧滑动栏
    private Toolbar main_toolbar;
    private FloatingActionButton main_fab_add;//圆形浮动按钮
    private NavigationView main_navigationView_nav;//侧滑动栏中的导航菜单
    private static Boolean isQuit = false;
    private String nowFragment;
    private FragmentByDay fragmentByDay;
    private FragmentByKind fragmentByKind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myFindViewById();//findView
        mySetView();//给view设置侦听等
        myIni();//一些初始化操作
    }

    //findViewById
    private void myFindViewById() {
        main_toolbar = (Toolbar) this.findViewById(R.id.main_toolbar);
        main_drawLayout = (DrawerLayout) this.findViewById(R.id.main_drawerLayout);
        main_fab_add = (FloatingActionButton) findViewById(R.id.main_fab_add);
        main_navigationView_nav = (NavigationView) findViewById(R.id.main_navigationView_nav);
    }

    //setView
    private void mySetView() {
        //操作toolbar
        setSupportActionBar(main_toolbar);//用toolbar替代actionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);//设置toolbar左侧的图标显示
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置将它显示为一个返回键
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, main_drawLayout, main_toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();//让toggle和drawLayout的抽屉同步
        main_drawLayout.setDrawerListener(mDrawerToggle);
        //操作悬浮按钮
        main_fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("opt", "add");
                Calendar c=Calendar.getInstance();
                bundle.putInt("year", c.get(Calendar.YEAR));
                bundle.putInt("month",c.get(Calendar.MONTH));
                bundle.putInt("day",c.get(Calendar.DAY_OF_MONTH));
                bundle.putInt("hour",c.get(Calendar.HOUR_OF_DAY));
                bundle.putInt("minute",c.get(Calendar.MINUTE));
                intent.putExtras(bundle);
                startActivity(intent);
                //fab既要处理按天显示时的添加又要处理按分类显示时的添加，应该在设置一个变量记录fragment来分别处理
            }
        });
        //操作侧滑导航栏
        main_navigationView_nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            //默认选中第一项
            private MenuItem myPreviousMenuItem = main_navigationView_nav.getMenu().getItem(0);

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //选中一个时把当前的状态设置为选中，之前的取消选中
                int id = menuItem.getItemId();
                if (myPreviousMenuItem != null) {
                    myPreviousMenuItem.setChecked(false);
                }
                menuItem.setChecked(true);
                // Toast.makeText(MainActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                myPreviousMenuItem = menuItem;
                main_drawLayout.closeDrawers();
                switch (id) {
                    case R.id.main_nav_day:
                        getFragmentManager().beginTransaction().replace(R.id.main_glance_container, fragmentByDay).commit();
                        nowFragment="byDay";
                        break;
                    case R.id.main_nav_kind:
                        getFragmentManager().beginTransaction().replace(R.id.main_glance_container, fragmentByKind).commit();
                        nowFragment="byKind";
                        break;
                    case R.id.main_nav_setting:
                        getFragmentManager().beginTransaction().replace(R.id.main_glance_container, new FragmentSetting()).commit();
                        break;
                }
                return true;
            }
        });
    }

    //初始化
    private void myIni() {
//        getFragmentManager().beginTransaction().add(R.id.main_glance_container, new FragmentByDay()).commit();
        fragmentByDay=new FragmentByDay();
        fragmentByKind=new FragmentByKind();

        getFragmentManager().beginTransaction().replace(R.id.main_glance_container,fragmentByDay).commit();
        nowFragment="byDay";

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent intent = new Intent(MainActivity.this,SettingActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //连按两次推出
//    public boolean onKeyDown(int keyCode, KeyEvent event){
//        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
//        if(keyCode == KeyEvent.KEYCODE_BACK){
//            Boolean exit = settings.getBoolean(Const.DOUBLE_EXIT, true);
//            if(exit){
//                Timer timer = new Timer();
//                if(isQuit == false){
//                    isQuit = true;
//                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                    timer.schedule(new TimerTask() {
//                        @Override
//                        public void run() {
//                            isQuit = false;
//                        }
//                    }, 2000);
//                }
//                else finish();
//            } else finish();
//        }
//        return false;
//    }

    @Override
    protected void onStart() {
        super.onStart();
        switch (nowFragment){
            case "byDay":
                fragmentByDay.updateSimpleByDayViews();
//                getFragmentManager().beginTransaction().replace(R.id.main_glance_container,new FragmentByDay()).commit();
                break;
            case "byKind":
                fragmentByKind=new FragmentByKind();
                getFragmentManager().beginTransaction().replace(R.id.main_glance_container,fragmentByKind).commit();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}
