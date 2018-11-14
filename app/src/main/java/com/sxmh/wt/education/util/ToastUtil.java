package com.sxmh.wt.education.util;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Toast工具
 * Created by Wang Tao on 2018/4/12 0012.
 */

public class ToastUtil {

    private static ArrayList<Toast> toastList = new ArrayList<Toast>();

    /**
     * 显示一个新Toast
     *
     * @param context
     * @param content
     */
    public static void newToast(Context context, String content) {
        cancelAll();
        Toast toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        toastList.add(toast);
        toast.show();
    }

    /**
     * 取消之前的Toast
     */
    private static void cancelAll() {
        if (!toastList.isEmpty()) {
            for (Toast t : toastList) {
                t.cancel();
            }
            toastList.clear();
        }
    }
}
