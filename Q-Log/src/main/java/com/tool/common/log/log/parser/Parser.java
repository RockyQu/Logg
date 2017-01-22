package com.tool.common.log.log.parser;

import com.tool.common.log.common.Constant;

/**
 * 格式化对象
 */
public interface Parser<T> {

    String LINE_SEPARATOR = Constant.BR;

    Class<T> parseClassType();

    String parseString(T t);
}
