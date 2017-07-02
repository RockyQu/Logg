package com.tool.common.log.util;

import java.io.File;

/**
 * FileUtil
 */
public class FileUtil {

    public FileUtil() {
        throw new AssertionError();
    }

    /**
     * SD卡是否存在检查
     *
     * @return SD卡是否存在
     */
    public static boolean existSDCard() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 创建文件夹
     *
     * @param path 文件夹绝对路径
     * @return File
     */
    public static boolean createDirectory(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return file.mkdirs();
        } else {
            return true;
        }
    }
}