package com.logg.interceptor.callback;

import com.logg.printer.Type;

public interface ICallback {

    /**
     * 添加一个全局回调
     */
    void addCallback(LoggStrategy callback);

    /**
     * 移除一个全局回调
     */
    void removeCallback(LoggStrategy callback);

    /**
     * 清空全部
     */
    void clearCallbacks(boolean clear);

    /**
     * 遍历全局回调集合，并回调至{@link LoggStrategy#logg(Type, String, String)}方法
     */
    void printerAll(Type type, String tag, String object);
}