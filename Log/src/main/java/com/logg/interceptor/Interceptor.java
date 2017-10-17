package com.logg.interceptor;

import com.logg.printer.Type;

public interface Interceptor {

    boolean isLoggable(Type type);

    LoggStructure intercept(LoggStructure structure);
}