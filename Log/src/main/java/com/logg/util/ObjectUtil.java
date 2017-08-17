package com.logg.util;

import com.logg.config.LoggConstant;
import com.logg.parser.Parser;
import com.logg.config.LoggConfig;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * ObjectUtil
 */
public class ObjectUtil {

    public ObjectUtil() {
        throw new AssertionError();
    }

    /**
     * Object to String
     *
     * @param object
     * @return
     */
    public static String objectToString(Object object) {
        return objectToString(object, 0);
    }

    /**
     * 是否为静态内部类
     *
     * @param cls
     * @return
     */
    private static boolean isStaticInnerClass(Class cls) {
        if (cls != null && cls.isMemberClass()) {
            int modifiers = cls.getModifiers();
            if ((modifiers & Modifier.STATIC) == Modifier.STATIC) {
                return true;
            }
        }
        return false;
    }

    /**
     * Object to String
     *
     * @param object
     * @param childLevel
     * @return
     */
    private static String objectToString(Object object, int childLevel) {
        if (object == null) {
            return LoggConstant.NULL;
        }

        if (childLevel > LoggConstant.MAX_LEVEL) {
            return object.toString();
        }

        // 自定义解析类判断
        LoggConfig loggConfig = LoggConfig.getConfig();
        if (loggConfig != null) {
            for (Parser parser : loggConfig.getParsers()) {
                if (parser.parseClassType().isAssignableFrom(object.getClass())) {
                    return parser.parseString(object);
                }
            }
        }

        // 是否是数组
        if (ArrayUtil.isArray(object)) {
            return ArrayUtil.parseArray(object);
        }

        if (object.toString().startsWith(object.getClass().getName() + "@")) {
            StringBuilder builder = new StringBuilder();
            iterateClassFields(object.getClass(), builder, object, false, childLevel);
            Class superClass = object.getClass().getSuperclass();
            while (!superClass.equals(Object.class)) {
                iterateClassFields(superClass, builder, object, true, childLevel);
                superClass = superClass.getSuperclass();
            }
            return builder.toString();
        }

        return object.toString();
    }

    /**
     * 拼接字段和值
     *
     * @param cla
     * @param builder
     * @param o           对象
     * @param isSubClass  是否为子class
     * @param childOffset 递归解析属性的层级
     */
    private static void iterateClassFields(Class cla, StringBuilder builder, Object o, boolean isSubClass,
                                           int childOffset) {
        if (cla.equals(Object.class)) {
            return;
        }

        if (isSubClass) {
            builder.append(LoggConstant.BR + "=> ");
        }

        String breakLine = "";
        builder.append(" " + cla.getSimpleName() + " {");
        Field[] fields = cla.getDeclaredFields();
        for (int i = 0; i < fields.length; ++i) {
            Field field = fields[i];
            field.setAccessible(true);
            if (cla.isMemberClass() && !isStaticInnerClass(cla) && i == 0) {
                continue;
            }
            Object subObject = null;
            try {
                subObject = field.get(o);
            } catch (IllegalAccessException e) {
                subObject = e;
            } finally {
                if (subObject != null) {
                    // 解决Instant Run情况下内部类死循环的问题
                    if (!isStaticInnerClass(cla) && (field.getName().equals("$change") || field.getName().equalsIgnoreCase("this$0"))) {
                        continue;
                    }
                    if (subObject instanceof String) {
                        subObject = "\"" + subObject + "\"";
                    } else if (subObject instanceof Character) {
                        subObject = "\'" + subObject + "\'";
                    }
                    if (childOffset < LoggConstant.MAX_LEVEL) {
                        subObject = objectToString(subObject, childOffset + 1);
                    }
                }
                String formatString = breakLine + "%s = %s, ";
                builder.append(String.format(formatString, field.getName(), subObject == null ? "null" : subObject.toString()));
            }
        }
        if (builder.toString().endsWith("{")) {
            builder.append("}");
        } else {
            builder.replace(builder.length() - 2, builder.length() - 1, breakLine + "}");
        }
    }
}
