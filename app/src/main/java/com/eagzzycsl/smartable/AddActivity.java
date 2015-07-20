package com.eagzzycsl.smartable;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.AppCompatEditText;

import java.util.Arrays;
import java.util.Calendar;
/**
 * Created by eagzzycsl on 7/20/15.
 */
public class AddActivity extends ActionBarActivity {
    //四个分别显示起始结束时间的TextView
    private AppCompatTextView textView_startDate;
    private AppCompatTextView textView_startTime;
    private AppCompatTextView textView_endDate;
    private AppCompatTextView textView_endTime;
    //切换是否全天的switch，不过是compat中的switch所以名字里带着compat，为的是5.0的样式
    private SwitchCompat switch_allDay;
    //设置提醒时间的TextView
    private AppCompatTextView textView_alert;
    //预置两个字符串，用于显示提醒，一个是全天的活动，一个是非全天的活动，到时候这个应该是从数据库中读取
    private String[] noAllDayAlert = new String[]{"无通知", "在活动开始时", "提前30分钟"};
    private String[] allDayAlert = new String[]{"无通知", "当天9:00", "提前一天（23:00）"};
    //一个变量，用到时再解释。
    private String[] alertItems = noAllDayAlert;

    private void iniDateAndTime() {
        //一个方法用来在界面创建的时候就把起始结束时间设定为当前时间
        //目前这儿缺少一个取整数的算法，即在11:30进行添加事件的操作时会把默认的事件事件设定为12:00-13:00
        textView_startDate.setText(MyPickerDialog.getDate());
        textView_endDate.setText(MyPickerDialog.getDate());
        textView_startTime.setText(MyPickerDialog.getTime());
        textView_endTime.setText(MyPickerDialog.getTime());
        //这个姑且放在这儿，不算是初始化时间的，是给提醒那一栏设置默认提醒项的
        textView_alert.setText(alertItems[2]);
        //alertItems见下

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        //把toolbar设置为actionbar，这样toolbar就和actionbar一样用了，起码目前我没有发现有大的影响或者差别
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolBar_main);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);//设置toolbar左侧的图标显示
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置将它显示为一个返回键
        getSupportActionBar().setHomeButtonEnabled(true);//设置它可以响应事件
        //获取view并设置侦听
        textView_startDate = (AppCompatTextView) findViewById(R.id.textView_startDate);
        textView_startTime = (AppCompatTextView) findViewById(R.id.textView_startTime);
        textView_endDate = (AppCompatTextView) findViewById(R.id.textView_endDate);
        textView_endTime = (AppCompatTextView) findViewById(R.id.textView_endTime);
        switch_allDay = (SwitchCompat) findViewById(R.id.switch_allDay);
        textView_alert = (AppCompatTextView) findViewById(R.id.textView_alert);

        textView_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //大意：显示一个dialog，
                //详解：dialog我只会这一个所以就只能说这个一个，显示一个AlertDialog的方法是先创建一个AlertDialog的builder，即下面的new，然后为这个builder设置内容，即下面的setSingleChoiceItems，表示单选项目，
                //最后调用show方法来显示这个dialog，或者先调用create方法创建一个dialog然后show，如果没有create方法而直接掉show方法的话show会自己调用create；
                //setSingleChoiceItems的参数，第一个是显示的项目，第二个是表示默认选中第几个，第三个是单击的事件
                //alertItems为要显示的内容项，造这么一个变量是为了不每次去选择填充全体那个数组还是段时间的那个数组，只要用这个一个变量就可以搞定了，在下面的switch事件中修改这个变量的值
                //Arrays.asList(alertItems).indexOf(textView_alert.getText().toString())表示先从alertItems转换一个List出来然后调用list的indexOf方法获取textView_alert的内容在数组中的下标
                //之所以这么做是因为数据没有indexOf方法而list有，这么做实现的是当点击修改一个提醒时默认选中的就是文本内容上显示的那个提醒
                (new AlertDialog.Builder(AddActivity.this)).setSingleChoiceItems(alertItems, Arrays.asList(alertItems).indexOf(textView_alert.getText().toString()), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //点击后把文本内容更改，witch表示点击的是哪个
                        textView_alert.setText(alertItems[which]);
                        //dismiss方法dialog
                        dialog.dismiss();
                    }
                }).show();
            }
        });
        switch_allDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //全天的switch在切换时执行显示隐藏起始结束时间的操作，同时为alertItems更改赋值
                if (isChecked) {
                    textView_startTime.setVisibility(View.INVISIBLE);
                    textView_endTime.setVisibility(View.INVISIBLE);
                    alertItems = allDayAlert;
                } else {
                    alertItems = noAllDayAlert;
                    textView_startTime.setVisibility(View.VISIBLE);
                    textView_endTime.setVisibility(View.VISIBLE);
                }
                //当事件被切换为全天或者段时间时它的默认提醒为第三个，不过下标为2，即提前30分钟或者提前一天
                textView_alert.setText(alertItems[2]);
            }
        });
        //那四个日期时间的文本点击后分别显示对应的选择时间日期的对话框
        //这么调是因为如果直接去产生一个picker的这块会多好几行代码，看着臃肿而且不便修改
        textView_startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyDatePickerDialog(AddActivity.this, textView_startDate).show();
            }
        });
        textView_endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyDatePickerDialog(AddActivity.this, textView_endDate).show();
            }
        });
        textView_startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyTimePickerDialog(AddActivity.this, textView_startTime).show();
            }
        });
        textView_endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyTimePickerDialog(AddActivity.this, textView_endTime).show();
            }
        });
        //初始化操作
        iniDateAndTime();


    }

    //有关菜单的操作
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate表示把布局转化为view，不是很懂
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_save) {


            return true;
        }
        if(id==android.R.id.home){
            //此处可能会涉及到安卓的activity的栈的东西
            finish();
            return true;
        }
        //此处加代码设置彩电上别的内容的点击事件

        return super.onOptionsItemSelected(item);
    }

}
class MyPickerDialog {

    //因为日期时间的选择有相似性而且雷同多所以先为他们写一个父类
    Context context;//字面意思是上下文，就是表示这个dialog算是哪个activity上的
    AppCompatTextView textView;//当选定时间后修改哪个文本的内容

    public MyPickerDialog(Context context, AppCompatTextView textView) {
        this.context = context;
        this.textView = textView;
    }


    public static String getDate(int year, int monthOfYear, int dayOfMonth) {
        //有参数的getDate方法用于在通过对话框选定了日期后拼接一个日期的字符串出来，同时加上星期
        Calendar calendar = Calendar.getInstance();
        //因为要获取星期，所以先给日历设定一个当前的日期以便于返回一个星期
        calendar.set(year, monthOfYear, dayOfMonth);
        String weekEtoC[] = new String[]{"", "日", "一", "二", "三", "四", "五", "六"};
        //因为返回的星期是数字而且周日为第一天所以用数组来实现转化
        return year + "年" + monthOfYear + "月" + dayOfMonth + "日星期" + weekEtoC[calendar.get(Calendar.DAY_OF_WEEK)];
    }

    public static String getDate() {
        //无参数的getDate方法用于返回当前日期的字符串
        Calendar calendar = Calendar.getInstance();
        return getDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    //getTime参考getDate
    public static String getTime(int hourOfDay, int minute) {
        //格式化字符串，和c语言相近
        //这一段关于日期的操作应该java类库中有对应的方法说不定可以直接用，这儿就姑且先造个轮子
        return hourOfDay + ":" + String.format("%02d", minute);
    }

    public static String getTime() {
        Calendar calendar = Calendar.getInstance();
        return getTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }
}

class MyDatePickerDialog extends MyPickerDialog {
    public MyDatePickerDialog(Context context, AppCompatTextView textView) {
        super(context, textView);
    }

    //show方法其实就是显示一个日期的选框
    public void show() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                textView.setText(MyPickerDialog.getDate(year, monthOfYear, dayOfMonth));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}

class MyTimePickerDialog extends MyPickerDialog {
    public MyTimePickerDialog(Context context, AppCompatTextView textView) {
        super(context, textView);
    }

    public void show() {
        Calendar calendar = Calendar.getInstance();
        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                textView.setText(MyPickerDialog.getTime(hourOfDay, minute));

            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
    }
}

