package com.logg.interceptor.callback;

import com.logg.printer.Type;

public interface ICallback {

    /**
     * 添加一个全局回调
     */
    void addCallback(LoggCallback callback);

    /**
     * 移除一个全局回调
     */
    void removeCallback(LoggCallback callback);

    /**
     * 清空全部
     */
    void clearCallbacks(boolean clear);

    /**
     * 遍历全局回调集合，并将Log信息回调至{@link LoggCallback#logg(Type, String, String)}方法
     */
    void printerAll(Type type, String tag, String object);
}