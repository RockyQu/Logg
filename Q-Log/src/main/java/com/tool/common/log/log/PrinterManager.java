package com.tool.common.log.log;

import android.text.TextUtils;

import com.tool.common.log.QLog;
import com.tool.common.log.common.Constant;
import com.tool.common.log.common.Setting;
import com.tool.common.log.log.printer.DefaultPrinter;
import com.tool.common.log.log.printer.JsonPrinter;
import com.tool.common.log.log.printer.Type;
import com.tool.common.log.log.printer.XmlPrinter;
import com.tool.common.log.util.ObjectUtil;
import com.tool.common.log.util.Util;

import java.util.logging.Logger;

import static com.tool.common.log.log.printer.Type.J;
import static com.tool.common.log.log.printer.Type.X;

/**
 * 日志打印管理器
 */
public class PrinterManager {

    //Default Printer
    private DefaultPrinter defaultPrinter = null;
    //Json Printer
    private JsonPrinter jsonPrinter = null;
    //XML Printer
    private XmlPrinter xmlPrinter = null;

    /**
     * 参数配置
     */
    private Setting setting = null;

    public PrinterManager() {
        defaultPrinter = new DefaultPrinter();
        jsonPrinter = new JsonPrinter();
        xmlPrinter = new XmlPrinter();

        setting = Setting.getInstance().addParserClass(Constant.DEFAULT_PARSER_CLASS);
    }

    /**
     * 日志输出-#BBBBBB
     *
     * @param object
     */
    public void v(Object object) {
        this.printer(Type.V, object);
    }

    /**
     * 日志输出-#0070BB
     *
     * @param object
     */
    public void d(Object object) {
        this.printer(Type.D, object);
    }

    /**
     * 日志输出-#48BB31
     *
     * @param object
     */
    public void i(Object object) {
        this.printer(Type.I, object);
    }

    /**
     * 日志输出-# BBBB23
     *
     * @param object
     */
    public void w(Object object) {
        this.printer(Type.W, object);
    }

    /**
     * 日志输出-#FF0006
     *
     * @param object
     */
    public void e(Object object) {
        this.printer(Type.E, object);
    }

    /**
     * 日志输出-#8F0005
     *
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
        this.printer(J, object);
    }

    /**
     * XML
     *
     * @param object
     */
    public void xml(Object object) {
        this.printer(X, object);
    }

    /**
     * 打印字符串
     *
     * @param type
     * @param object
     */
    private synchronized void printer(Type type, Object object) {

        if (!setting.isDebug()) {//是否允许日志输出
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
                if (o.length() > Constant.LINE_MAX) {
                    for (String subMsg : Util.bigStringToList(o)) {
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
    }

    /**
     * 拼装Tag信息
     *
     * @return Tag information
     */
    private String getTag() {
        if (TextUtils.isEmpty(setting.getTag())) {
            StackTraceElement caller = getCurrentStackTrace();
            if (caller == null) {
                return "";
            }
            String stackTrace = caller.toString();
            stackTrace = stackTrace.substring(stackTrace.lastIndexOf('('), stackTrace.length());
            String callerClazzName = caller.getClassName();
            callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
            return String.format("%s.%s%s", callerClazzName, caller.getMethodName(), stackTrace);
        } else {
            return setting.getTag();
        }
    }

    /**
     * 获取当前堆栈信息
     *
     * @return StackTraceElement
     */
    private StackTraceElement getCurrentStackTrace() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();

        int stackOffset = -1;

        for (int i = 5; i < trace.length; i++) {
            StackTraceElement e = trace[i];

            if (QLog.class.equals(Logger.class) && i < trace.length - 1 && trace[i + 1].getClassName()
                    .equals(Logger.class.getName())) {
                continue;
            }
            if (e.getClassName().equals(QLog.class.getName())) {
                stackOffset = ++i;
            }
        }

        return stackOffset != -1 ? trace[stackOffset] : null;
    }
}