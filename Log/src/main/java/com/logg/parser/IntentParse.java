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

package com.logg.parser;

import android.content.Intent;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Intent
 */
public class IntentParse implements Parser<Intent> {

    private static Map<Integer, String> flagMap = new HashMap<>();

    static {
        Class cla = Intent.class;
        Field[] fields = cla.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().startsWith("FLAG_")) {
                int value = 0;
                try {
                    Object object = field.get(cla);
                    if (object instanceof Integer || object.getClass().getSimpleName().equals("int")) {
                        value = (int) object;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (flagMap.get(value) == null) {
                    flagMap.put(value, field.getName());
                }
            }
        }
    }

    @Override
    public Class<Intent> parseClassType() {
        return Intent.class;
    }

    @Override
    public String parseString(Intent intent) {
        StringBuilder builder = new StringBuilder(parseClassType().getSimpleName() + " [" + LINE_SEPARATOR);
        builder.append(String.format("%s = %s" + LINE_SEPARATOR, "Scheme", intent.getScheme()));
        builder.append(String.format("%s = %s" + LINE_SEPARATOR, "Action", intent.getAction()));
        builder.append(String.format("%s = %s" + LINE_SEPARATOR, "DataString", intent.getDataString()));
        builder.append(String.format("%s = %s" + LINE_SEPARATOR, "Type", intent.getType()));
        builder.append(String.format("%s = %s" + LINE_SEPARATOR, "Package", intent.getPackage()));
        builder.append(String.format("%s = %s" + LINE_SEPARATOR, "ComponentInfo", intent.getComponent()));
        builder.append(String.format("%s = %s" + LINE_SEPARATOR, "Flags", getFlags(intent.getFlags())));
        builder.append(String.format("%s = %s" + LINE_SEPARATOR, "Categories", intent.getCategories()));
        builder.append(String.format("%s = %s" + LINE_SEPARATOR, "Extras", new BundleParse().parseString(intent.getExtras())));
        return builder.toString() + "]";
    }

    /**
     * 获取flag的值
     *
     * @param flags
     * @return
     */
    private String getFlags(int flags) {
        StringBuilder builder = new StringBuilder();
        for (int flagKey : flagMap.keySet()) {
            if ((flagKey & flags) == flagKey) {
                builder.append(flagMap.get(flagKey));
                builder.append(" | ");
            }
        }
        if (TextUtils.isEmpty(builder.toString())) {
            builder.append(flags);
        } else if (builder.indexOf("|") != -1) {
            builder.delete(builder.length() - 2, builder.length());
        }
        return builder.toString();
    }
}