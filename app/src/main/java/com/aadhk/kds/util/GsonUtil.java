package com.aadhk.kds.util;

public class GsonUtil {

	public static boolean validateGson(String json, String key) {
		return (json != null && (json.contains(key) || "[]".equals(json)));
	}

}
