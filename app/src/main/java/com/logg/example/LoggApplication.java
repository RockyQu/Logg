package com.logg.example;

import android.app.Application;
import com.logg.LogConfig;

public class LoggApplication extends Application {

    // Log配置
    private LogConfig logConfig;

    @Override
    public void onCreate() {
        super.onCreate();

        this.logConfig = LogConfig.Buidler
                .buidler()
                .setContext(this)
                .setOpen(true)
                .build();
    }
}