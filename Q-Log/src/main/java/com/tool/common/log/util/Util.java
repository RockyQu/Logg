package com.tool.common.log.util;

import android.content.Context;
import android.content.pm.PackageManager;

import com.tool.common.log.common.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Util
 */
public class Util {

    public Util() {
        throw new AssertionError();
    }

    /**
     * 超大文本字符串转化为List
     *
     * @param message
     * @return
     */
    public static List<String> bigStringToList(String message) {
        List<String> stringList = new ArrayList<>();
        int index = 0;
        int maxLength = Constant.LINE_MAX;
        int countOfSub = message.length() / maxLength;
        if (countOfSub > 0) {
            for (int i = 0; i < countOfSub; i++) {
                String sub = message.substring(index, index + maxLength);
                stringList.add(sub);
                index += maxLength;
            }
            stringList.add(message.substring(index, message.length()));
        } else {
            stringList.add(message);
        }
        return stringList;
    }

    /**
     * 判断是否声明权限
     *
     * @param context
     * @param permission
     */
    public static void isPermission(Context context, String permission) {
        int status = context.checkCallingOrSelfPermission(permission);
        if (status == PackageManager.PERMISSION_DENIED) {
            throw new IllegalStateException("in order to use this, " +
                    "please add the permission " + permission + " to your AndroidManifest.xml");
        }
    }

    /**
     * 屏幕的宽
     *
     * @param context
     *            Context
     * @return ScreenWidth
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 屏幕的高
     *
     * @param context
     *            Context
     * @return ScreenHeight
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
