package com.logg.printer.manager;

import android.text.TextUtils;

import com.logg.Logg;
import com.logg.config.LoggConfiguration;
import com.logg.config.LoggConstant;
import com.logg.interceptor.Interceptor;
import com.logg.interceptor.LoggStructure;
import com.logg.printer.DefaultPrinter;
import com.logg.printer.JsonPrinter;
import com.logg.printer.Printer;
import com.logg.printer.Type;
import com.logg.printer.XmlPrinter;
import com.logg.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * 日志管理器
 */
public class PrinterManager implements IPrinterManager {

    // Default Printer
    private Printer defaultPrinter = null;
    // Json Printer
    private Printer jsonPrinter = null;
    // XML Printer
    private Printer xmlPrinter = null;

    /**
     * LoggInterceptors
     */
    private final List<Interceptor> interceptors = new ArrayList<>();

    /**
     * Parameter configuration
     */
    private LoggConfiguration configuration = null;

    private PrinterManager() {

    }

    private final static class SingletonHolder {
        private final static IPrinterManager INSTANCE = new PrinterManager();
    }

    public static IPrinterManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public void init() {
        LoggConfiguration configuration = new LoggConfiguration.Buidler()
                .setDebug(true)
                .build();

        this.init(configuration);
    }

    @Override
    public void init(LoggConfiguration configuration) {
        if (configuration == null) {
            throw new NullPointerException("LoggConfiguration == null");
        }

        this.configuration = configuration;

        defaultPrinter = new DefaultPrinter(this.configuration);
        jsonPrinter = new JsonPrinter(this.configuration);
        xmlPrinter = new XmlPrinter(this.configuration);
    }

    @Override
    public void v(Object object) {
        this.v(getTag(), object);
    }

    @Override
    public void v(String tag, Object object) {
        this.printer(Type.V, tag, object);
    }

    @Override
    public void d(Object object) {
        this.d(getTag(), object);
    }

    @Override
    public void d(String tag, Object object) {
        this.printer(Type.D, tag, object);
    }

    @Override
    public void i(Object object) {
        this.i(getTag(), object);
    }

    @Override
    public void i(String tag, Object object) {
        this.printer(Type.I, tag, object);
    }

    @Override
    public void w(Object object) {
        this.w(getTag(), object);
    }

    @Override
    public void w(String tag, Object object) {
        this.printer(Type.W, tag, object);
    }

    @Override
    public void e(Object object) {
        this.e(getTag(), object);
    }

    @Override
    public void e(String tag, Object object) {
        this.printer(Type.E, tag, object);
    }

    @Override
    public void wtf(Object object) {
        this.wtf(getTag(), object);
    }

    @Override
    public void wtf(String tag, Object object) {
        this.printer(Type.WTF, tag, object);
    }

    /**
     * Json
     *
     * @param object
     */
    @Override
    public void json(Object object) {
        this.json(getTag(), object);
    }

    @Override
    public void json(String tag, Object object) {
        this.printer(Type.J, tag, object);
    }

    /**
     * XML
     *
     * @param object
     */
    @Override
    public void xml(Object object) {
        this.xml(getTag(), object);
    }

    @Override
    public void xml(String tag, Object object) {
        this.printer(Type.X, tag, object);
    }

    @Override
    public void addInterceptor(Interceptor interceptor) {
        if (interceptor != null) {
            interceptors.add(interceptor);
        }
    }

    @Override
    public void removeInterceptor(Interceptor interceptor) {
        if (interceptor != null) {
            interceptors.remove(interceptor);
        }
    }

    @Override
    public void clearInterceptors() {
        interceptors.clear();
    }

    /**
     * 打印字符串
     *
     * @param type
     * @param object
     */
    private synchronized void printer(Type type, String tag, Object object) {
        if (configuration != null && !configuration.isDebug()) {
            return;
        }

        LoggStructure item = new LoggStructure(type, tag, object, null);
        for (Interceptor interceptor : interceptors) {
            if (interceptor.isLoggable(type)) {
                item = interceptor.intercept(item);
                if (item == null) {
                    throw new NullPointerException("LoggCallback == null");
                }

                if (item.getTag() == null) {
                    throw new NullPointerException("Tag == null, You can modify Tag, but can not empty Tag.");
                }

                if (item.getObject() == null) {
                    throw new NullPointerException("Object == null, You can modify the log information, but you can not clear the log information.");
                }
            }
        }

        switch (type) {
            case V:
            case D:
            case I:
            case W:
            case E:
            case WTF:
                String o = Utils.objectToString(object);
                if (o.length() > LoggConstant.LINE_MAX) {
                    for (String subMsg : Utils.bigStringToList(o)) {
                        defaultPrinter.printer(type, tag, subMsg);
                    }
                    return;
                } else {
                    defaultPrinter.printer(type, tag, o);
                }
                break;
            case J:
                jsonPrinter.printer(type, tag, Utils.objectToString(object));
                break;
            case X:
                xmlPrinter.printer(type, tag, Utils.objectToString(object));
                break;
            default:
                defaultPrinter.printer(type, tag, Utils.objectToString(object));
                break;
        }
    }

    /**
     * 拼接 Tag 前缀信息
     *
     * @return
     */
    private String getTag() {
        if (configuration != null) {
            if (!TextUtils.isEmpty(configuration.getTag())) {
                return configuration.getTag();
            } else {
                return getStackTraceFormatter();
            }
        } else {
            return getStackTraceFormatter();
        }
    }

    /**
     * 拼接 Tag 前缀信息
     *
     * @return
     */
    private String getStackTraceFormatter() {
        StackTraceElement caller = getCurrentStackTrace();
        if (caller == null) {
            return "";
        }
        String stackTrace = caller.toString();
        stackTrace = stackTrace.substring(stackTrace.lastIndexOf('('), stackTrace.length());
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        return String.format("%s.%s%s", callerClazzName, caller.getMethodName(), stackTrace);
    }

    /**
     * 获取当前堆栈信息
     *
     * @return
     */
    private StackTraceElement getCurrentStackTrace() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();

        int stackOffset = -1;
        for (int i = 5; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            if (Logg.class.equals(Logger.class) && i < trace.length - 1 && trace[i + 1].getClassName()
                    .equals(Logger.class.getName())) {
                continue;
            }
            if (e.getClassName().equals(Logg.class.getName())) {
                stackOffset = ++i;
            }
        }

        return stackOffset != -1 ? trace[stackOffset] : null;
    }

    @Override
    public LoggConfiguration getConfiguration() {
        return configuration;
    }
}