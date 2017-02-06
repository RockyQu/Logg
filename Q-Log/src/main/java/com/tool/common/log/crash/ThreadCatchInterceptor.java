package com.tool.common.log.crash;

import android.os.Build;
import android.os.Environment;

import com.tool.common.log.QLog;
import com.tool.common.log.log.LogConfig;
import com.tool.common.log.util.AppUtils;
import com.tool.common.log.util.FileUtil;
import com.tool.common.log.util.TimeUtils;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ThreadCatchInterceptor {

    // 系统默认的Thread.UncaughtException处理类
    private Thread.UncaughtExceptionHandler defaultExceptionHandler;

    // Crash堆栈相关信息
    private List<String> crashInfo = new ArrayList<>();

    // Log参数配置
    private LogConfig logConfig = null;

    // 默认Crash保存目录
    private String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/QLog/" + "CrashLog/";

    private final Logger logger;

    public ThreadCatchInterceptor() {
        this.logger = Logger.DEFAULT;

        logConfig = LogConfig.getConfig();
    }

    /**
     * 类级的内部类，也就是静态类的成员式内部类，该内部类的实例与外部类的实例没有绑定关系，而且只有被调用到时才会装载，从而实现了延迟加载。
     */
    private static class CrashHolder {

        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static ThreadCatchInterceptor crash = new ThreadCatchInterceptor();
    }

    /**
     * 获取单例
     *
     * @return Setting
     */
    public static ThreadCatchInterceptor getInstance() {
        return CrashHolder.crash;
    }

    /**
     * 安装初始化
     */
    public void install(final CallBack callBack) {
        if (!FileUtil.existSDCard()) {
            logger.log("Not installed SDCard, which causes the crash information could not be saved.");
        } else {
            if (!FileUtil.createDirectory(PATH)) {
                logger.log("Create QLog directory fails, which causes the crash information could not be saved.");
            }
        }

        defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

        if (defaultExceptionHandler != null) {
            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

                @Override
                public void uncaughtException(Thread thread, Throwable throwable) {

                    if (callBack != null) {
                        callBack.error(throwable);
                    }

                    if (handleException(throwable)) {
                        // 保存错误报告至文件
                        callBack.finish(save(throwable));
                    } else {
                        logger.log("Collected error information failed.");
                    }

                    if (defaultExceptionHandler != null) {
                        defaultExceptionHandler.uncaughtException(thread, throwable);
                    }
                }
            });
        } else {
            logger.log("Get DefaultUncaughtExceptionHandler Failure.");
        }
    }

    /**
     * 收集整理错误信息
     *
     * @param throwable
     * @return
     */
    private boolean handleException(Throwable throwable) {
        if (throwable == null) {
            return false;
        }

        if (logConfig.getContext() != null) {
            crashInfo.add("Version:" + AppUtils.getVersionName(logConfig.getContext()));
        }

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                crashInfo.add(field.getName() + ": " + field.get(null));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    /**
     * 保存错误信息到文件中
     *
     * @param throwable
     * @return
     */
    private String save(Throwable throwable) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);

        // 将此 throwable 及其追踪输出到指定的PrintWriter
        throwable.printStackTrace(printWriter);

        // getCause() 返回此 throwable 的 cause；如果 cause 不存在或未知，则返回 null。
        Throwable cause = throwable.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }

        // toString() 以字符串的形式返回该缓冲区的当前值。
        String result = writer.toString();
        printWriter.close();
        crashInfo.add(result);

        // 文件路径
        String savePath = PATH + TimeUtils.formatTime() + "_" + TimeUtils.currentTimeMillis() + ".log";
        try {
            // 保存至文件
            FileOutputStream trace = new FileOutputStream(savePath, true);
            for (String string : crashInfo) {
                trace.write((string + "\n").getBytes());
            }
            trace.flush();
            trace.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return savePath;
    }

    /**
     * 卸载释放
     */
    public void uninstall() {
        Thread.setDefaultUncaughtExceptionHandler(null);
    }

    public interface CallBack {

        /**
         * 触发异常
         *
         * @param throwable
         */
        void error(Throwable throwable);

        /**
         * 保存完毕
         *
         * @param path 保存路径
         */
        void finish(String path);
    }

    private interface Logger {
        void log(String message);

        Logger DEFAULT = new Logger() {

            @Override
            public void log(String message) {
                QLog.w(message);
            }
        };
    }
}