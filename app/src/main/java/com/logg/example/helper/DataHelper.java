package com.logg.example.helper;

import android.content.Context;
import android.content.Intent;

import com.logg.example.model.Child;
import com.logg.example.model.Man;
import com.logg.example.model.People;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DataHelper {

    /**
     * 数组
     *
     * @return
     */
    public static Man[] getArray() {
        Man[] man = {new Man(1, "男人名字", "男人地址", new Child(3, "孩子名字"))};
        return man;
    }

    /**
     * Intent
     *
     * @return
     */
    public static Intent getIntent() {
        Intent intent = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra("9527", "IntentValue");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        return intent;
    }



    /**
     * Map
     *
     * @return
     */
    public static Map<String, People> getMap() {
        Map<String, People> map = new HashMap<>();
        map.put("2", getPeople(2, "name1"));
        map.put("3", getPeople(3, "name2"));
        map.put("4", getPeople(4, "name3"));
        return map;
    }

    /**
     * 对象List
     *
     * @return
     */
    public static List<People> getList() {
        List<People> list = new ArrayList<>();
        list.add(getPeople(2, "name1"));
        list.add(getPeople(3, "name2"));
        list.add(getPeople(4, "name3"));
        return list;
    }

    /**
     * 获取对象
     *
     * @return
     */
    public static People getPeople(int id, String name) {
        return new People(id, name);
    }

    /**
     * Json测试
     *
     * @return
     */
    public static String getJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("a","b");
            jsonObject.put("aasasf","bag");
            jsonObject.put("aadg","badga");
            jsonObject.put("adg",true);

            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("a","b");
            jsonArray.put(jsonObject1);

            jsonObject1 = new JSONObject();
            jsonObject1.put("c","asfadged");
            jsonArray.put(jsonObject1);

            jsonObject1 = new JSONObject();
            jsonObject1.put("a",1);
            jsonArray.put(jsonObject1);

            jsonObject1 = new JSONObject();
            jsonObject1.put("r",3.141586521156);
            jsonArray.put(jsonObject1);

            jsonObject.put("array",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    /**
     * JsonArray测试
     *
     * @return
     */
    public static String getJsonArray() {
        JSONArray jsonArray = new JSONArray();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("a","b");
            jsonArray.put(jsonObject);

            jsonObject = new JSONObject();
            jsonObject.put("c","asfadged");
            jsonArray.put(jsonObject);

            jsonObject = new JSONObject();
            jsonObject.put("a",1);
            jsonArray.put(jsonObject);

            jsonObject = new JSONObject();
            jsonObject.put("r",3.141586521156);
            jsonArray.put(jsonObject);

            jsonObject = new JSONObject();
            jsonObject.put("w",true);
            jsonArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArray.toString();
    }

    /**
     * XML测试
     *
     * @return
     */
    public static String getXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Pensons><Penson id=\"1\"><name>name</name><sex>男</sex><age>30</age></Penson><Penson id=\"2\"><name>name</name><sex>女</sex><age>27</age></Penson></Pensons>";
    }

    /**
     * 大文本
     *
     * @param context
     * @return
     */
    public static String getBigString(Context context) {
        try {
            InputStream is = context.getAssets().open("city.json");
            StringBuffer sb = new StringBuffer();
            int len = -1;
            byte[] buf = new byte[is.available()];
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len, "utf-8"));
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


}