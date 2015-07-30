package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.eagzzycsl.smartable.Business;
import com.eagzzycsl.smartable.MyTime;
import com.eagzzycsl.smartable.TableFiled;

/**
 * Created by JZF on 2015/7/20.
 */
public class DatabaseManager implements TableFiled {

    private SQLHelper dbHelper;
    public static DatabaseManager instance = null;
    private SQLiteDatabase sqLiteDatabase;

    private DatabaseManager(Context context) {
        sqLiteDatabase = (new SQLHelper(context)).getWritableDatabase();//得到可写的数据库
    }

    /**
     * 获取本类对象实例
     */
    public static final DatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManager(context);
        }
        if (instance.sqLiteDatabase.isOpen() == false) {
            instance.sqLiteDatabase = instance.dbHelper.getWritableDatabase();
        }
        return instance;
    }

    /**
     * 插入数据
     *
     * @param table  表名
     * @param values 要插入的数据
     * @return result      返回新添加记录的行号，与主键ID无关
     */
    //By宇
    public Long insert_add_table(String table, ContentValues values) {
        long result = 0;
        if (sqLiteDatabase.isOpen())
            result = sqLiteDatabase.insert(table, null, values);
        sqLiteDatabase.close();
        return result;
    }

    /**
     * 查询数据
     *
     * @param table         表名
     * @param columns       查询列
     * @param selection     查询条件_1
     * @param selectionArgs 查询条件_2
     * @return 返回查询的游标，可对数据自行操作，需要自己关闭游标
     */
    public List<String> queryData(String table, String[] columns, String selection, String[] selectionArgs) {

        List<String> data = new ArrayList<String>(); // 数组 - 放数据

        if (sqLiteDatabase.isOpen()) {
            Cursor cursor = sqLiteDatabase.query(table, columns, selection, selectionArgs, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    data.add(cursor.getString(cursor.getColumnIndex("title")));
                } while (cursor.moveToNext());
            }
            cursor.close();
//            sqLiteDatabase.close();
            return data;
        }
        return null;
    }

    //查询所有
    public List<String> queryAll(String table) {

        List<String> data = new ArrayList<String>(); // 数组 - 放数据

        if (sqLiteDatabase.isOpen()) {
            Cursor cursor = sqLiteDatabase.query(table, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    data.add(cursor.getString(cursor.getColumnIndex("title")));
                } while (cursor.moveToNext());
            }
            cursor.close();
//            sqLiteDatabase.close();
            return data;
        }
        return null;
    }
    //TODO list是个长生命周期的对象则应该尽早让它 = null 查询所有这个函数，返回了list ，那么它被 null 了没，如果没，那咋办

    //针对分类界面写的一个类
    public class query_classify {

        //查询：遍历add_table， 并按照要求返回相应的 列 + id + title
        public ArrayList<ArrayList<String>> query_column(String table, String column) {
            ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
            if (sqLiteDatabase.isOpen()) {
                Cursor cursor = sqLiteDatabase.query(table, null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        ArrayList<String> a = new ArrayList<String>();
                        a.add(cursor.getString(cursor.getColumnIndex("_id")));
                        a.add(cursor.getString(cursor.getColumnIndex("title")));
                        a.add(cursor.getString(cursor.getColumnIndex(column)));//对不？？？
                        data.add(a);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                return data;
            }
            return null;
        }

        //查询多个东西。 以“地点为例”，要查出3列： 1. query有多少地点分类（空也算一种）。 2. query各个地点分类的名目。 3. 记录各个名目下有几个事项

        //TODO 如果表里一条数据也没有，要告诉用户，并提示他添加
        public ArrayList<Object> query_to_classify(String table, String classify) {
            ArrayList<Object> object = new ArrayList<>(10);//最后返回的大对象

            ArrayList<ArrayList<String>> data = new ArrayList<>(10);//扫指定表里的所有行，把有用的列存到data里
            data = query_column(table, classify);

            ArrayList<String> location_title = new ArrayList<>(10);//记录各个地点分类的名称
            ArrayList<ArrayList<String>> location_item = new ArrayList<>(10);//记录各个名称下的所有子项

            boolean[] flag = new boolean[200];//（在此函数中为辅助变量，最后是没用的）在下面二重循环筛选的过程中标记相同的      data.size() + 1

            if (data == null) {
                return null;//可以不？？？  如果表里一条数据也没有，要告诉用户，并提示他添加？？
            }

            for (int i = 0; i < data.size(); i++) {  //data.size() 返回的是“有多少条数组”
                //TODO 把数组大小“10”删了
                ArrayList<String> temp = new ArrayList<>();//收集各个名称下的所有子项，然后给location_item
                if (false == flag[i]) {

                    //TODO [already updated] （if 去掉了， 因为一旦进入上层 if ，就代表要加入一个新的“地点”了）
//                    if(data.get(i).size() != 0 && (false == flag[i])){ //true
                    location_title.add(data.get(i).get(2)); //"jiaoshi" //把地点名称加进来
                    temp.add(data.get(i).get(1));//把当前地点名称下的第一条数据（的title）加进来  location_item.get(num)
//                          temp.add(data.get(i).get(0));//再加上id 顺序乱啦 get(0) get(1)
                    flag[i] = true;
//                    }

                    //TODO [remember] 是否处理过某件事了：“标记”要做好啊！！
                    //找到具有相同“地点”的事项
                    for (int j = i + 1; j < data.size(); j++) {
                        //记录有多少地点分类
                        if (data.get(i).get(2).equals(data.get(j).get(2)) && (false == flag[j])) {
                            temp.add(data.get(j).get(1));
                            flag[j] = true;
                        }
                    }

                    //TODO [remember] it is error to use temp != null, because temp is always not null!
                    if (temp.size() != 0) {
                        location_item.add(temp);
//                    temp.clear();// 临时变量不能清空，此句一出，全盘都是bug
                    }
                }


            }

            object.add(location_title);
            object.add(location_item);

            return object;
        }

        //当查询地点时，提供4种查询条件
        //前端查询有多少地点时，返回int
        public int getLocaNum() {//Integer 还是int 比较好
            ArrayList<Object> object = new ArrayList<>(10);//取箱子
            object = query_to_classify("add_table", "location");//取箱子
            if (null == object) return -1;//TODO 不知道对不对

            ArrayList<String> location_title = new ArrayList<>(10);//记录各个地点分类的名目

            location_title = (ArrayList<String>) (object.get(0));
//        location_item = (ArrayList<ArrayList<String>>)(object.get(1));

            return location_title.size();
        }

        //前端查询所有已有地点的名称时，返回字符串数组集合
        public ArrayList<String> getLocaTitle() { //最后返回字符串集合（是一个对象）
            ArrayList<Object> object = new ArrayList<>(10);//取箱子
            object = query_to_classify("add_table", "location");//取箱子
            if (null == object) return null;//TODO 不知道对不对

            ArrayList<String> location_title = new ArrayList<>(10);//记录各个地点分类的名目
            location_title = (ArrayList<String>) object.get(0);
            return location_title;
        }

        //前端查询某地点名称下有多少地点，返回int
        public int getLocaNum(String location) {//Integer 还是int 比较好
            ArrayList<Object> object = new ArrayList<>(10);//取箱子
            object = query_to_classify("add_table", "location");//取箱子
            if (null == object) return -1;//TODO 不知道对不对

            ArrayList<String> location_title = new ArrayList<>(10);//记录各个地点分类的名目
            ArrayList<ArrayList<String>> location_item = new ArrayList<>(10);//记录各个名目下有几个子项

            location_title = (ArrayList<String>) object.get(0);
            location_item = (ArrayList<ArrayList<String>>) (object.get(1));

            int zuobiao = -1;
            for (int i = 0; i < location_title.size(); i++) {
                if (location.equals(location_title.get(i))) {
                    zuobiao = i;
                    break;
                }
            }
            if (-1 == zuobiao) {
                return -1;
            } else {
                return location_item.get(zuobiao).size();
            }
        }

        //前端查询某地点名称下的所有事项，返回字符串数组集合
        public ArrayList<String> getLocaName(String location) { //最后返回字符串集合（是一个对象）
            ArrayList<Object> object = new ArrayList<>(10);//取箱子
            object = query_to_classify("add_table", "location");//取箱子
            if (null == object) return null;//TODO 不知道对不对

            ArrayList<String> location_title = new ArrayList<>(10);//记录各个地点分类的名目
            ArrayList<ArrayList<String>> location_item = new ArrayList<>(10);//记录各个名目下有几个子项

            location_title = (ArrayList<String>) object.get(0);
            location_item = (ArrayList<ArrayList<String>>) (object.get(1));

            int zuobiao = -1;
            Log.i("TAG", "ceshi_a");

            for (int i = 0; i < location_title.size(); i++) {

                Log.i("TAG", "ceshi_b");
                if (location.equals(location_title.get(i))) {
                    Log.i("TAG", "ceshi_c");
                    zuobiao = i;
                    break;
                }
            }

            if (-1 == zuobiao) {
                Log.i("TAG", "ceshi_d");
                return null;
            } else {
                Log.i("TAG", "ceshi_e");
                Log.i("TAG", "zuobiao" + String.valueOf(zuobiao));

                return location_item.get(zuobiao);// zuobiao
            }
        }

        //当查询分类时，提供4种查询条件
        //前端查询有多少分类时，返回int
        public int getClassifyNum() {//Integer 还是int 比较好
            ArrayList<Object> object = new ArrayList<>(10);//取箱子
            object = query_to_classify("add_table", "location");//取箱子
            if (null == object) return -1;//TODO 不知道对不对

            ArrayList<String> location_title = new ArrayList<>(10);//记录各个地点分类的名目

            location_title = (ArrayList<String>) (object.get(0));
//        location_item = (ArrayList<ArrayList<String>>)(object.get(1));

            return location_title.size();
        }

        //前端查询所有已有地点的名称时，返回字符串数组集合
        public ArrayList<String> getLClassifyitle() { //最后返回字符串集合（是一个对象）
            ArrayList<Object> object = new ArrayList<>(10);//取箱子
            object = query_to_classify("add_table", "location");//取箱子
            if (null == object) return null;//TODO 不知道对不对

            ArrayList<String> location_title = new ArrayList<>(10);//记录各个地点分类的名目
            location_title = (ArrayList<String>) object.get(0);
            return location_title;
        }

        //前端查询某地点名称下有多少地点，返回int
        public int getClassifyNum(String location) {//Integer 还是int 比较好
            ArrayList<Object> object = new ArrayList<>(10);//取箱子
            object = query_to_classify("add_table", "location");//取箱子
            if (null == object) return -1;//TODO 不知道对不对

            ArrayList<String> location_title = new ArrayList<>(10);//记录各个地点分类的名目
            ArrayList<ArrayList<String>> location_item = new ArrayList<>(10);//记录各个名目下有几个子项

            location_title = (ArrayList<String>) object.get(0);
            location_item = (ArrayList<ArrayList<String>>) (object.get(1));

            int zuobiao = -1;
            for (int i = 0; i < location_title.size(); i++) {
                if (location.equals(location_title.get(i))) {
                    zuobiao = i;
                    break;
                }
            }
            if (-1 == zuobiao) {
                return -1;
            } else {
                return location_item.get(zuobiao).size();
            }
        }

        //前端查询某地点名称下的所有事项，返回字符串数组集合
        public ArrayList<String> getClassifyName(String location) { //最后返回字符串集合（是一个对象）
            ArrayList<Object> object = new ArrayList<>(10);//取箱子
            object = query_to_classify("add_table", "location");//取箱子
            if (null == object) return null;//TODO 不知道对不对

            ArrayList<String> location_title = new ArrayList<>(10);//记录各个地点分类的名目
            ArrayList<ArrayList<String>> location_item = new ArrayList<>(10);//记录各个名目下有几个子项

            location_title = (ArrayList<String>) object.get(0);
            location_item = (ArrayList<ArrayList<String>>) (object.get(1));

            int zuobiao = -1;
            for (int i = 0; i < location_title.size(); i++) {
                if (location.equals(location_title.get(i))) {
                    zuobiao = i;
                    break;
                }
            }
            if (-1 == zuobiao) {
                return null;
            } else {
                return location_item.get(0);// zuobiao
            }
        }
    }

    public void close() {
        if (sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
        if (dbHelper != null)
            dbHelper.close();
        if (instance != null)
            instance = null;
    }

    public int delete_thing(String table, String whereClause, String[] whereArgs) {
        int result = 0;
        if (sqLiteDatabase.isOpen()) {
            result = sqLiteDatabase.delete(table, whereClause, whereArgs);
        }
        return result;
    }
    //TODO Just to remember
    //TODO 找出所有的 "==" 改成 equals
    //TODO 没把握，不要写 .get.add， 用add最好
    //TODO 给二维数组 “赋大值”的时候，那个临时变量 要 new 啊！！！
    //TODO 是否处理过某件事了：“标记”要做好啊！！


    //todo 当地点为空时，并未合理归档为一种条件，当没有一件事情的时候，还没有设置提醒 和 界面跳转


    public void insertBusiness(ContentValues cv) {
        sqLiteDatabase.insert("business", null, cv);
    }

    public ArrayList<Business> getBusiness(int year, int month, int day) {
        ArrayList<Business> bs = new ArrayList<>();
        //s and e year ,mont,day
        Cursor c = sqLiteDatabase.rawQuery("select * from business where s_year=? and s_month=? and s_day=?", new String[]{
                String.valueOf(year), String.valueOf(month), String.valueOf(day)
        });
        while (c.moveToNext()) {
            bs.add(new Business(c.getInt(c.getColumnIndex("_id")),
                            c.getString(c.getColumnIndex(TableFiled.TITLE)),
                            new MyTime(c.getInt(c.getColumnIndex(TableFiled.START_year)),
                                    c.getInt(c.getColumnIndex(TableFiled.START_month)),
                                    c.getInt(c.getColumnIndex(TableFiled.START_day)),
                                    c.getInt(c.getColumnIndex(TableFiled.START_hour)),
                                    c.getInt(c.getColumnIndex(TableFiled.START_minute))),
                            new MyTime(c.getInt(c.getColumnIndex(TableFiled.END_year)),
                                    c.getInt(c.getColumnIndex(TableFiled.END_month)),
                                    c.getInt(c.getColumnIndex(TableFiled.END_day)),
                                    c.getInt(c.getColumnIndex(TableFiled.END_hour)),
                                    c.getInt(c.getColumnIndex(TableFiled.END_minute))))
            );
        }
        c.close();
        //TODO after instert and get ,close database;

        return bs;
    }

    public Business getBusiness(int id) {
        Business bs = null;
        //s and e year ,mont,day
        Cursor c = sqLiteDatabase.rawQuery("select * from business where _id=?", new String[]{
                String.valueOf(id)
        });
        if (c.moveToFirst()) {
            bs = new Business(c.getInt(c.getColumnIndex("_id")),
                    c.getString(c.getColumnIndex(TableFiled.TITLE)),
                    new MyTime(c.getInt(c.getColumnIndex(TableFiled.START_year)),
                            c.getInt(c.getColumnIndex(TableFiled.START_month)),
                            c.getInt(c.getColumnIndex(TableFiled.START_day)),
                            c.getInt(c.getColumnIndex(TableFiled.START_hour)),
                            c.getInt(c.getColumnIndex(TableFiled.START_minute))),
                    new MyTime(c.getInt(c.getColumnIndex(TableFiled.END_year)),
                            c.getInt(c.getColumnIndex(TableFiled.END_month)),
                            c.getInt(c.getColumnIndex(TableFiled.END_day)),
                            c.getInt(c.getColumnIndex(TableFiled.END_hour)),
                            c.getInt(c.getColumnIndex(TableFiled.END_minute))));
        }
        c.close();
        return bs;
    }
    public void deleteBusiness( int id){
        sqLiteDatabase.delete("business","_id=?",new String[]{String.valueOf(id)});

    }
}
