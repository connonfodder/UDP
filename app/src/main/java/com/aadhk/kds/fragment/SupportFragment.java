package com.aadhk.kds.fragment;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.Preference.OnPreferenceClickListener;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.aadhk.kds.R;
import com.aadhk.kds.SupportActivity;
import com.aadhk.kds.util.Constant;
import com.aadhk.product.util.BaseConstant;
import com.aadhk.product.util.IntentUtil;

public class SupportFragment extends PreferenceFragmentCompat implements OnPreferenceClickListener {
	private Preference userVoice, skype, teamView, eamilLog, emailImage;
	private SupportActivity activity;
	private String email;

	@Override
	public void onCreatePreferences(Bundle arg0, String arg1) {
		addPreferencesFromResource(R.xml.support);
		userVoice = findPreference("userVoice");
		skype = findPreference("skype");
		teamView = findPreference("teamView");
		eamilLog = findPreference("eamilLog");
		emailImage = findPreference("emailImage");
		userVoice.setOnPreferenceClickListener(this);
		skype.setOnPreferenceClickListener(this);
		teamView.setOnPreferenceClickListener(this);
		eamilLog.setOnPreferenceClickListener(this);
		emailImage.setOnPreferenceClickListener(this);
		email = getString(R.string.companyEmail);
	}

	@Override
	public void onAttach(Activity activity) {
		this.activity = (SupportActivity) activity;
		super.onAttach(activity);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == BaseConstant.REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK) {
			Uri uri = intent.getData();
			String type = "application/octet-stream";
			String subject = getString(R.string.aadhk_app_name) + "image";
			IntentUtil.emailFile(activity, type, email, subject, uri);
		}
	}

	@Override
	public boolean onPreferenceClick(Preference pref) {
		if (pref == userVoice) { //user voice
//			IntentUtil.openSupportWebSite(activity);
		} else if (pref == skype) { //skype
			IntentUtil.openAnotherApp(activity, "com.skype.android.verizon");
		} else if (pref == teamView) { //team view
			IntentUtil.openAnotherApp(activity, "com.teamviewer.quicksupport.market");
		} else if (pref == eamilLog) { //email log file
			String type = "application/octet-stream";
			String subject = getString(R.string.aadhk_app_name) + "log file";
			String file = Constant.SDFOLDER_CRASH_FILE_NAME + File.separator + "crash.log";
			IntentUtil.emailFile(activity, type, email, subject, file);
		} else if (pref == emailImage) { //email screen capture
			IntentUtil.selectImage(activity);
		}
		return true;
	}
}
