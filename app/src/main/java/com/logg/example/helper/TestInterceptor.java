package com.logg.example.helper;

import android.util.Log;

import com.logg.interceptor.LoggInterceptor;
import com.logg.interceptor.LoggStructure;
import com.logg.printer.Type;

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