package com.xuexibao.teacher.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author oldlu
 * 
 */
public class GsonUtil {

	private static Gson createGson() {
		GsonBuilder gb = new GsonBuilder();
		return gb.create();
	}

	public static <X> List<X> json2List(String json, Class<X> rawType) {
		List<X> result = new ArrayList<X>();
		JsonArray array = new JsonParser().parse(json).getAsJsonArray();
		Gson gson = createGson();
		for (JsonElement el : array) {
			if (el.isJsonPrimitive()) {
				result.add(gson.fromJson(el, rawType));
			} else if (el.isJsonObject()) {
				JsonObject obj = el.getAsJsonObject();
				result.add(gson.fromJson(obj, rawType));
			}
		}
		return result;
	}

	public static List<Map<String, String>> json2ListMap(String json) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		JsonArray array = new JsonParser().parse(json).getAsJsonArray();
		for (int i = 0; i < array.size(); i++) {
			result.add(json2Map(array.get(i).getAsJsonObject().toString()));
		}
		return result;
	}

	public static List<Long> json2LongList(String json) {
		return json2List(json, Long.class);
	}

	public static Map<String, String> json2Map(String json) {
		Map<String, String> result = createGson().fromJson(json, new TypeToken<HashMap<String, String>>() {
		}.getType());
		if (result == null)
			return new HashMap<String, String>();
		return result;
	}

	public static <X> X json2Object(String json, Class<X> clazz) {
		return createGson().fromJson(json, clazz);
	}

	public static String obj2Json(Object obj) {
		return createGson().toJson(obj);
	}

	public static Map<String, String> obj2Map(Object teacher) {
		return json2Map(obj2Json(teacher));
	}
}
