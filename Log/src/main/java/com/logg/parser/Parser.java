package com.logg.parser;

import com.logg.config.LoggConstant;

/**
 * 格式化对象
 */
public interface Parser<T> {

    String LINE_SEPARATOR = LoggConstant.BR;

    Class<T> parseClassType();

    String parseString(T t);
}
