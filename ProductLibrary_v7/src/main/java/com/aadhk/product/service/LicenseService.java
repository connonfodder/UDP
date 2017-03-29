/*******************************************************************************
 * Copyright (C) Hong Kong Android Technology Co.
 * All right reserved.
 ******************************************************************************/
package com.aadhk.product.service;

import android.util.Log;

import com.aadhk.product.bean.AppUpdate;
import com.aadhk.product.bean.License;
import com.aadhk.product.library.BuildConfig;
import com.aadhk.product.util.BaseConstant;
import com.aadhk.product.util.GsonUtil;
import com.aadhk.product.util.OkHttpUtil;
import com.google.gson.Gson;

import org.acra.ACRA;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LicenseService {

	private static final String TAG = "LicenseService";

//	private String serverPath = "http://192.168.1.200:8080/aadhkUtil/";
//	private String serverPath = "http://54.174.39.17:8080/aadhkUtil/";
	private String serverPath = BuildConfig.LICENSE_SERVER;
	private OkHttpUtil okHttpUtil;

	public LicenseService() {
		okHttpUtil = OkHttpUtil.getInstance();
	}

	// 安裝
	public Map<String, Object> install(License license) {
		Map<String, Object> sendResult = new HashMap<String, Object>();
		try {
			Gson gson = new Gson();
			String json = gson.toJson(license);
			json = "{\"license\":" + json + "}";

			// send to server
			String url = serverPath + "licenseService/install.action";
						Log.e(TAG, "to server:" + url + json);

			String result = okHttpUtil.postJson(url, json);
						Log.e(TAG, "from server:" + result);

			if (GsonUtil.validateGson(result, "userName")) {
				License bean = gson.fromJson(result, License.class);
				sendResult.put(BaseConstant.SERVICE_STATUS, BaseConstant.STATUS_SUCCESS);
				sendResult.put(BaseConstant.SERVICE_DATA, bean);
			} else {
				sendResult.put(BaseConstant.SERVICE_STATUS, result);
			}
		} catch (IOException e) {
			e.printStackTrace();
			ACRA.getErrorReporter().handleException(e);
		}
		return sendResult;
	}

	// 註冊
	public Map<String, Object> register(License license) {
		Map<String, Object> sendResult = new HashMap<String, Object>();
		try {
			Gson gson = new Gson();
			String json = gson.toJson(license);
			json = "{\"license\":" + json + "}";

			// send to server
			String url = serverPath + "licenseService/register.action";

			 Log.i(TAG, "to server:"+json);

			String result = okHttpUtil.postJson(url, json);

			 Log.i(TAG, "from server:" + result);
			if (GsonUtil.validateGson(result, "userName")) {
				License bean = gson.fromJson(result, License.class);
				sendResult.put(BaseConstant.SERVICE_STATUS, BaseConstant.STATUS_SUCCESS);
				sendResult.put(BaseConstant.SERVICE_DATA, bean);
			} else {
				sendResult.put(BaseConstant.SERVICE_STATUS, result);
			}
		} catch (IOException e) {
			e.printStackTrace();
			ACRA.getErrorReporter().handleException(e);
		}
		return sendResult;
	}

	public Map<String, Object> checkLicense(String serialNumber) {
		Map<String, Object> sendResult = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("serialNumber", serialNumber);
			Gson gson = new Gson();
			String json = gson.toJson(map);

			// send to server
			String url = serverPath + "licenseService/fetch";

			// Log.i(TAG, "to server:" + json);

			String result = okHttpUtil.postJson(url, json);

			// Log.i(TAG, "from server:" + result);
			if (GsonUtil.validateGson(result, "userName")) {
				License bean = gson.fromJson(result, License.class);
				sendResult.put(BaseConstant.SERVICE_STATUS, BaseConstant.STATUS_SUCCESS);
				sendResult.put(BaseConstant.SERVICE_DATA, bean);
			} else {
				sendResult.put(BaseConstant.SERVICE_STATUS, result);
			}
		} catch (IOException e) {
			e.printStackTrace();
			ACRA.getErrorReporter().handleException(e);
		}
		return sendResult;
	}

	public Map<String, Object> updateVersion(String version, String item) {
		Map<String, Object> sendResult = new HashMap<String, Object>();
		try {
			Gson gson = new Gson();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("version", version);
			map.put("item", item);

			String json = gson.toJson(map);

			// send to server
			String url = serverPath + "appUpdateService/checkVersion";

			//	Log.i(TAG, "to server:" + json);
			String result = okHttpUtil.postJson(url, json);
			//	Log.i(TAG, "from server:" + result);

			if (GsonUtil.validateGson(result, "version")) {
				AppUpdate bean = gson.fromJson(result, AppUpdate.class);
				sendResult.put(BaseConstant.SERVICE_STATUS, BaseConstant.STATUS_SUCCESS);
				sendResult.put(BaseConstant.SERVICE_DATA, bean);
			} else if (GsonUtil.validateGson(result, "null")) {
				sendResult.put(BaseConstant.SERVICE_STATUS, BaseConstant.STATUS_SUCCESS);
				sendResult.put(BaseConstant.SERVICE_DATA, null);
			} else {
				sendResult.put(BaseConstant.SERVICE_STATUS, result);
			}
		} catch (IOException e) {
			e.printStackTrace();
			ACRA.getErrorReporter().handleException(e);
		}
		return sendResult;
	}
}
