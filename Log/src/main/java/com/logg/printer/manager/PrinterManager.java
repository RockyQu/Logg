package com.logg.printer.manager;

import android.text.TextUtils;

import com.logg.Logg;
import com.logg.config.LoggConfig;
import com.logg.config.LoggConstant;
import com.logg.interceptor.LoggInterceptor;
import com.logg.printer.DefaultPrinter;
import com.logg.printer.JsonPrinter;
import com.logg.printer.Printer;
import com.logg.printer.Type;
import com.logg.printer.XmlPrinter;
import com.logg.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * 日志管理器
 */
public class PrinterManager implements IPrinterManager {

    // PrinterManager
    private static IPrinterManager printerManager = null;

    // Default Printer
    private Printer defaultPrinter = null;
    // Json Printer
    private Printer jsonPrinter = null;
    // XML Printer
    private Printer xmlPrinter = null;

    /**
     * LoggInterceptors
     */
    private final List<LoggInterceptor> interceptors = new ArrayList<>();

    /**
     * Parameter configuration
     */
    private LoggConfig setting = null;

    public PrinterManager() {
        defaultPrinter = new DefaultPrinter();
        jsonPrinter = new JsonPrinter();
        xmlPrinter = new XmlPrinter();

        setting = LoggConfig.getConfig();
    }

    public static synchronized IPrinterManager get() {
        if (printerManager == null) {
            synchronized (PrinterManager.class) {
                if (printerManager == null) {
                    printerManager = new PrinterManager();
                }
            }
        }
        return printerManager;
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
    public void addInterceptor(LoggInterceptor interceptor) {
        if (interceptor != null) {
            interceptors.add(interceptor);
        }
    }

    @Override
    public void removeInterceptor(LoggInterceptor interceptor) {
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
        if (setting != null && !setting.isDebug()) {
            return;
        }

        for (LoggInterceptor interceptor : interceptors) {
            if (interceptor.isLoggable()) {
                interceptor.proceed(type, tag, object);
            }
        }

        switch (type) {
            case V:
            case D:
            case I:
            case W:
            case E:
            case WTF:
                String o = ObjectUtil.objectToString(object);
                if (o.length() > LoggConstant.LINE_MAX) {
                    for (String subMsg : bigStringToList(o)) {
                        defaultPrinter.printer(type, tag, subMsg);
                    }
                    return;
                } else {
                    defaultPrinter.printer(type, tag, o);
                }
                break;
            case J:
                jsonPrinter.printer(type, tag, ObjectUtil.objectToString(object));
                break;
            case X:
                xmlPrinter.printer(type, tag, ObjectUtil.objectToString(object));
                break;
            default:
                defaultPrinter.printer(type, tag, ObjectUtil.objectToString(object));
                break;
        }
    }

    /**
     * 拼接 Tag 前缀信息
     *
     * @return
     */
    private String getTag() {
        if (setting != null) {
            if (!TextUtils.isEmpty(setting.getTag())) {
                return setting.getTag();
            } else {
                return spliceTag();
            }
        } else {
            return spliceTag();
        }
    }

    /**
     * 拼接 Tag 前缀信息
     *
     * @return
     */
    private String spliceTag() {
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

    /**
     * 超大文本字符串转化为 List
     *
     * @param message
     * @return
     */
    private List<String> bigStringToList(String message) {
        List<String> stringList = new ArrayList<>();
        int index = 0;
        int maxLength = LoggConstant.LINE_MAX;
        int countOfSub = message.length() / maxLength;
        if (countOfSub > 0) {
            for (int i = 0; i < countOfSub; i++) {
                String sub = message.substring(index, index + maxLength);
                stringList.add(sub);
                index += maxLength;
            }
            stringList.add(message.substring(index, message.length()));
        } else {
            stringList.add(message);
        }
        return stringList;
    }
}