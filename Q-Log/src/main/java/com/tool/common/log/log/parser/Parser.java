package com.tool.common.log.log.parser;

import com.tool.common.log.log.LogConstant;

/**
 * 格式化对象
 */
public interface Parser<T> {

    String LINE_SEPARATOR = LogConstant.BR;

    Class<T> parseClassType();

    String parseString(T t);
}
