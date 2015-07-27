package com.eagzzycsl.smartable;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.DatabaseManager;

public class FragmentByKind extends Fragment {
    // 分类界面融合入框架 - 前期定义 By宇
    SimpleAdapter adapter;// 自定义适配器， 将 【数组】data 里的东西 放进【容器】ListView

    private final ListView[] listContent = new ListView[10];// 容器填东西
    private final TextView[] textViews = new TextView[10];// 各个小标题

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_kind, container, false);

        // 分类界面融合入框架 - 主函数中 By宇
        DatabaseManager databaseManager = DatabaseManager
                .getInstance(getActivity());// 连接DatabaseManager
        DatabaseManager.query_classify query_classify = databaseManager.new query_classify();// 得到内部类对象

        // 首先判断数据库中是否有数据，如果没有，就提醒用户去加！！
        // TODO 对于没有地点(分类)的事项，还没打印出来
        // TODO 还不能动态生成listContent
        if (-1 == query_classify.getLocaNum()) {
            Toast.makeText(getActivity(), "目前还没有任何事项，主人快点加点事情吧！",
                    Toast.LENGTH_SHORT).show();
        } else {

            listContent[0] = (ListView) v.findViewById(R.id.listContent1);
            listContent[1] = (ListView) v.findViewById(R.id.listContent2);
            listContent[2] = (ListView) v.findViewById(R.id.listContent3);
            listContent[3] = (ListView) v.findViewById(R.id.listContent4);
            textViews[0] = (TextView) v.findViewById(R.id.classify_stitle1);
            textViews[1] = (TextView) v.findViewById(R.id.classify_stitle2);
            textViews[2] = (TextView) v.findViewById(R.id.classify_stitle3);
            textViews[3] = (TextView) v.findViewById(R.id.classify_stitle4);

            for (int i = 0; i < query_classify.getLocaNum(); i++) {

                // 各个小标题
                textViews[i].setText(query_classify.getLocaTitle().get(i));

                // listContent 填数据
                adapter = new SimpleAdapter(getActivity(), getData(i),
                        R.layout.simpleadapter, new String[]{
                        "btn_check_off_normal_holo_light", "title"},
                        new int[]{R.id.btn_check_off_normal_holo_light,
                                R.id.title});// 适配器装填数据 , 把填充的 data 数组里的值打印到
                // 【自定义】 ListView 里
                // listContent1 = (ListView) v.findViewById(R.id.listContent1);
                listContent[i].setAdapter(adapter);
            }
        }
        databaseManager.close();// 这个界面要多次访问数据库，因此不在DatabaseManager中关闭数据库
        return v;
    }

    // 分类界面融合入框架 - 自定义函数 By宇
    private List<Map<String, Object>> getData(int roll) {
        // 以“地点”作为测试
        DatabaseManager databaseManager = DatabaseManager
                .getInstance(getActivity());// 连接DatabaseManager
        DatabaseManager.query_classify query_classify = databaseManager.new query_classify();// 得到内部类对象

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int j = 0; j < query_classify.getLocaNum((query_classify
                .getLocaTitle()).get(roll)); j++) {
            Map<String, Object> map = new HashMap<String, Object>();

            map.put("btn_check_off_normal_holo_light",
                    R.drawable.btn_check_off_normal_holo_light);
            map.put("title", (query_classify.getLocaName(query_classify
                    .getLocaTitle().get(roll))).get(j));

            list.add(map);
        }
        return list;
    }
}
