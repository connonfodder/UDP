package com.aadhk.kds.fragment;

import java.util.ArrayList;
import java.util.List;

import org.acra.ACRA;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.Preference.OnPreferenceClickListener;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;

import com.aadhk.billing.IabHelper;
import com.aadhk.billing.IabResult;
import com.aadhk.billing.Inventory;
import com.aadhk.billing.Purchase;
import com.aadhk.kds.POSApp;
import com.aadhk.kds.R;
import com.aadhk.kds.SettingsActivity;
import com.aadhk.kds.dialog.ProductRegistrationDialog;
import com.aadhk.kds.dialog.ProductRegistrationDialog.OnUnlockListener;
import com.aadhk.kds.service.TaskRegisterLicense;
import com.aadhk.kds.util.ChangeLog;
import com.aadhk.kds.util.Config;
import com.aadhk.kds.util.Constant;
import com.aadhk.kds.util.PreferenceUtil;
import com.aadhk.kds.view.EditMintuesDialog;
import com.aadhk.kds.view.EditTimeDialog;
import com.aadhk.license.LicenseException;
import com.aadhk.license.LicenseManager;
import com.aadhk.product.asyn.TaskAsyncCustom;
import com.aadhk.product.bean.License;
import com.aadhk.product.dialog.MessageDialog;
import com.aadhk.product.util.BaseConstant;
import com.aadhk.product.util.IntentUtil;

public class SettingsFragment extends PreferenceFragmentCompat implements OnPreferenceClickListener, OnSharedPreferenceChangeListener {
	private static final String TAG = "SettingsFragment";
	private SettingsActivity activity;
	private IabHelper mHelper;
	private boolean isIA;
	private Preference prefMinutes, prefFontSize, prefSupport, prefLog, prefRegister, prefPurchase;
	private PreferenceUtil prefUtil;
	private ListPreference prefLang;
	private PreferenceScreen preferenceScreen;

	@Override
	public void onCreatePreferences(Bundle arg0, String arg1) {
		addPreferencesFromResource(R.xml.preference_settings);
		preferenceScreen = getPreferenceScreen();
		
		prefUtil = new PreferenceUtil(activity);
		prefMinutes = findPreference(Constant.PREF_MINUTES);
		prefMinutes.setOnPreferenceClickListener(this);
		prefFontSize = findPreference(Constant.PREF_FONTSIZE);
		prefFontSize.setOnPreferenceClickListener(this);
		prefSupport = findPreference(Constant.PREF_SUPPORT);
		prefSupport.setOnPreferenceClickListener(this);
		prefLog = findPreference(Constant.PREF_LOG);
		prefLog.setOnPreferenceClickListener(this);
		prefLang = (ListPreference) findPreference(BaseConstant.PREF_LANG);

		prefRegister = findPreference(Constant.PREF_REGITER);
		prefRegister.setOnPreferenceClickListener(this);

		prefPurchase = findPreference(Constant.PREF_BUY);
		prefPurchase.setOnPreferenceClickListener(this);
		
		if (Config.WHITE_LABLE_VERSION) {
			preferenceScreen.removePreference(prefSupport);
			preferenceScreen.removePreference(prefLog);
		}

		if(Config.DEVELOPER_VERSION){
			preferenceScreen.removePreference(prefRegister);
		}

	}

	@Override
	public void onAttach(Activity activity) {
		this.activity = (SettingsActivity) activity;
		super.onAttach(activity);
	}

	@Override
	public void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
		String Value = String.format(getString(R.string.prefMinutesSummary), prefUtil.getRedMinutes(), prefUtil.getGreenMinutes(), prefUtil.getYellowMinutes());
		prefMinutes.setSummary(Value);
		String fontSizeValue = String.format(getString(R.string.prefFontSizeSummary), prefUtil.getFontSize());
		prefFontSize.setSummary(fontSizeValue);
	}

	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);
		String message;
		try {
			message = String.format(getString(R.string.versionNum), activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionName);
			prefLog.setSummary(message);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		if (Config.TRIAL_VERSION) {
			getPreferenceScreen().removePreference(prefPurchase);
		}else if (Config.DEVELOPER_VERSION || LicenseManager.isPurchased(activity, Config.PRODUCT_FULL)||Config.INAPP_VERSION) {
			getPreferenceScreen().removePreference(prefPurchase);
			getPreferenceScreen().removePreference(prefRegister);
		}

		mHelper = new IabHelper(activity, Config.BASE64_ENCODE_PUBLIC_KEY);
		try {
			mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
				@Override
				public void onIabSetupFinished(IabResult result) {
					if (!result.isSuccess()) {
						// complain("Problem setting up in-app billing: " + result);
						return;
					}
					isIA = true;
					final List<String> moreSkus = new ArrayList<String>();
					moreSkus.add(Config.PRODUCT_FULL);
					mHelper.queryInventoryAsync(true, moreSkus, mGotInventoryListener);
				}
			});
		} catch (Exception e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		Preference pref = findPreference(key);
		if(pref instanceof  ListPreference){
			ListPreference listPref = (ListPreference) pref;
			if (listPref == prefLang) {
				((POSApp) activity.getApplication()).setLocale();
				activity.recreate();
			}
		}
	}

	@Override
	public boolean onPreferenceClick(Preference pref) {
		if (pref == prefMinutes) {
			showEditTextMintuesDialog();
		} else if (pref == prefFontSize) {
			showEditTextTimeDialog(Constant.TYPE_FONTSIZE);
		} else if (pref == prefSupport) {
			IntentUtil.emailUs(activity);
			/*Intent intent = new Intent();
			intent.setClass(activity, SupportActivity.class);
			startActivity(intent);*/
		} else if (pref == prefLog) {
			ChangeLog changeLog = new ChangeLog(activity);
			changeLog.getLogDialog().show();
		} else if (pref == prefRegister) {
			registerDialog();
		} else if (pref == prefPurchase) {
			handlePurchase();
		}
		//		else if (pref == prefRefresh) {
		//			showEditTextTimeDialog(Constant.TYPE_REFRESH);
		//		} else if(pref==prefIpAdress){
		//			ServerIpDialog dialog = new ServerIpDialog(activity, prefUtil.getServerIp(),Constant.SOCKET_PORT);
		//			dialog.setTitle(getString(R.string.menuKitchenIp));
		//			dialog.setOnConfirmListener(new ServerIpDialog.OnConfirmListener() {
		//				@Override
		//				public void onConfirm(Object value) {
		//					String ipValue = String.format(getString(R.string.prefIpSummary), (String) value);
		//					prefIpAdress.setSummary(ipValue);
		//					prefUtil.savePreference(Constant.PREF_SERVER_IP, (String) value);
		//				}
		//			});
		//			dialog.show();
		//		}
		return true;
	}

	private void handlePurchase() {
		if (!isIA) {
			MessageDialog msgDialog = new MessageDialog(activity);
			msgDialog.setTitle(R.string.msgIAStartFail);
			msgDialog.show();
		} else {
		try {
			mHelper.launchPurchaseFlow(activity, Config.PRODUCT_FULL, IabHelper.PURCHASE_RC_REQUEST, mPurchaseFinishedListener, IabHelper.PAYLOAD);
		} catch (Exception e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}
		}
	}

	private IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
		@Override
		public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
			Log.d(TAG, "Query inventory finished.");
			if (result.isFailure()) {
				// complain("Failed to query inventory: " + result);
				return;
			}

			try {
				Purchase purchase = inventory.getPurchase(Config.PRODUCT_FULL);
				if (purchase != null && purchase.getPurchaseState() == IabHelper.PURCHASE_STATE_PURCHASED) {
					//Toast.makeText(PurchaseActivity.this, "===>purchase<=======" + purchase, Toast.LENGTH_LONG).show();
					LicenseManager.addPurchase(activity, purchase);
					prefPurchase.setSummary(R.string.purchasedSummary);
				} else {
					LicenseManager.deletePurchase(activity, Config.PRODUCT_FULL);
				}
			} catch (LicenseException e) {
				ACRA.getErrorReporter().handleException(e);
				e.printStackTrace();
			}

		}
	};

	// 完成購買
	// Callback for when a purchase is finished
	private IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
		public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
			if (result.isFailure()) {
				// complain("Error purchasing: " + result);
				return;
			}
			try {
				LicenseManager.addPurchase(activity, purchase);
				prefPurchase.setSummary(R.string.purchasedSummary);
			} catch (LicenseException e) {
				ACRA.getErrorReporter().handleException(e);
				e.printStackTrace();
			}
		}
	};
	
	@Override
	public void onDestroy() {
		if (mHelper != null) {
			mHelper.dispose();
			mHelper = null;
		}
		super.onDestroy();
	}

	private void registerDialog() {
		final ProductRegistrationDialog dialog = new ProductRegistrationDialog(activity, prefUtil.getLicense());
		dialog.setCancelable(true);
		dialog.setonUnlockListener(new OnUnlockListener() {
			@Override
			public void onUnlock(License license) {
				TaskAsyncCustom dataTask = new TaskAsyncCustom(new TaskRegisterLicense(activity, license, dialog), activity);
				dataTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);
			}
		});
		dialog.show();
	}

	private void showEditTextMintuesDialog() {
		EditMintuesDialog dialog = new EditMintuesDialog(activity);
		dialog.setOnConfirmListener(new EditMintuesDialog.OnConfirmListener() {
			@Override
			public void onConfirm(Object object) {
				String minute = (String) object;
				String[] minutes = minute.split("\\,");
				int redMinutes = Integer.parseInt(minutes[0]);
				int greenMinutes = Integer.parseInt(minutes[1]);
				int yellowMinutes = Integer.parseInt(minutes[2]);
				String Value = String.format(getString(R.string.prefMinutesSummary), redMinutes, greenMinutes, yellowMinutes);
				prefMinutes.setSummary(Value);
				prefUtil.savePreference(Constant.PREF_KEY_REDMINUTES, redMinutes);
				prefUtil.savePreference(Constant.PREF_KEY_GREENMINUTES, greenMinutes);
				prefUtil.savePreference(Constant.PREF_KEY_YELLOWMINUTES, yellowMinutes);
			}
		});
		dialog.setOnCancelListener(new EditMintuesDialog.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
			}
		});
		dialog.show();
	}

	private void showEditTextTimeDialog(final int type) {
		EditTimeDialog dialog = new EditTimeDialog(activity, type);
		dialog.setTitle(getString(R.string.setFontSize));
		if (type == Constant.TYPE_REFRESH) {
			dialog.setTitle(getString(R.string.setRefresh));
		}
		dialog.setOnConfirmListener(new EditTimeDialog.OnConfirmListener() {
			@Override
			public void onConfirm(Object object) {
				if (type == Constant.TYPE_FONTSIZE) {
					int fontSize = Integer.parseInt((String) object);
					String Value = String.format(getString(R.string.prefFontSizeSummary), fontSize);
					prefFontSize.setSummary(Value);
					prefUtil.savePreference(Constant.PREF_KEY_FONTSIZE, fontSize);
				}
			}
		});
		dialog.show();
	}
}
