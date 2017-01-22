package com.tool.common.log.common;

import android.os.Environment;

import com.tool.common.log.log.parser.BundleParse;
import com.tool.common.log.log.parser.CollectionParse;
import com.tool.common.log.log.parser.IntentParse;
import com.tool.common.log.log.parser.MapParse;
import com.tool.common.log.log.parser.Parser;
import com.tool.common.log.log.parser.ReferenceParse;
import com.tool.common.log.log.parser.ThrowableParse;

/**
 * 常量
 */
public class Constant {

    //Value is null
    public static final String NULL = "null";

    // 解析属性最大层级
    public static final int MAX_LEVEL = 2;

    // 换行符
    public static final String BR = System.getProperty("line.separator");

    // 每行最大日志长度
    public static final int LINE_MAX = 1024 * 2;

    // 默认支持解析库
    public static final Class<? extends Parser>[] DEFAULT_PARSER_CLASS = new Class[]{
            BundleParse.class, IntentParse.class, CollectionParse.class,
            MapParse.class, ThrowableParse.class, ReferenceParse.class
    };

    // 默认Crash保存目录
    public static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/QLog/" + "CrashLog/";
}
