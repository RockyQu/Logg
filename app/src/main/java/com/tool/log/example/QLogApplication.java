package com.tool.log.example;

import android.app.Application;

import com.tool.common.log.QLog;
import com.tool.common.log.crash.ThreadCatchInterceptor;
import com.tool.common.log.log.LogConfig;

/**
 * QLog Application.
 */
public class QLogApplication extends Application {

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

        ThreadCatchInterceptor.getInstance().install(new ThreadCatchInterceptor.CallBack() {

            @Override
            public void error(Throwable throwable) {
                QLog.i(throwable);
            }

            @Override
            public void finish(String path) {
                QLog.i(path);
            }
        });
    }
}