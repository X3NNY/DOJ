package pers.dreamer.oj.judge.util;

import com.alibaba.fastjson.JSONObject;

import java.io.File;

public class JSONUtil {
    public static String getJSONString(String path) {
        File file = new File(path);
        if (!file.exists()) return "";
        return NioUtil.readNIO(path);
    }

    public static JSONObject loadJSON(String json) {
        return JSONObject.parseObject(json);
    }

    public static String toJSONString(JSONObject json) {
        return JSONObject.toJSONString(json);
    }
}
