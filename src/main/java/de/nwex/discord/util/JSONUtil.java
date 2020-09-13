package de.nwex.discord.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONUtil {

    public static List<JSONObject> list(JSONArray array) {
        List<JSONObject> list = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            list.add(array.getJSONObject(i));
        }

        return list;
    }

    public static List<Object> listAny(JSONArray array) {
        List<Object> list = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            list.add(array.get(i));
        }

        return list;
    }

    public static Stream<JSONObject> stream(JSONArray array) {
        return list(array).stream();
    }

    public static Stream<Object> streamAny(JSONArray array) {
        return listAny(array).stream();
    }
}
