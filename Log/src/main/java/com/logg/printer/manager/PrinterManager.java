package com.logg.printer.manager;

import android.text.TextUtils;

import com.logg.Logg;
import com.logg.config.LoggConfig;
import com.logg.config.LoggConstant;
import com.logg.printer.DefaultPrinter;
import com.logg.printer.JsonPrinter;
import com.logg.printer.Type;
import com.logg.printer.XmlPrinter;
import com.logg.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * 日志管理器
 */
public class PrinterManager {

    // PrinterManager
    private static PrinterManager printerManager = null;

    // Default Printer
    private DefaultPrinter defaultPrinter = null;
    // Json Printer
    private JsonPrinter jsonPrinter = null;
    // XML Printer
    private XmlPrinter xmlPrinter = null;

    /**
     * Parameter configuration
     */
    private LoggConfig setting = null;

    // 一个细粒度自定义前缀Tag
    private String tag;

    /**
     * 提供全局回调接口，如果你设置了此接口，在不影响底层输出的情况下，所有的Log都会回调到这个接口里
     * 注意控制集合的数量，避免存放过多接口而导致性能上的问题
     * 注意：不要在 LoggListener 接口的 logg 方法里再调用 Logg 会造成死循环
     * <p>
     * 使用方法
     * Add {@link PrinterManager#addListeners(LoggListener)}
     * Remove {@link PrinterManager#removeListener(LoggListener)}
     * Clear {@link PrinterManager#clearListeners()}
     */
    private List<LoggListener> listeners = new ArrayList<>();

    public PrinterManager() {
        defaultPrinter = new DefaultPrinter(listeners);
        jsonPrinter = new JsonPrinter(listeners);
        xmlPrinter = new XmlPrinter(listeners);

        setting = LoggConfig.getConfig();
    }

    public static synchronized PrinterManager get() {
        if (printerManager == null) {
            synchronized (PrinterManager.class) {
                if (printerManager == null) {
                    printerManager = new PrinterManager();
                }
            }
        }
        return printerManager;
    }

    /**
     * @param tag
     */
    public PrinterManager tag(String tag) {
        this.tag = tag;
        return this;
    }

    /**
     * @param object
     */
    public void v(Object object) {
        this.printer(Type.V, object);
    }

    /**
     * @param object
     */
    public void d(Object object) {
        this.printer(Type.D, object);
    }

    /**
     * @param object
     */
    public void i(Object object) {
        this.printer(Type.I, object);
    }

    /**
     * @param object
     */
    public void w(Object object) {
        this.printer(Type.W, object);
    }

    /**
     * @param object
     */
    public void e(Object object) {
        this.printer(Type.E, object);
    }

    /**
     * @param object
     */
    public void wtf(Object object) {
        this.printer(Type.WTF, object);
    }

    /**
     * Json
     *
     * @param object
     */
    public void json(Object object) {
        this.printer(Type.J, object);
    }

    /**
     * XML
     *
     * @param object
     */
    public void xml(Object object) {
        this.printer(Type.X, object);
    }

    /**
     * 打印字符串
     *
     * @param type
     * @param object
     */
    private synchronized void printer(Type type, Object object) {
        // 是否允许日志输出
        if (setting != null && !setting.isDebug()) {
            return;
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
                        defaultPrinter.printer(type, getTag(), subMsg);
                    }
                    return;
                } else {
                    defaultPrinter.printer(type, getTag(), o);
                }
                break;
            case J:
                jsonPrinter.printer(type, getTag(), ObjectUtil.objectToString(object));
                break;
            case X:
                xmlPrinter.printer(type, getTag(), ObjectUtil.objectToString(object));
                break;
            default:
                break;
        }

        tag = null;
    }

    /**
     * 拼接Tag前缀信息
     *
     * @return
     */
    private String getTag() {
        if (setting != null) {
            if (!TextUtils.isEmpty(tag)) {
                return tag;
            } else if (!TextUtils.isEmpty(setting.getTag())) {
                return setting.getTag();
            } else {
                return spliceTag();
            }
        } else {
            return spliceTag();
        }
    }

    /**
     * 拼接Tag前缀信息
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
     * 超大文本字符串转化为List
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

    public interface LoggListener {
        void logg(Type type, String tag, String message);
    }

    public void addListeners(LoggListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(LoggListener listener) {
        this.listeners.remove(listener);
    }

    public void clearListeners() {
        this.listeners.clear();
    }
}