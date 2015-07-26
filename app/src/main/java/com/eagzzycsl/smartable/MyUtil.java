package com.eagzzycsl.smartable;

import android.util.DisplayMetrics;

/**
 * Created by eagzzycsl on 7/23/15.
 */
public class MyUtil {
    private static String[] weekEtoCString = new String[]{"", "日", "一", "二", "三", "四", "五", "六"};

    public static String weekEtoC(int e) {
        return "周" + weekEtoCString[e];
    }

    public static int dpToPxInCode(float density, int dp) {
        return (int) (density * dp + 0.5);
    }
}
