package com.tool.common.log.crash;

/**
 * CrashListener
 */
public interface CrashListener {

    /**
     * 触发异常
     *
     * @param throwable
     */
    void error(Throwable throwable);

    /**
     * 异常信息保存完毕
     *
     * @param path
     */
    void finish(String path);
}