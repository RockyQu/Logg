/*
 * Copyright 2016-2018 DesignQu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.logg.config;

import com.logg.interceptor.LoggInterceptor;
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

    private List<LoggInterceptor> interceptors;

    public LoggConfiguration(Buidler buidler) {
        this.debug = buidler.debug;
        this.tag = buidler.tag;
        this.parsers = buidler.parsers;

        this.addParserClass(LoggConstant.DEFAULT_PARSER_CLASS);

        this.interceptors = buidler.interceptors;
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

    public List<LoggInterceptor> getInterceptors() {
        return interceptors;
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

        /**
         * LoggInterceptors
         */
        private List<LoggInterceptor> interceptors = new ArrayList<>();

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

        public Buidler addInterceptor(LoggInterceptor interceptor) {
            this.interceptors.add(interceptor);
            return this;
        }

        public LoggConfiguration build() {
            return new LoggConfiguration(this);
        }
    }
}