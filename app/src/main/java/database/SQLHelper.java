package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import common.TableFiled;
/**
 * Created by 宇 on 2015/7/16.
 */
public class SQLHelper extends SQLiteOpenHelper implements TableFiled {
    // 构造函数：在此建立数据库，仅第一次打开程序建立（系统自动判断）
    public SQLHelper(Context context) {
        super(context, "smart.db", null, 1);// smart.db 为数据库的“文件名”
    }

    // 建表的方法
    public void onCreate(SQLiteDatabase db) {
        // 添加页面 add_table
        db.execSQL("CREATE TABLE add_table("
                + "_id integer primary key autoincrement," + "title STRING,"
                + "detail STRING," + "isRemind boolean,"
                + "remind_early STRING," + "location STRING,"
                + "classify STRING," + "time_start STRING,"
                + "time_end STRING," + "date_start STRING," + "date_end STRING"
                +

                ");");

        // 日期与时间 time_table
        db.execSQL("CREATE TABLE IF NOT EXISTS time_table("
                + "_id integer primary key autoincrement," + "title STRING,"
                + "time_start STRING," + "time_end STRING,"
                + "date_start STRING," + "date_end STRING,"
                + "year_start integer," + "month_start integer,"
                + "day_start integer," + "hour_start integer,"
                + "minute_start integer," + "year_end integer,"
                + "month_end integer," + "day_end integer,"
                + "hour_end integer," + "minute_end integer" + ");");

        // 归档 filing_table
        db.execSQL("CREATE TABLE IF NOT EXISTS filing_table("
                + "_id integer primary key autoincrement," + "title STRING,"
                + "isComplete integer," + "isRoll booleam" + ");");
        //business
        db.execSQL("CREATE TABLE IF NOT EXISTS business("
                        + "_id integer primary key autoincrement,"
                        +TableFiled.TITLE+" string,"
                        +TableFiled.START_year   +" integer,"
                        +TableFiled.START_month  +" integer,"
                        +TableFiled.START_day    +" integer,"
                        +TableFiled.START_hour   +" integer,"
                        +TableFiled.START_minute +" integer,"
                        +TableFiled.END_year     +" integer,"
                        +TableFiled.END_month    +" integer,"
                        +TableFiled.END_day      +" integer,"
                        +TableFiled.END_hour     +" integer,"
                        +TableFiled.END_minute   +" integer"
                        + ");"
        );

    }

    // 必须有的函数 - 升级数据库的函数
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }
    // 数据库建表的时候要注意：
    // 1. 声明不要写错
    // 2. 每行末尾的","不要多了或者少了
}
