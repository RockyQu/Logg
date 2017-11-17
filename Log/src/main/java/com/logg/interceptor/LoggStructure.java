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

package com.logg.interceptor;

import com.logg.printer.Type;

/**
 * 要打印的单个日志
 */
public class LoggStructure {

    // 类型
    private Type type;
    // 前缀
    private String tag;
    // 内容
    private String object;

    // 线程信息，如果线程信息被禁用则为Null
    private String thread;

    private LoggStructure() {

    }

    public LoggStructure(Type type, String tag, String object) {
        this(type, tag, object, null);
    }

    public LoggStructure(Type type, String tag, String object, String thread) {
        this.type = type;
        this.tag = tag;
        this.object = object;
        this.thread = thread;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }
}