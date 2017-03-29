/*******************************************************************************
 * Copyright (C) Hong Kong Android Technology Co.
 * All right reserved.
 ******************************************************************************/
package com.aadhk.product.util;

public class GsonUtil {

	public static boolean validateGson(String json, String key) {
		return (json != null && (json.contains(key) || "[]".equals(json) || "{}".equals(json)));
	}

}
