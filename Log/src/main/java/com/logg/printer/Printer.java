package com.logg.printer;

/**
 * 日志打印
 * 所有日志最终输出至{@link DefaultPrinter#printer(Type, String, String)}方法
 */
public interface Printer {

    void printer(Type type, String tag, String object);
}