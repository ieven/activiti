package org.activiti.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JSONHelper {

    private JSONHelper() {

    }

    public static String toString(Object obj, boolean isEncodeJSONString) {

        if (null == obj)
            return null;

        String str;

        if (String.class.isAssignableFrom(obj.getClass())) {
            str = (String) obj;
        }
        else {
            str = JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect);
        }

        if (isEncodeJSONString == true) {
            str = str.replace("\"", "\\\"");
        }

        return str;
    }

    public static String toString(Object obj) {

        return toString(obj, false);
    }

    public static <T> T toObject(String jsonString, Class<T> c) {

        return toObject(jsonString, c, true);
    }

    public static <T> T toObject(String jsonString, Class<T> c, boolean isOrderFields) {

        if (null == c || StringHelper.isEmpty(jsonString)) {
            return null;
        }

        return JSON.parseObject(jsonString, c, Feature.OrderedField);
    }

    public static <T> List<T> toObjectArray(String jsonString, Class<T> c) {

        if (null == c || StringHelper.isEmpty(jsonString)) {
            return Collections.emptyList();
        }

        return JSON.parseArray(jsonString, c);
    }

    public static Object convertJO2POJO(Object data) {

        Class<?> dCls = data.getClass();

        if (JSONObject.class.isAssignableFrom(dCls)) {

            Map<String, Object> m = new LinkedHashMap<String, Object>();

            JSONObject jod = (JSONObject) data;

            for (String key : jod.keySet()) {

                Object attr = jod.get(key);

                Object attrObj = convertJO2POJO(attr);

                m.put(key, attrObj);
            }

            return m;

        }
        else if (JSONArray.class.isAssignableFrom(dCls)) {

            List<Object> l = new ArrayList<Object>();

            JSONArray joa = (JSONArray) data;

            for (Object o : joa) {

                Object attrObj = convertJO2POJO(o);

                l.add(attrObj);
            }

            return l;

        }

        return data;
    }
}
