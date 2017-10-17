package com.logg.printer.manager;

import com.logg.config.LoggConfiguration;
import com.logg.interceptor.LoggInterceptor;

public interface IPrinterManager {

    void init();

    void init(LoggConfiguration configuration);

    void v(Object object);

    void v(String tag, Object object);

    void d(Object object);

    void d(String tag, Object object);

    void i(Object object);

    void i(String tag, Object object);

    void w(Object object);

    void w(String tag, Object object);

    void e(Object object);

    void e(String tag, Object object);

    void wtf(Object object);

    void wtf(String tag, Object object);

    void json(Object object);

    void json(String tag, Object object);

    void xml(Object object);

    void xml(String tag, Object object);

    void addInterceptor(LoggInterceptor interceptor);

    void removeInterceptor(LoggInterceptor interceptor);

    void clearInterceptors();

    LoggConfiguration getConfiguration();
}