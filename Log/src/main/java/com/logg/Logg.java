package com.logg;

import com.logg.printer.manager.PrinterManager;

/**
 * 日志管理器
 */
public class Logg {

    /**
     * 日志打印管理器
     */
    private static PrinterManager printer = null;

    static {
        printer = PrinterManager.get();
    }

    public Logg() {
        throw new AssertionError();
    }

    /**
     * 增加一个细粒度自定义前缀Tag{@link PrinterManager#tag(String)}
     *
     * @param tag
     */
    public static PrinterManager tag(String tag) {
        return printer.tag(tag);
    }

    /**
     * {@link PrinterManager#v(Object)}
     * Color #BBBBBB
     *
     * @param object
     */
    public static void v(Object object) {
        printer.v(object);
    }

    /**
     * {@link PrinterManager#d(Object)}
     * Color #0070BB
     *
     * @param object
     */
    public static void d(Object object) {
        printer.d(object);
    }

    /**
     * {@link PrinterManager#i(Object)}
     * Color #48BB31
     *
     * @param object
     */
    public static void i(Object object) {
        printer.i(object);
    }

    /**
     * {@link PrinterManager#w(Object)}
     * Color #BBBB23
     *
     * @param object
     */
    public static void w(Object object) {
        printer.w(object);
    }

    /**
     * {@link PrinterManager#e(Object)}
     * Color #FF0006
     *
     * @param object
     */
    public static void e(Object object) {
        printer.e(object);
    }

    /**
     * {@link PrinterManager#wtf(Object)}
     * Color #8F0005
     *
     * @param object
     */
    public static void wtf(Object object) {
        printer.wtf(object);
    }

    /**
     * Json
     * {@link PrinterManager#json(Object)}
     *
     * @param object
     */
    public static void json(Object object) {
        printer.json(object);
    }

    /**
     * XML
     * {@link PrinterManager#xml(Object)}
     *
     * @param object
     */
    public static void xml(Object object) {
        printer.xml(object);
    }
}