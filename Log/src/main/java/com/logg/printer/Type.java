package com.logg.printer;

/**
 * 日志类型
 */
public enum Type {

    V("verbose"),
    D("debug"),
    I("info"),
    W("warn"),
    E("error"),
    WTF("wtf"),
    J("json"),//Json
    X("xml");//XML

    private String value;

    Type(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}