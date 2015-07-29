package com.eagzzycsl.smartable;

import android.content.ContentValues;

/**
 * Created by eagzzycsl on 7/20/15.
 */
public class Business implements TableFiled {
    private int id;
    private String title;
    private MyTime start;
    private MyTime end;

    //    private String annotation;
//    private boolean isRemind;
//    private int remind_early;
//    private int location_id;
//    private int location_name;
    public Business(int id, String title, MyTime start, MyTime end) {
        this(title, start, end);
        this.id = id;
    }

    public Business(String title, MyTime start, MyTime end) {
        this.title = title;
        this.start = start;
        this.end = end;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public MyTime getStart() {
        return this.start;

    }

    public MyTime getEnd() {
        return this.end;
    }

    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(TableFiled.TITLE, this.title);
        cv.put(TableFiled.START_year, this.start.getYear());
        cv.put(TableFiled.START_month, this.start.getMonth());
        cv.put(TableFiled.START_day, this.start.getDay());
        cv.put(TableFiled.START_hour, this.start.getHour());
        cv.put(TableFiled.START_minute, this.start.getMinute());
        cv.put(TableFiled.END_year, this.end.getYear());
        cv.put(TableFiled.END_month, this.end.getMonth());
        cv.put(TableFiled.END_day, this.end.getDay());
        cv.put(TableFiled.END_hour, this.end.getHour());
        cv.put(TableFiled.END_minute, this.end.getMinute());
        return cv;
    }
}
//
//class MyDate {
//    int year;
//    int month;
//    int day;
//
//    public MyDate(int year, int month, int day) {
//        this.year = year;
//        this.month = month;
//        this.day = day;
//    }
//
//    public int getYear() {
//        return this.year;
//    }
//
//    public int getMonth() {
//        return this.month;
//    }
//
//    public int getDay() {
//        return this.day;
//    }
//}


