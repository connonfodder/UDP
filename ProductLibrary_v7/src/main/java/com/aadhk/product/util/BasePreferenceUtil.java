package com.aadhk.product.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

import com.aadhk.product.bean.License;

import java.util.Calendar;

public class BasePreferenceUtil {

	protected SharedPreferences preference;

	public BasePreferenceUtil(Context context) {
		preference = PreferenceManager.getDefaultSharedPreferences(context);
	}

	public boolean useAutoBackupSD() {
		return preference.getBoolean(BaseConstant.PREF_AUTOBACKUP_SD, BaseConstant.PRE_DEF_AUTOBACKUP_SD);
	}

	public boolean useAutoBackupDropbox() {
		return preference.getBoolean(BaseConstant.PREF_AUTODROPBOXBACKUP, BaseConstant.PRE_DEF_AUTOBACKUP_DROPBOX);
	}
	
	public String getDateFormat() {
		return preference.getString(BaseConstant.PREF_DATE_FORMAT, BaseConstant.PREF_DEF_DATE);
	}
	
	public String getTimeFormat() {
		return preference.getString(BaseConstant.PREF_TIME, BaseConstant.TIME_FORMAT_24);
	}

	public String getTimeFormat24(boolean isFullFormat) {
		return preference.getString(BaseConstant.PREF_TIME, isFullFormat  ? BaseConstant.TIME_FORMAT_24 :  BaseConstant.TIME_FORMAT_12);
	}
	
	public int getLang() {
		return Integer.parseInt(preference.getString(BaseConstant.PREF_LANG, "0"));
	}
	
	public int getFirstDayofWeek() {
		return Integer.parseInt(preference.getString(BaseConstant.PREF_FIRST_DAY_WEEK, Calendar.SUNDAY + ""));
	}

	public int getPeriod() {
		return Integer.parseInt(preference.getString(BaseConstant.PREF_PERIOD, BaseConstant.PREF_DEF_PERIOD + ""));
	}

	public String getPassword() {
		return preference.getString(BaseConstant.PREF_PASSWORD, "");
	}

	public String getEmailDef() {
		return preference.getString(BaseConstant.PREF_EMAIL_DEF, "");
	}
	
	public String getCurrencySign() {
		return preference.getString(BaseConstant.PREF_CURRENCY_SIGN, BaseConstant.PREF_DEF_CURRENCY_SIGN);
	}

	public String getCurrencyCode() {
		return preference.getString(BaseConstant.PREF_CURRENCY_CODE, BaseConstant.PREF_DEF_CURRENCY_CODE);
	}
	
	public String getSysLang() {
		return preference.getString(BaseConstant.PREF_LANG_SYS, "Non");
	}
	
	public void savePreference(String key, boolean value) {
		SharedPreferences.Editor editor = preference.edit();
		editor.putBoolean(key,value);
		editor.commit();
	}
	public void savePreference(String key, int value) {
		SharedPreferences.Editor editor = preference.edit();
		editor.putInt(key,value);
		editor.commit();
	}
	public void savePreference(String key, float value) {
		SharedPreferences.Editor editor = preference.edit();
		editor.putFloat(key,value);
		editor.commit();
	}
	public void savePreference(String key, long value) {
		SharedPreferences.Editor editor = preference.edit();
		editor.putLong(key,value);
		editor.commit();
	}
	public void savePreference(String key, String value) {
		SharedPreferences.Editor editor = preference.edit();
		editor.putString(key,value);
		editor.commit();
	}
	
	public long getPreference(String key) {
		return preference.getLong(key, 0);
	}
	public boolean getPreferenceCheck(String key) {
		return preference.getBoolean(key, false);
	}
	
	public void removePreference(String key) {
		SharedPreferences.Editor editor = preference.edit();
		editor.remove(key);
		editor.commit();
	}
	public int getCategoryPosition(String key) {
		return preference.getInt(key, 0);
	}
	
	/*
	 * license
	 */
	public String getInstalledDate() {
		return preference.getString(BaseConstant.PREF_LICENSE_INSTALLED_DATE, "");
	}

	public String getActivationKey() {
		return preference.getString(BaseConstant.PREF_LICENSE_ACTIVATION_KEY, "");
	}

	public void removeActivationKey() {
		Editor editor = preference.edit();
		editor.remove(BaseConstant.PREF_LICENSE_ACTIVATION_KEY);
		editor.commit();
	}

	public void saveLicense(License license) {
		Editor editor = preference.edit();
		editor.putString(BaseConstant.PREF_LICENSE_ACTIVATION_KEY, license.getActivationKey());
		editor.putString(BaseConstant.PREF_LICENSE_SERIAL_NUMBER, license.getSerialNumber());
		editor.putString(BaseConstant.PREF_LICENSE_USER_NAME, license.getUserName());
		editor.putString(BaseConstant.PREF_LICENSE_PHONE, license.getPhone());
		editor.putString(BaseConstant.PREF_LICENSE_EMAIL, license.getEmail());
		editor.putString(BaseConstant.PREF_LICENSE_ITEM_ID, license.getItem());
		editor.putString(BaseConstant.PREF_LICENSE_INSTALLED_DATE, license.getInstalledDate());
		editor.putString(BaseConstant.PREF_LICENSE_DEVICE_MODEL, license.getDeviceModel());
		editor.putString(BaseConstant.PREF_LICENSE_LOCALE, license.getLocale());
		editor.putString(BaseConstant.PREF_LICENSE_DEVICE_SERIAL, license.getDeviceSerial());
		editor.putString(BaseConstant.PREF_LICENSE_DEVICE_MACADDRESS, license.getDeviceMacAddress());
		editor.putInt(BaseConstant.PREF_LICENSE_PURCHASE_TYPE, license.getPurchaseType());
		//Log.e("TAG", "======license.getPurchaseType():"+license.getPurchaseType());

		editor.commit();
	}

	public License getLicense() {
		License license = new License();
		license.setActivationKey(preference.getString(BaseConstant.PREF_LICENSE_ACTIVATION_KEY, ""));
		license.setSerialNumber(preference.getString(BaseConstant.PREF_LICENSE_SERIAL_NUMBER, ""));
		license.setUserName(preference.getString(BaseConstant.PREF_LICENSE_USER_NAME, ""));
		license.setPhone(preference.getString(BaseConstant.PREF_LICENSE_PHONE, ""));
		license.setEmail(preference.getString(BaseConstant.PREF_LICENSE_EMAIL, ""));
		license.setInstalledDate(preference.getString(BaseConstant.PREF_LICENSE_INSTALLED_DATE, ""));
		license.setItem(preference.getString(BaseConstant.PREF_LICENSE_ITEM_ID, ""));
		license.setDeviceModel(preference.getString(BaseConstant.PREF_LICENSE_DEVICE_MODEL, ""));
		license.setLocale(preference.getString(BaseConstant.PREF_LICENSE_LOCALE, ""));
		license.setDeviceSerial(preference.getString(BaseConstant.PREF_LICENSE_DEVICE_SERIAL, ""));
		license.setDeviceMacAddress(preference.getString(BaseConstant.PREF_LICENSE_DEVICE_MACADDRESS, ""));
		license.setPurchaseType(preference.getInt(BaseConstant.PREF_LICENSE_PURCHASE_TYPE, 0));
		return license;
	}

	public int getLicenseType() {
		return preference.getInt(BaseConstant.PREF_LICENSE_PURCHASE_TYPE, 0);
	}

}
