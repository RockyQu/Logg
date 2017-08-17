package com.logg.parser;

import com.logg.LogConstant;

/**
 * 格式化对象
 */
public interface Parser<T> {

    String LINE_SEPARATOR = LogConstant.BR;

    Class<T> parseClassType();

    String parseString(T t);
}
