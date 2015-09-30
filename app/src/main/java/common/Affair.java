package common;

public class Affair {
    // 参考宦神的item
    private String title;
    private int no;// 标号
    private int location;// 地点
    private int kind;// 类别
    private int takes;// 耗时
    private int startDay;// 起始日期
    private int endDAy;// 结束日期
    private boolean meet;// 是否能够满足需求
    private int day;// 表示这件事情最终会在哪天做
    private int hour;// 表示这件事情最终从这天的几点开始做

    public Affair(int no, int location, int kind, int takes, int startDay, int endDay) {
        this.no = no;
        this.location = location;
        this.kind = kind;
        this.takes = takes;
        this.startDay = startDay;
        this.endDAy = endDay;
    }

    public Affair(String title, int day, int hour, int takes) {
        this.title = title;
        this.day = day;
        this.hour = hour;
        this.takes = takes;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public void setEndDAy(int endDAy) {
        this.endDAy = endDAy;
    }

    public Affair() {

    }

    public int getNo() {
        return this.no;
    }

    public int getLocation() {
        return this.location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getKind() {
        return this.kind;

    }

    public void setTakes(int takes) {
        this.takes = takes;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public int getStartDay() {
        return this.startDay;

    }

    public int getEndDay() {
        return this.endDAy;
    }

    public int getTakes() {
        return this.takes;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public boolean isMeet() {
        return this.meet;
    }

    public void setMeet(boolean meet) {
        this.meet = meet;
    }

    public Business toBusiness() {

        return new Business(this.title, new MyTime(this.hour, 0), new MyTime(this.hour + this.takes, 0), this.day + 1);

    }
}
