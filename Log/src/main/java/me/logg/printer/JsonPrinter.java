/*
 * Copyright 2016-2018 RockyQu
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

package me.logg.printer;

import me.logg.config.LoggConfiguration;
import me.logg.config.LoggConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Json Printer
 */
public class JsonPrinter extends DefaultPrinter {

    /**
     * 缩进量
     */
    private static final int JSON_INDENT = 4;

    public JsonPrinter(LoggConfiguration configuration) {
        super(configuration);
    }

    @Override
    public void printer(Type type, String tag, String object) {
        StringBuilder json = new StringBuilder();
        json.append(LoggConstant.SPACE).append(LoggConstant.BR);

        try {
            if (object.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(object);
                json.append(jsonObject.toString(JSON_INDENT));
            } else if (object.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(object);
                json.append(jsonArray.toString(JSON_INDENT));
            } else {
                json.append(object);
            }
        } catch (JSONException e) {
            json.append(object);
        }

        super.printer(type, tag, json.toString());
    }
}