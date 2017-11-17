/*
 * Copyright 2016-2018 DesignQu
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

package com.logg;

import com.logg.config.LoggConfiguration;
import com.logg.interceptor.LoggInterceptor;
import com.logg.printer.manager.IPrinterManager;
import com.logg.printer.manager.PrinterManager;

/**
 * 日志管理器
 */
public class Logg {

    /**
     * 日志打印管理器
     */
    private static IPrinterManager printer = null;

    static {
        printer = PrinterManager.getInstance();
    }

    public Logg() {
        throw new AssertionError();
    }

    public static void init() {
        printer.init();
    }

    public static void init(LoggConfiguration configuration) {
        printer.init(configuration);
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

    public static void v(String tag, Object object) {
        printer.v(tag, object);
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

    public static void d(String tag, Object object) {
        printer.d(tag, object);
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

    public static void i(String tag, Object object) {
        printer.i(tag, object);
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

    public static void w(String tag, Object object) {
        printer.w(tag, object);
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

    public static void e(String tag, Object object) {
        printer.e(tag, object);
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

    public static void wtf(String tag, Object object) {
        printer.wtf(tag, object);
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

    public static void json(String tag, Object object) {
        printer.json(tag, object);
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

    public static void xml(String tag, Object object) {
        printer.xml(tag, object);
    }

    public static void addInterceptor(LoggInterceptor interceptor) {
        printer.addInterceptor(interceptor);
    }

    public static void removeInterceptor(LoggInterceptor interceptor) {
        printer.removeInterceptor(interceptor);
    }

    public static void clearInterceptors() {
        printer.clearInterceptors();
    }
}