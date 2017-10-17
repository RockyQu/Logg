package com.logg.config;

import com.logg.parser.Parser;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数配置
 */
public class LoggConfiguration {

    private boolean debug = true;
    private String tag = null;

    private List<Parser> parsers = new ArrayList<>();

    public LoggConfiguration(Buidler buidler) {
        this.debug = buidler.debug;
        this.tag = buidler.tag;
        this.parsers = buidler.parsers;

        this.addParserClass(LoggConstant.DEFAULT_PARSER_CLASS);
    }

    public static class Buidler {

        /**
         * 是否开启Debug模试
         * 在发布正式版本,应该关闭Log日志的输出
         */
        private boolean debug = true;

        /**
         * 所有日志的前缀Tag
         * 如果你设置的单个日志Tag会覆盖此变量
         */
        private String tag = null;

        /**
         * 如果你想处理解析一些此框架不支持的数据,可以实现{@link Parser}接口,实现自己的解析方式
         */
        private List<Parser> parsers = new ArrayList<>();

        public Buidler() {
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

        public LoggConfiguration build() {
            return new LoggConfiguration(this);
        }
    }

    public List<Parser> getParsers() {
        return parsers;
    }

    /**
     * Add a custom parser
     */
    public LoggConfiguration addParserClass(Class<? extends Parser>... classes) {
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