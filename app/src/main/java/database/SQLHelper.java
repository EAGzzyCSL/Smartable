package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 宇 on 2015/7/16.
 */
public class SQLHelper extends SQLiteOpenHelper {
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
                + "time_year_start integer," + "time_month_start integer,"
                + "time_day_start integer," + "time_hour_start integer,"
                + "time_minute_start integer," + "time_year_end integer,"
                + "time_month_end integer," + "time_day_end integer,"
                + "time_hour_end integer," + "time_minute_end integer" + ");");

        // 归档 filing_table
        db.execSQL("CREATE TABLE IF NOT EXISTS filing_table("
                + "_id integer primary key autoincrement," + "title STRING,"
                + "isComplete integer," + "isRoll booleam" + ");");
    }

    // 必须有的函数 - 升级数据库的函数
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }
    // 数据库建表的时候要注意：
    // 1. 声明不要写错
    // 2. 每行末尾的","不要多了或者少了
}
