package com.eagzzycsl.smartable;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.MyUtil;
import database.DatabaseManager;
import view.FlowLayout;

public class FragmentByKind extends Fragment {
    //分类界面融合入框架 - 前期定义 By宇
    SimpleAdapter[] adapter = new SimpleAdapter[50];// 自定义适配器， 将【数组】data 里的东西 放进【容器】ListView

    final float width_suggest = 155;//每个块的建议宽度 单位dp
    final int margin_final = 5;
    private LinearLayout[] classif_box = new LinearLayout[50];
    ;
    private LinearLayout[] classify_Linear1 = new LinearLayout[50];
    private LinearLayout[] classify_Linear2 = new LinearLayout[50];
    private final ListView[] listContent = new ListView[50];// 容器填东西
    private final TextView[] textview = new TextView[50];// 各个小标题
    private View[] child_classify = new View[30];
    private FlowLayout mFlowLayout;
    private LinearLayout fragment_kind_box;
    private ImageButton[] add_button = new ImageButton[50];
    private int i;
    private float destiny;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        destiny = getActivity().getResources().getDisplayMetrics().density;//dp到像素
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_kind, container, false);

        /**
         * 先获取屏幕高度，然后再设置高度
         */
        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        float screenHeight = dm.heightPixels; // 屏幕宽（像素，如：480px）
        float height_dp = screenHeight / destiny;


        mFlowLayout = (FlowLayout) v.findViewById(R.id.id_flowlayout);
        fragment_kind_box = (LinearLayout) v.findViewById(R.id.fragment_kind_box);
        RelativeLayout.LayoutParams scroll = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, MyUtil.dpToPxInCode(destiny, (int) (height_dp - 10)));
        fragment_kind_box.setLayoutParams(scroll);


        //分类界面融合入框架 - 主函数中 By宇
        DatabaseManager databaseManager = DatabaseManager.getInstance(getActivity());//连接DatabaseManager
        DatabaseManager.query_classify query_classify = databaseManager.new query_classify();//得到内部类对象


        //TODO 对于没有地点(分类)的事项，还没打印出来
        //首先判断数据库中是否有数据，如果没有，就提醒用户去加！！
        if ((-1 == query_classify.getLocaNum()) || (0 == query_classify.getLocaNum())) {
            Toast.makeText(getActivity(), "亲爱的主人，你现在没有事项啦，要不要去加一点啦^0^", Toast.LENGTH_LONG).show();
        } else {
            for (i = 0; i < query_classify.getLocaNum(); i++) {

                /**
                 * 获取屏幕密度与宽度 计算自定义“假宽度”
                 */

                float screenWidth = dm.widthPixels; // 屏幕宽（像素，如：480px）
                float width_dp = screenWidth / destiny;//(nexus5 1080/ (480 / 160) ; 联想a320t 480 / ( 240 / 160))
                //获取密度end...

                //修饰每一大块的东西， 可以再为 polish_classify重设margin
                LinearLayout.LayoutParams polish_classify = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );
//                polish_classify.setMargins(150, 0, 50, 0);


                //各种找id
                child_classify[i] = View.inflate(getActivity(), R.layout.classify_list, null);

                listContent[i] = (ListView) child_classify[i].findViewById(R.id.listContent1);
                textview[i] = (TextView) child_classify[i].findViewById(R.id.classify_stitle1);
                classify_Linear1[i] = (LinearLayout) child_classify[i].findViewById(R.id.classify_Linear1);
                classify_Linear2[i] = (LinearLayout) child_classify[i].findViewById(R.id.classify_Linear2);
                //找id end...


                /**
                 * 以下修改每一个块的宽度，以适应不同分辨率
                 * 分三种情况，
                 * 第一种是4.0英寸以下的手机每行只显示一个块；
                 * 第二种是4.0英寸以上的手机（或平板）每行显示2个块或者更多。
                 * ps:以nexus5 竖屏的时候能够均匀显示2个块为参照，设定每个块的大小和margin
                 */

                /*
                *第1种情况：4.0英寸以下的手机每行只显示一个块
                * Linear1、2为动态更改两个Linear块的属性（相当于附加xml），classify_Linear1、2为容器
                */
                Log.i("TAG", "______" + "screenWidth:" + String.valueOf(screenWidth));
                Log.i("TAG", "______" + "width_dp:" + String.valueOf(width_dp));
                if (width_dp <= 330) {
                    LinearLayout.LayoutParams Linear1_1 = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, MyUtil.dpToPxInCode(destiny, 40));
                    classify_Linear1[i].setLayoutParams(Linear1_1);

                    LinearLayout.LayoutParams Linear2_1 = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, MyUtil.dpToPxInCode(destiny, 150));
                    classify_Linear2[i].setLayoutParams(Linear2_1);
                }
                //4.0英寸end...

                /*
                *第2种情况：4.0英寸以上
                * Linear1、2为动态更改两个Linear块的属性（相当于附加xml），classify_Linear1、2为容器
                */
                else if (width_dp > 330) {
                    /**
                     * 设定一个块（不加margin）的建议宽度width_suggest：155dp
                     * margin固定为7dp, 定义一个临时“块数” (float)boxNum_temp = (width_dp - 10 * 2 - 7 ) / (width_suggest + 7)
                     *算法：if(boxNum_temp小数部分 <= 0.8)  widthSet = (width_dp - 10 * 2 - 7 )/ boxNum_temp整数部分 - 7
                     *算法：if(boxNum_temp小数部分 >= 0.8)  widthSet = (width_dp - 10 * 2 - 7 )/ (boxNum_temp整数部分 + 1) - 7
                     */
                    float boxNum_temp = (width_dp - 5* 2 - margin_final) / (width_suggest + margin_final);
                    int boxNum_int = (int) boxNum_temp;
                    int width_Set;
                    if ((boxNum_temp - boxNum_int) <= 0.8) {
                        width_Set = (int) ((width_dp - 5 * 2 - margin_final) / boxNum_int - margin_final);
                    } else {
                        width_Set = (int) ((width_dp - 5 * 2 - margin_final) / (boxNum_int + 1) - margin_final);
                    }
                    Log.i("TAG", "______" + "width_Set:" + String.valueOf(width_Set));
                    LinearLayout.LayoutParams Linear1_2 = new LinearLayout.LayoutParams(
                            MyUtil.dpToPxInCode(destiny, width_Set), MyUtil.dpToPxInCode(destiny, 40));
                    classify_Linear1[i].setLayoutParams(Linear1_2);

                    LinearLayout.LayoutParams Linear2_2 = new LinearLayout.LayoutParams(
                            MyUtil.dpToPxInCode(destiny, width_Set), MyUtil.dpToPxInCode(destiny, 150));
                    classify_Linear2[i].setLayoutParams(Linear2_2);

                }
                // > 4.0英寸 end...
                //自适应设置end...

                mFlowLayout.addView(child_classify[i], polish_classify);

                /***
                 * 以上是对布局的处理
                 * 以下是对数据的处理
                 */

                //各个小标题
                //TODO 标题过长时，不能够全部显示出来
                textview[i].setText(query_classify.getLocaTitle().get(i));//

                //listContent 填数据
                adapter[i] = new SimpleAdapter(getActivity(), getData(i), R.layout.simpleadapter, new String[]{"btn_check_off_normal_holo_light", "title"},
                        new int[]{R.id.btn_check_off_normal_holo_light, R.id.title});//适配器装填数据 , 把填充的 data 数组里的值打印到 【自定义】 ListView 里
//                listContent1 = (ListView) v.findViewById(R.id.listContent1);
                listContent[i].setAdapter(adapter[i]);

                //add_button点击事件“功能绑定”
                add_button[i] = (ImageButton) child_classify[i].findViewById(R.id.ic_suggestions_add);
                add_button[i].setOnClickListener(listener_add_button);

                //add_button 点击事件“效果绑定”
                add_button[i].setOnTouchListener(listener_OnTouch);

                //ListView点击事件
                listContent[i].setOnItemClickListener(listener_ListView);
            }
        }
        databaseManager.close();//这个界面要多次访问数据库，因此不在DatabaseManager中关闭数据库

        return v;
    }


    //分类界面融合入框架 - 自定义函数  By宇
    private List<Map<String, Object>> getData(int roll) {
        //以“地点”作为测试
        DatabaseManager databaseManager = DatabaseManager.getInstance(getActivity());//连接DatabaseManager
        DatabaseManager.query_classify query_classify = databaseManager.new query_classify();//得到内部类对象

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (int j = 0; j < query_classify.getLocaNum((query_classify.getLocaTitle()).get(roll)); j++) {//0
            Map<String, Object> map = new HashMap<String, Object>();

            map.put("btn_check_off_normal_holo_light", R.drawable.btn_check_off_normal_holo_light);
            map.put("title", (query_classify.getLocaName(query_classify.getLocaTitle().get(roll))).get(j));

            list.add(map);
        }
        return list;
    }


    //点击事件处理，获取是哪个块里的事件,打包数据；并跳转到add界面，在add界面写好接收title
    private View.OnClickListener listener_add_button = new View.OnClickListener() {
        //TODO 研究：采用标记的方法记录每个“块”的位置
        private int index2;

        @Override
        public void onClick(View v) {

            DatabaseManager databaseManager = DatabaseManager.getInstance(getActivity());//连接DatabaseManager
            DatabaseManager.query_classify query_classify = databaseManager.new query_classify();//得到内部类对象

            Intent intent = new Intent(getActivity(), AddActivity.class);

            //打包数据
            try {
//                Bundle allBundle = new Bundle();
//                String FinalFlag = "FragmentByKind";
//                allBundle.putString("FinalFlag", FinalFlag);


                Bundle allBundle = new Bundle();
                String FinalFlag = "FragmentByKind";
//                allBundle.putString("FinalFlag", FinalFlag);
                allBundle.putString("opt", "add_withClass");
                Calendar c= Calendar.getInstance();
                allBundle.putInt("year", c.get(Calendar.YEAR));
                allBundle.putInt("month", c.get(Calendar.MONTH));
                allBundle.putInt("day",c.get(Calendar.DAY_OF_MONTH));
                allBundle.putInt("hour",c.get(Calendar.HOUR_OF_DAY));
                allBundle.putInt("minute", c.get(Calendar.MINUTE));
                //获取add_button所在“块”的坐标
                int index = ((ViewGroup) v.getParent().getParent().getParent().getParent().getParent()).indexOfChild((ViewGroup) v.getParent().getParent().getParent().getParent());
                allBundle.putString("location_title", query_classify.getLocaTitle().get(index));
                //TODO 日后这边会加好几个属性

                intent.putExtras(allBundle);

                startActivityForResult(intent, 1);
//                getActivity().startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    //给ImageButton 添加“按下”的效果
    private View.OnTouchListener listener_OnTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                //更改为按下时的背景颜色
                v.setBackgroundColor(Color.parseColor("#E6E6E6"));
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                //改为抬起时的背景颜色
                v.setBackgroundColor(Color.parseColor("#D5D5D5"));
            }
            return false;
        }
    };


    //ListView点击事件
    private AdapterView.OnItemClickListener listener_ListView = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            DatabaseManager databaseManager = DatabaseManager.getInstance(getActivity());//连接DatabaseManager
            DatabaseManager.query_classify query_classify = databaseManager.new query_classify();//得到内部类对象

            Intent intent = new Intent(getActivity(), AddActivity.class);

            //打包数据
            try {
//                Bundle allBundle = new Bundle();
//                String FinalFlag = "FragmentByKind_ListView";//与“所跳转页面”的暗号
//                allBundle.putString("FinalFlag", FinalFlag);

                Bundle allBundle = new Bundle();
                String FinalFlag = "FragmentByKind_ListView";//与“所跳转页面”的暗号
//                allBundle.putString("FinalFlag", FinalFlag);
                allBundle.putString("opt", "edit");
                //获取listView所在“块”的坐标
                int index = ((ViewGroup) parent.getParent().getParent().getParent().getParent()).indexOfChild((ViewGroup) parent.getParent().getParent().getParent());
//                ((ViewGroup) view.getParent().getParent().getParent()).indexOfChild((ViewGroup) view.getParent().getParent());
                allBundle.putString("location_title", query_classify.getLocaTitle().get(index));

                //获取listView里TextView的值
                HashMap<String, String> item_value_parent = (HashMap<String, String>) parent.getItemAtPosition(position);
                String item_value = item_value_parent.get("title");
                allBundle.putString("item_value", item_value);
                Log.i("TAG", "_____" + item_value);
                //TODO 日后这边会加好几个属性

                intent.putExtras(allBundle);
                startActivityForResult(intent, 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

}

//TODO child_classify一定要开数组吗？