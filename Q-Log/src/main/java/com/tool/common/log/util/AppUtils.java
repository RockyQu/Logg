package com.tool.common.log.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * AppUtils
 */
public class AppUtils {

    /**
     * 获取当前版本号
     *
     * @param context Context
     * @return 版本号
     */
    public static String getVersionName(Context context) {
        String version = "";
        try {
            PackageInfo pkg = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pkg.versionName;
        } catch (NameNotFoundException e) {
            version = "未知版本";
            e.printStackTrace();
        }
        return version;
    }
}