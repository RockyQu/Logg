package com.logg.config;

import com.logg.parser.BundleParse;
import com.logg.parser.CollectionParse;
import com.logg.parser.IntentParse;
import com.logg.parser.MapParse;
import com.logg.parser.Parser;
import com.logg.parser.ReferenceParse;
import com.logg.parser.ThrowableParse;

/**
 * 常量
 */
public class LoggConstant {

    // Value is null
    public static final String NULL = "null";

    // 解析属性最大层级
    public static final int MAX_LEVEL = 2;

    // 换行符
    public static final String BR = System.getProperty("line.separator");

    // 每行最大日志长度
    public static final int LINE_MAX = 1024 * 3;

    // 默认支持解析库
    public static final Class<? extends Parser>[] DEFAULT_PARSER_CLASS = new Class[]{
            BundleParse.class, IntentParse.class, CollectionParse.class,
            MapParse.class, ThrowableParse.class, ReferenceParse.class
    };
}