package com.logg.example.helper;

import android.util.Log;

import me.logg.interceptor.LoggInterceptor;
import me.logg.interceptor.LoggStructure;
import me.logg.printer.Type;

public class TestInterceptor extends LoggInterceptor {

    @Override
    public boolean isLoggable(Type type) {
        return true;
    }

    @Override
    public LoggStructure intercept(LoggStructure structure) {
        Log.e("TestInterceptor", "intercept");
        return structure;
    }
}