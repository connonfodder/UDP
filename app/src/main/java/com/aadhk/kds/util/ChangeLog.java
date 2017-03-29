/*******************************************************************************
 * Copyright (C) Hong Kong Android Technology Co.
 * All right reserved.
 ******************************************************************************/
package com.aadhk.kds.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.acra.ACRA;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import com.aadhk.kds.R;
import com.aadhk.kds.view.ChangeLogDialog;


/**
 * @author Administrator
 * @name ChangeLog
 * @description
 * @date 2015年8月3日
 */
public class ChangeLog {

	private final Context context;
	private String oldVersion, thisVersion;
	private SharedPreferences sp;

	// this is the key for storing the version name in SharedPreferences
	private static final String VERSION_KEY = "PREFS_VERSION_KEY";
	private static final String TAG = "ChangeLog";

	private Listmode listMode = Listmode.NONE;
	private StringBuffer contentBuffer;
	private StringBuffer templateBuffer;
	private static final String EOVS = "END_OF_CHANGE_LOG";
	private static final String CLC = "CHANGE_LOG_CONTENT";
	private Resources resources;

	/**
	 * Constructor
	 * 
	 * Retrieves the version names and stores the new version name in SharedPreferences
	 */
	public ChangeLog(Context context) {
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

	/**
	 * @return true if this version of your app is started the first time
	 */
	public boolean showChangeLog() {
		return !oldVersion.equals(thisVersion);
	}

	public boolean firstRun() {
		return "".equals(oldVersion);
	}

	/**
	 * @return an AlertDialog displaying the changes since the previous installed version of your app (what's new).
	 */
	public Dialog getLogDialog() {
		return this.getDialog(false);
	}

	/**
	 * @return an AlertDialog with a full change log displayed
	 */
	public Dialog getFullLogDialog() {
		return this.getDialog(true);
	}

	private Dialog getDialog(boolean full) {

		/*
		 * WebView wv = new WebView(this.context); wv.setBackgroundColor(0); // transparent wv.loadData(this.getLog(full), "text/html", "UTF-8");
		 */

		ChangeLogDialog msgDialog = new ChangeLogDialog(context);
		msgDialog.setTitle(R.string.prefLogTitle);
		msgDialog.setCustomView(this.getLog(full));

		return msgDialog;
		/*
		 * AlertDialog.Builder builder = new AlertDialog.Builder(this.context); builder.setTitle(resources.getString(full ? R.string.changelog_full_title : R.string.changelog_title)).setView(wv).setCancelable(false).setPositiveButton(resources.getString(R.string.btnOk), new DialogInterface.OnClickListener() { public void onClick(DialogInterface dialog, int id) { dialog.cancel(); } }); return builder.create();
		 */
	}

	/**
	 * @return HTML displaying the changes since the previous installed version of your app (what's new)
	 */
	public String getLog() {
		return this.getLog(false);
	}

	/**
	 * @return HTML which displays full change log
	 */
	public String getFullLog() {
		return this.getLog(true);
	}

	/** modes for HTML-Lists (bullet, numbered) */
	private enum Listmode {
		NONE, ORDERED, UNORDERED,
	}

	private String getLog(boolean full) {

		contentBuffer = new StringBuffer();
		templateBuffer = new StringBuffer();
		InputStream ins = null;

		// read changelog.txt file
		try {
			ins = resources.openRawResource(R.raw.changelog);
			BufferedReader br = new BufferedReader(new InputStreamReader(ins));

			String line = null;
			boolean advanceToEOVS = false; // true = ignore further version sections
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.startsWith("$")) {
					// begin of a version section
					this.closeList();
					String version = line.substring(1).trim();
					// stop output?
					if (!full) {
						if (this.oldVersion.equals(version)) {
							advanceToEOVS = false;
						} else if (version.equals(EOVS)) {
							advanceToEOVS = true; // skip content
						}
					}
				} else if (!advanceToEOVS) {
					if (line.startsWith("%")) {
						// line contains version title
						this.closeList();
						contentBuffer.append("<div class='title'>" + line.substring(1).trim() + "</div>\n");
					} else if (line.startsWith("_")) {
						// line contains date
						this.closeList();
						contentBuffer.append("<div class='subtitle'>" + line.substring(1).trim() + "</div>\n");
					} else if (line.startsWith("!")) {
						// line contains free text
						this.closeList();
						contentBuffer.append("<div class='freetext'>" + line.substring(1).trim() + "</div>\n");
					} else if (line.startsWith("#")) {
						// line contains numbered list item
						this.openList(Listmode.ORDERED);
						contentBuffer.append("<li>" + line.substring(1).trim() + "</li>\n");
					} else if (line.startsWith("*")) {
						// line contains bullet list item
						this.openList(Listmode.UNORDERED);
						contentBuffer.append("<li>" + line.substring(1).trim() + "</li>\n");
					} else {
						// no special character: just use line as is
						this.closeList();
						contentBuffer.append(line + "\n");
					}
				}
			}
			this.closeList();
			br.close();

			// get template
			InputStream insTemplate = resources.openRawResource(R.raw.changelogtemplate);
			br = new BufferedReader(new InputStreamReader(insTemplate));

			line = null;
			while ((line = br.readLine()) != null) {
				if (line.indexOf(CLC) > -1) {
					templateBuffer.append(contentBuffer);
				} else {
					templateBuffer.append(line + "\n");
				}
			}
			br.close();
		} catch (IOException e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}

		return templateBuffer.toString();
	}

	private void openList(Listmode listMode) {
		if (this.listMode != listMode) {
			closeList();
			if (listMode == Listmode.ORDERED) {
				contentBuffer.append("<div class='list'><ol>\n");
			} else if (listMode == Listmode.UNORDERED) {
				contentBuffer.append("<div class='list'><ul>\n");
			}
			this.listMode = listMode;
		}
	}

	private void closeList() {
		if (this.listMode == Listmode.ORDERED) {
			contentBuffer.append("</ol></div>\n");
		} else if (this.listMode == Listmode.UNORDERED) {
			contentBuffer.append("</ul></div>\n");
		}
		this.listMode = Listmode.NONE;
	}
}
