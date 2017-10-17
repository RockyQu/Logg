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
    private Object object;

    // 线程信息，如果线程信息被禁用则为Null
    private String thread;

    private LoggStructure() {

    }

    public LoggStructure(Type type, String tag, Object object) {
        this(type, tag, object, null);
    }

    public LoggStructure(Type type, String tag, Object object, String thread) {
        this.type = type;
        this.tag = tag;
        this.object = object;
        this.type = type;
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

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }
}