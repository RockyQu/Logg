package com.logg.example;

import android.app.Application;

import com.logg.Logg;
import com.logg.config.LoggConfiguration;

public class LoggApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LoggConfiguration configuration = new LoggConfiguration.Buidler()
                .setDebug(true)
//                .setTag("test")// 自定义全局Tag
                .build();
        Logg.init(configuration);
    }
}