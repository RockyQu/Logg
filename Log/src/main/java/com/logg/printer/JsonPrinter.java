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

package com.logg.printer;

import com.logg.config.LoggConfiguration;

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
        String json;
        try {
            if (object.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(object);
                json = jsonObject.toString(JSON_INDENT);
            } else if (object.startsWith("{")) {
                JSONArray jsonArray = new JSONArray(object);
                json = jsonArray.toString(JSON_INDENT);
            } else {
                json = object;
            }
        } catch (JSONException e) {
            json = object;
        }

        super.printer(type, tag, json);
    }
}
