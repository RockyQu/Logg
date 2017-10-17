package com.logg.interceptor.callback;

import com.logg.printer.Type;
import com.logg.printer.manager.PrinterManager;

import java.util.List;

/**
 * 提供全局回调接口，如果你设置了此接口，在不影响底层输出的情况下，所有的Log都会回调到这个接口里
 * 注意控制集合的数量，避免存放过多接口而导致性能上的问题
 * 注意：不要在 LoggListener 接口的 logg 方法里再调用 Logg 会造成死循环
 * <p>
 * 使用方法
 */
public interface LoggStrategy {

    void logg(Type type, String tag, String message);
}