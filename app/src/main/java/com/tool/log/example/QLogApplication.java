package com.tool.log.example;

import android.app.Application;

import com.tool.common.log.QLog;
import com.tool.common.log.common.Constant;
import com.tool.common.log.common.Setting;
import com.tool.common.log.crash.CrashListener;

/**
 * QLog Application.
 */
public class QLogApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        QLog.e("QLogApplication onCreate");

        Setting.getInstance()
                .setContext(getApplicationContext())//You must first set Context
                .setTag(null)// 设置LogTag
                .setDebug(true)// 开启Log输出
                .setCrash(true)// 开启自定义Crash
                .setPath(Constant.PATH);// Crash保存目录

        // Crash监听
        Setting.getInstance().setCrashListener(new CrashListener() {

            @Override
            public void error(Throwable throwable) {
                QLog.i(throwable);
            }

            @Override
            public void finish(String path) {
                QLog.i(path);

                //TODO Upload Server
            }
        });
    }

    @Override
    public void onTerminate() {
        QLog.e("QLogApplication onTerminate");
        super.onTerminate();
    }
}