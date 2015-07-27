package com.eagzzycsl.smartable;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.DatabaseManager;
import view.FlowLayout;

public class FragmentByKind extends Fragment {
    //分类界面融合入框架 - 前期定义 By宇
    SimpleAdapter[] adapter = new SimpleAdapter[50];// 自定义适配器， 将【数组】data 里的东西 放进【容器】ListView

    private final ListView[] listContent = new ListView[50];// 容器填东西
    private final TextView[] textview = new TextView[50];// 各个小标题
    private TextView textview11;// 各个小标题
    private View[] child_classify = new View[30];
    private FlowLayout mFlowLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_kind, container, false);

        mFlowLayout = (FlowLayout) v.findViewById(R.id.id_flowlayout);

       /* LinearLayout.LayoutParams polish_classify = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );*/

        //分类界面融合入框架 - 主函数中 By宇
        DatabaseManager databaseManager = DatabaseManager.getInstance(getActivity());//连接DatabaseManager
        DatabaseManager.query_classify query_classify = databaseManager.new query_classify();//得到内部类对象

        //首先判断数据库中是否有数据，如果没有，就提醒用户去加！！
        //TODO 对于没有地点(分类)的事项，还没打印出来
        //TODO 还不能动态生成listContent
        if (-1 == query_classify.getLocaNum()) {
            Toast.makeText(getActivity(), "目前还没有任何事项，主人快点加点事情吧！", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < query_classify.getLocaNum(); i++) {

                LinearLayout.LayoutParams polish_classify = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );

                //TODO child_classify一定要开数组吗？
                child_classify[i] = View.inflate(getActivity(), R.layout.classify_list, null);
                mFlowLayout.addView(child_classify[i], polish_classify);

                listContent[i] = (ListView) child_classify[i].findViewById(R.id.listContent1);
                textview[i] = (TextView) child_classify[i].findViewById(R.id.classify_stitle1);

                //各个小标题
                //TODO 标题过长时，不能够全部显示出来
                textview[i].setText(query_classify.getLocaTitle().get(i));//

                //listContent 填数据
                adapter[i] = new SimpleAdapter(getActivity(), getData(i), R.layout.simpleadapter, new String[]{"btn_check_off_normal_holo_light", "title"},
                        new int[]{R.id.btn_check_off_normal_holo_light, R.id.title});//适配器装填数据 , 把填充的 data 数组里的值打印到 【自定义】 ListView 里
//                listContent1 = (ListView) v.findViewById(R.id.listContent1);
                listContent[i].setAdapter(adapter[i]);
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
}