/*
 * Copyright 2016-2018 RockyQu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.logg.config;

import me.logg.parser.BundleParse;
import me.logg.parser.CollectionParse;
import me.logg.parser.IntentParse;
import me.logg.parser.MapParse;
import me.logg.parser.Parser;
import me.logg.parser.ReferenceParse;
import me.logg.parser.ThrowableParse;

/**
 * 常量配置
 */
public class LoggConstant {

    // Value is null
    public static final String NULL = "null";

    // 解析属性最大层级，请不要将 MAX_LEVEL 设置过高，可能会带来方法嵌套调用性能问题
    public static final int MAX_LEVEL = 2;

    // 换行符
    public static final String BR = System.getProperty("line.separator");

    // 空格
    public static final String SPACE = " ";

    // 每行最大日志长度
    public static final int LINE_MAX = 1024 * 2;

    // 默认支持解析库
    public static final Class[] DEFAULT_PARSER_CLASS = new Class[]{
            BundleParse.class, IntentParse.class, CollectionParse.class, MapParse.class, ThrowableParse.class, ReferenceParse.class
    };
}