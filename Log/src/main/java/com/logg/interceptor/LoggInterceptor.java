package com.logg.interceptor;

import com.logg.printer.Type;

/**
 * 这是一个 Logg 拦截器，思想类似 Okhttp 的 Interceptor
 */
public interface LoggInterceptor {

    boolean isLoggable();

    void proceed(Type type, String tag, Object object);
}