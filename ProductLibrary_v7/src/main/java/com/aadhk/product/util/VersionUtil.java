/*******************************************************************************
 * Copyright (C) Hong Kong Android Technology Co.
 * All right reserved.
 ******************************************************************************/
package com.aadhk.product.util;

import org.acra.ACRA;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.preference.PreferenceManager;

/**
 * @author Administrator
 * @name ChangeLog
 * @description
 * @date 2015年8月3日
 */
public class VersionUtil {

	private final Context context;
	private String oldVersion, thisVersion;
	private SharedPreferences sp;

	// this is the key for storing the version name in SharedPreferences
	private static final String VERSION_KEY = "PREFS_VERSION_KEY";
	private Resources resources;

	/**
	 * Constructor
	 * 
	 * Retrieves the version names and stores the new version name in SharedPreferences
	 */
	public VersionUtil(Context context) {
		this.context = context;
		this.sp = PreferenceManager.getDefaultSharedPreferences(context);
		resources = context.getResources();

		try {
			oldVersion = sp.getString(VERSION_KEY, "");
			thisVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;

		} catch (NameNotFoundException e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}
	}

	public void setManifestVersion() {
		// save new version number to preferences
		SharedPreferences.Editor editor = sp.edit();
		editor.putString(VERSION_KEY, thisVersion);
		editor.commit();
	}

	public String getSavedVersion() {
		return this.oldVersion;
	}

	public String getManifestVersion() {
		return this.thisVersion;
	}

	public boolean isChange() {
		return !oldVersion.equals(thisVersion);
	}

	public boolean firstRun() {
		return "".equals(oldVersion);
	}
}
