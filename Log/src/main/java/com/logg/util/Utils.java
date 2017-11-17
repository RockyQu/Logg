/*
 * Copyright 2016-2018 DesignQu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.logg.util;

import com.logg.config.LoggConstant;
import com.logg.parser.Parser;
import com.logg.config.LoggConfiguration;
import com.logg.printer.manager.PrinterManager;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ObjectUtil
 */
public class Utils {

    public Utils() {
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
        LoggConfiguration loggConfig = PrinterManager.getInstance().getConfiguration();
        if (loggConfig != null) {
            for (Parser parser : loggConfig.getParsers()) {
                if (parser.parseClassType().isAssignableFrom(object.getClass())) {
                    return parser.parseString(object);
                }
            }
        }

        // 是否是数组
        if (Utils.isArray(object)) {
            return Utils.parseArray(object);
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
    private static void iterateClassFields(Class cla, StringBuilder builder, Object o, boolean isSubClass, int childOffset) {
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

    /**
     * 超大文本字符串转化为 List
     *
     * @param message
     * @return
     */
    public static List<String> bigStringToList(String message) {
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

    /**
     * 是否为数组
     *
     * @param object
     * @return
     */
    public static boolean isArray(Object object) {
        return object.getClass().isArray();
    }

    /**
     * 获取数组的维度
     *
     * @param object
     * @return
     */
    private static int getArrayDimension(Object object) {
        int dim = 0;
        for (int i = 0; i < object.toString().length(); ++i) {
            if (object.toString().charAt(i) == '[') {
                ++dim;
            } else {
                break;
            }
        }
        return dim;
    }

    /**
     * 获取数组类型
     *
     * @param object
     * @return
     */
    private static char getType(Object object) {
        if (isArray(object)) {
            String str = object.toString();
            return str.substring(str.lastIndexOf("[") + 1, str.lastIndexOf("[") + 2).charAt(0);
        }
        return 0;
    }

    /**
     * 遍历数组
     *
     * @param result
     * @param array
     */
    private static void traverseArray(StringBuilder result, Object array) {
        if (getArrayDimension(array) == 1) {
            switch (getType(array)) {
                case 'I':
                    result.append(Arrays.toString((int[]) array));
                    break;
                case 'D':
                    result.append(Arrays.toString((double[]) array));
                    break;
                case 'Z':
                    result.append(Arrays.toString((boolean[]) array));
                    break;
                case 'B':
                    result.append(Arrays.toString((byte[]) array));
                    break;
                case 'S':
                    result.append(Arrays.toString((short[]) array));
                    break;
                case 'J':
                    result.append(Arrays.toString((long[]) array));
                    break;
                case 'F':
                    result.append(Arrays.toString((float[]) array));
                    break;
                case 'L':
                    Object[] objects = (Object[]) array;
                    result.append("[");

                    for (int i = 0; i < objects.length; i++) {
                        result.append(Utils.objectToString(objects[i]));
                        if (i != objects.length - 1) {
                            result.append(",");
                        }
                    }
                    result.append("]");
                    break;
                default:
                    result.append(Arrays.toString((Object[]) array));
                    break;
            }
        } else {
            result.append("[");
            for (int i = 0; i < ((Object[]) array).length; i++) {
                traverseArray(result, ((Object[]) array)[i]);
                if (i != ((Object[]) array).length - 1) {
                    result.append(",");
                }
            }
            result.append("]");
        }
    }

    /**
     * 将数组内容转化为字符串
     *
     * @param array
     * @return
     */
    public static String parseArray(Object array) {
        StringBuilder result = new StringBuilder();
        traverseArray(result, array);
        return result.toString();
    }
}