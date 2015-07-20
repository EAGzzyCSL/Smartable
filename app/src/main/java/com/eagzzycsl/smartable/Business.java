package com.eagzzycsl.smartable;

/**
 * Created by eagzzycsl on 7/20/15.
 */
public class Business {

    private String name;
    private MyTime start;
    private MyTime end;

    public Business(String name, MyTime start, MyTime end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return this.name;
    }

    public MyTime getStart() {
        return this.start;

    }

    public MyTime getEnd() {
        return this.end;
    }
}

class MyDate {
    int year;
    int month;
    int day;

    public MyDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
}

class MyTime {
    private int hour;
    private int minute;

    public MyTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return this.hour;

    }

    public int getMinute() {
        return this.minute;
    }

    public int toMinutes() {
        return this.getHour() * 60 + this.getMinute();
    }

    public int compareTo(MyTime t) {
        return this.toMinutes() - t.toMinutes();
    }

}

