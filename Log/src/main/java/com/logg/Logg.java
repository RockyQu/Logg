package com.logg;

import com.logg.printer.manager.PrinterManager;

/**
 * 日志管理器
 */
public class Logg {

    /**
     * 日志打印管理器
     */
    private static PrinterManager printer = new PrinterManager();

    public Logg() {
        throw new AssertionError();
    }

    /**
     * 日志输出-#BBBBBB
     *
     * @param object
     */
    public static void v(Object object) {
        printer.v(object);
    }

    /**
     * 日志输出-#0070BB
     *
     * @param object
     */
    public static void d(Object object) {
        printer.d(object);
    }

    /**
     * 日志输出-#48BB31
     *
     * @param object
     */
    public static void i(Object object) {
        printer.i(object);
    }

    /**
     * 日志输出-# BBBB23
     *
     * @param object
     */
    public static void w(Object object) {
        printer.w(object);
    }

    /**
     * 日志输出-#FF0006
     *
     * @param object
     */
    public static void e(Object object) {
        printer.e(object);
    }

    /**
     * 日志输出-#8F0005
     *
     * @param object
     */
    public static void wtf(Object object) {
        printer.wtf(object);
    }

    /**
     * Json
     *
     * @param object
     */
    public static void json(Object object) {
        printer.json(object);
    }

    /**
     * XML
     *
     * @param object
     */
    public static void xml(Object object) {
        printer.xml(object);
    }
}