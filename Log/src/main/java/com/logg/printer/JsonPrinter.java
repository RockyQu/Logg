package com.logg.printer;

import com.logg.config.LoggConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Json Printer
 */
public class JsonPrinter extends DefaultPrinter {

    public JsonPrinter(LoggConfiguration configuration) {
        super(configuration);
    }

    /**
     * 缩进量
     */
    private static final int JSON_INDENT = 4;

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
