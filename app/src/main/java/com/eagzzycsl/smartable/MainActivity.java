package com.eagzzycsl.smartable;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.widget.SearchView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    private DrawerLayout main_drawLayout;
    private Toolbar main_toolbar;
    private FloatingActionButton main_fab_add;
    private NavigationView main_navigationView_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_toolbar = (Toolbar) this.findViewById(R.id.main_toolbar);
        setSupportActionBar(main_toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);//设置toolbar左侧的图标显示
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置将它显示为一个返回键
        main_drawLayout = (DrawerLayout) this.findViewById(R.id.main_drawerLayout);
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
        mDrawerToggle.syncState();
        main_drawLayout.setDrawerListener(mDrawerToggle);
        main_fab_add = (FloatingActionButton) findViewById(R.id.main_fab_add);
        main_fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AddActivity.class);
                startActivity(intent);


            }
        });
        main_navigationView_nav = (NavigationView) findViewById(R.id.main_navigationView_nav);

        main_navigationView_nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            private MenuItem myPreviousMenuItem;

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (myPreviousMenuItem != null) {
                    myPreviousMenuItem.setChecked(false);
                }

                menuItem.setChecked(true);
                Toast.makeText(MainActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                myPreviousMenuItem = menuItem;
                main_drawLayout.closeDrawers();
                if (id == R.id.main_nav_day) {

                    getFragmentManager().beginTransaction().replace(R.id.main_glance_container, new FragmentByDay()).commit();

                }

                if (id == R.id.main_nav_kind) {
                    getFragmentManager().beginTransaction().replace(R.id.main_glance_container, new FragmentByKind()).commit();

                }
                return true;
            }

        });


        getFragmentManager().beginTransaction().add(R.id.main_glance_container, new FragmentByDay()).commit();

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
            return true;
        }



        return super.onOptionsItemSelected(item);
    }


}
