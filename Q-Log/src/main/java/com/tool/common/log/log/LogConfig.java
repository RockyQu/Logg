package com.tool.common.log.log;

import android.content.Context;

import com.tool.common.log.log.parser.Parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Log参数配置
 */
public class LogConfig {

    private volatile static LogConfig logConfig;

    // Context
    private Context context;

    // 是否开启日志输出
    private boolean open = true;
    // 设置Log默认Tag
    private String tag = null;

    /*
     * 自定义解析器
     * 当输入类型无法满足需求时或无法解析时，可实现Parser接口重写方法来添加自定义解析器
     */
    private List<Parser> parsers = new ArrayList<>();

    public LogConfig(Buidler buidler) {
        this.context = buidler.context;
        this.open = buidler.open;
        this.tag = buidler.tag;
        this.parsers = buidler.parsers;

        this.addParserClass(LogConstant.DEFAULT_PARSER_CLASS);
    }

    public static LogConfig getInstance(Buidler buidler) {
        if (logConfig == null) {
            synchronized (LogConfig.class) {
                if (logConfig == null) {
                    logConfig = new LogConfig(buidler);
                }
            }
        }
        return logConfig;
    }

    public static LogConfig getConfig() {
        return logConfig;
    }

    /**
     * 基本配置
     */
    public static class Buidler {

        // Context
        private Context context;

        // 是否开启日志输出
        private boolean open = false;
        // 设置Log默认Tag
        private String tag = null;

        /*
         * 自定义解析器
         * 当输入类型无法满足需求时或无法解析时，可实现Parser接口重写方法来添加自定义解析器
         */
        private List<Parser> parsers = new ArrayList<>();

        private Buidler() {
            ;
        }

        public static Buidler buidler() {
            return new Buidler();
        }

        public Buidler setContext(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context can not be empty!");
            }
            this.context = context;
            return this;
        }

        public Buidler setTag(String tag) {
            if (tag == null) {
                throw new IllegalArgumentException("Tag can not be empty!");
            }
            this.tag = tag;
            return this;
        }

        public Buidler setOpen(boolean open) {
            this.open = open;
            return this;
        }

        public LogConfig build() {
            return LogConfig.getInstance(this);
        }
    }

    public Context getContext() {
        return context;
    }

    public List<Parser> getParsers() {
        return parsers;
    }

    /**
     * 添加自定义解析器
     */
    public LogConfig addParserClass(Class<? extends Parser>... classes) {
        for (Class<? extends Parser> cla : classes) {
            try {
                parsers.add(0, cla.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public boolean isOpen() {
        return open;
    }

    public String getTag() {
        return tag;
    }
}