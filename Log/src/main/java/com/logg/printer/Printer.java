package com.logg.printer;

/**
 * 日志输出
 */
public interface Printer {

    /**
     * 日志输出
     */
    void printer(Type type, String tag, String object);
}
