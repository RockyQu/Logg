package com.logg.example;

import android.app.Application;
import com.logg.config.LoggConfig;

public class LoggApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LoggConfig
                .buidler()
                .setDebug(true)
                .build();

    }
}