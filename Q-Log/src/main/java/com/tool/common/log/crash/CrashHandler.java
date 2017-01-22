package com.tool.common.log.crash;

import android.os.Build;

import com.tool.common.log.QLog;
import com.tool.common.log.common.Setting;
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

public class CrashHandler {

    /**
     * 系统默认的Thread.UncaughtException处理类
     */
    private Thread.UncaughtExceptionHandler defaultExceptionHandler;

    /**
     * 保存Crash堆栈相关信息
     */
    private List<String> crashInfo = new ArrayList<>();

    /**
     * Log参数配置
     */
    private Setting setting = null;

    //是否存在SDCard
    private boolean isSDCard = false;

    //CrashListener
    private CrashListener crashListener = null;

    public CrashHandler() {
        setting = Setting.getInstance();
    }

    /**
     * 类级的内部类，也就是静态类的成员式内部类，该内部类的实例与外部类的实例没有绑定关系，而且只有被调用到时才会装载，从而实现了延迟加载。
     */
    private static class CrashHolder {

        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static CrashHandler crash = new CrashHandler();
    }

    /**
     * 获取单例
     *
     * @return Setting
     */
    public static CrashHandler getInstance() {
        return CrashHolder.crash;
    }

    /**
     * 安装初始化
     */
    public void install() {
        isSDCard = FileUtil.existSDCard();
        if (!isSDCard) {
            QLog.w("Not installed SDCard, which causes the crash information could not be saved.");
        } else {
            if (!FileUtil.createDirectory(setting.getPath())) {
                QLog.w("Create QLog directory fails, which causes the crash information could not be saved.");
            }
        }

        if (setting.isCrash()) {
            defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

            if (defaultExceptionHandler != null) {
                Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

                    @Override
                    public void uncaughtException(Thread thread, Throwable throwable) {

                        if (getCrashListener() != null) {
                            getCrashListener().error(throwable);
                        }

                        if (handleException(throwable)) {
                            // 保存错误报告至文件
                            save(throwable);
                        } else {
                            QLog.w("Collected error information failed.");
                        }

                        if (defaultExceptionHandler != null) {
                            defaultExceptionHandler.uncaughtException(thread, throwable);
                        }
                    }
                });
            } else {
                QLog.w("Get DefaultUncaughtExceptionHandler Failure.");
            }
        } else {
            QLog.w("Not turned Crash, example:Setting.getInstance().setCrash(true);");
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

        if (setting.getContext() != null) {
            crashInfo.add("Version:" + AppUtils.getVersionName(setting.getContext()));
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
    private void save(Throwable throwable) {
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

        try {
            // 文件路径
            String savePath = Setting.getInstance().getPath() + TimeUtils.formatTime() + "_" + TimeUtils.currentTimeMillis() + ".log";
            // 保存至文件
            FileOutputStream trace = new FileOutputStream(savePath, true);
            for (String string : crashInfo) {
                trace.write((string + "\n").getBytes());
            }
            trace.flush();
            trace.close();

            if (getCrashListener() != null) {
                getCrashListener().finish(savePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CrashListener getCrashListener() {
        return crashListener;
    }

    public void setCrashListener(CrashListener crashListener) {
        this.crashListener = crashListener;
    }

    /**
     * 卸载释放
     */
    public void uninstall() {
        Thread.setDefaultUncaughtExceptionHandler(null);
    }
}
