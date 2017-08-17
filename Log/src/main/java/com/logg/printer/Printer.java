package com.logg.printer;

/**
 * 日志输出接口
 */
public interface Printer {

    /**
     * 默认日志输出
     */
    void printer(Type type, String tag, String object);
}
