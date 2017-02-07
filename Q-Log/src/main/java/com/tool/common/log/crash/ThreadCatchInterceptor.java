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

    // Thread.UncaughtException
    private Thread.UncaughtExceptionHandler defaultExceptionHandler;

    // Crash Stack-related information
    private List<String> crashInfo = new ArrayList<>();

    // Log Config
    private LogConfig logConfig = null;

    // Crash Save Directory
    private String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/QLog/" + "CrashLog/";

    private final Logger logger;

    public ThreadCatchInterceptor() {
        this.logger = Logger.DEFAULT;

        logConfig = LogConfig.getConfig();
    }

    private static class CrashHolder {
        private static ThreadCatchInterceptor crash = new ThreadCatchInterceptor();
    }

    /**
     * Get Singleton
     *
     * @return Setting
     */
    public static ThreadCatchInterceptor getInstance() {
        return CrashHolder.crash;
    }

    /**
     * Install
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
                        // Save the error report to a file
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
     * Gathering error messages
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
     * Save the error message to a file
     *
     * @param throwable
     * @return
     */
    private String save(Throwable throwable) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);

        throwable.printStackTrace(printWriter);

        Throwable cause = throwable.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }

        String result = writer.toString();
        printWriter.close();
        crashInfo.add(result);

        String savePath = PATH + TimeUtils.formatTime() + "_" + TimeUtils.currentTimeMillis() + ".log";
        try {
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

    public void uninstall() {
        Thread.setDefaultUncaughtExceptionHandler(null);
    }

    public interface CallBack {

        /**
         * Fires an exception
         *
         * @param throwable
         */
        void error(Throwable throwable);

        /**
         * Save finished
         *
         * @param path
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