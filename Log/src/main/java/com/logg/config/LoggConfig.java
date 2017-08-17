package com.logg.config;

import com.logg.parser.Parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Log参数配置
 */
public class LoggConfig {

    // 这是一个单例
    private static LoggConfig loggConfig = null;

    private boolean debug = true;
    private String tag = null;

    private List<Parser> parsers = new ArrayList<>();

    public LoggConfig(Buidler buidler) {
        this.debug = buidler.debug;
        this.tag = buidler.tag;
        this.parsers = buidler.parsers;

        this.addParserClass(LoggConstant.DEFAULT_PARSER_CLASS);
    }

    public static LoggConfig getConfig() {
        return loggConfig;
    }

    public static LoggConfig getInstance(Buidler buidler) {
        if (loggConfig == null) {
            synchronized (LoggConfig.class) {
                if (loggConfig == null) {
                    loggConfig = new LoggConfig(buidler);
                }
            }
        }
        return loggConfig;
    }

    public static Buidler buidler() {
        return new Buidler();
    }

    public static class Buidler {

        // Whether to enable log output
        private boolean debug = true;
        // Default Tag
        private String tag = null;

        /*
         * Custom Parser
         * When the input type can not meet the demand or can not be resolved, can be achieved Parser interface rewriting method to add custom resolver.
         */
        private List<Parser> parsers = new ArrayList<>();

        private Buidler() {
            ;
        }

        public Buidler setTag(String tag) {
            if (tag == null) {
                throw new IllegalArgumentException("Tag can not be empty!");
            }
            this.tag = tag;
            return this;
        }

        public Buidler setDebug(boolean debug) {
            this.debug = debug;
            return this;
        }

        public LoggConfig build() {
            return LoggConfig.getInstance(this);
        }
    }

    public List<Parser> getParsers() {
        return parsers;
    }

    /**
     * Add a custom parser
     */
    public LoggConfig addParserClass(Class<? extends Parser>... classes) {
        for (Class<? extends Parser> cla : classes) {
            try {
                parsers.add(0, cla.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public boolean isDebug() {
        return debug;
    }

    public String getTag() {
        return tag;
    }
}