package com.logg.interceptor;

import com.logg.printer.Type;

/**
 * 这是一个 Logg 拦截器，思想类似 Okhttp 的 Interceptor
 * 当你添加一个{@link Interceptor}它会在最终打印Log前进行拦截处理，你可以在{@link #intercept(LoggStructure)}方法里进行日志的重构处理
 * 添加一个新的 Interceptor 请继承 {@link LoggInterceptor} 根据需求重写相关方法
 */
public class LoggInterceptor implements Interceptor {

    /**
     * Logg 输出开关控件，可以根据{@link Type#getValue()}类型来过滤不同级别的日志信息
     *
     * @param type 类型
     */
    @Override
    public boolean isLoggable(Type type) {
        return true;
    }

    /**
     * {@link LoggStructure}包含Logg相关的所有信息
     *
     * @param structure
     * @see LoggStructure
     */
    @Override
    public LoggStructure intercept(LoggStructure structure) {
        // 如果不做任务处理返回 structure
        return structure;
    }
}