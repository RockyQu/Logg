package com.tool.common.log.common;

import android.content.Context;

import com.tool.common.log.crash.CrashHandler;
import com.tool.common.log.crash.CrashListener;
import com.tool.common.log.log.parser.Parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Setting
 */
public class Setting {

    /**
     * Context
     */
    private Context context = null;

    /**
     * 是否允许日志输出
     */
    private boolean isDebug = false;
    // 设置默认Tag
    private String tag = null;
    // 自定义解析类
    private List<Parser> parsers = new ArrayList<>();

    /**
     * 是否开启自定义Crash
     */
    private boolean isCrash = false;
    // 默认Crash保存目录
    private String path = Constant.PATH;

    public Setting() {
        ;
    }

    /**
     * 类级的内部类，也就是静态类的成员式内部类，该内部类的实例与外部类的实例没有绑定关系，而且只有被调用到时才会装载，从而实现了延迟加载。
     */
    private static class SettingHolder {

        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static Setting setting = new Setting();
    }

    /**
     * 获取单例
     *
     * @return Setting
     */
    public static Setting getInstance() {
        return SettingHolder.setting;
    }

    public Context getContext() {
        return context;
    }

    public Setting setContext(Context context) {
        this.context = context;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public Setting setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public Setting setDebug(boolean debug) {
        isDebug = debug;
        return this;
    }

    public List<Parser> getParsers() {
        return parsers;
    }

    /**
     * 添加自定义解析类
     */
    public Setting addParserClass(Class<? extends Parser>... classes) {
        for (Class<? extends Parser> cla : classes) {
            try {
                parsers.add(0, cla.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public boolean isCrash() {
        return isCrash;
    }

    /**
     * 设置是否拦截异常信息
     */
    public Setting setCrash(boolean crash) {
        isCrash = crash;

        if (isCrash) {
            CrashHandler.getInstance().install();
        } else {
            CrashHandler.getInstance().uninstall();
        }

        return this;
    }

    public String getPath() {
        return path;
    }

    /**
     * 设置异常信息保存目录
     */
    public Setting setPath(String path) {
        this.path = path;
        return this;
    }

    public CrashListener getCrashListener() {
        return CrashHandler.getInstance().getCrashListener();
    }

    /**
     * 设置异常信息状态结果监听回调
     */
    public Setting setCrashListener(CrashListener crashListener) {
        CrashHandler.getInstance().setCrashListener(crashListener);
        return this;
    }
}
