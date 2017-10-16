package com.logg.interceptor.callback;

import com.logg.printer.Type;

import java.util.List;

/**
 *
 */
public interface ICallback {

    void addCallback(LoggCallback callback);

    void removeCallback(LoggCallback callback);

    void clearCallback(boolean clear);

    List<LoggCallback> getCallbacks();

    void printerAll(Type type, String tag, String object);
}