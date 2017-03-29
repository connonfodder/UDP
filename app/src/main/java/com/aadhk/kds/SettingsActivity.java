package com.aadhk.kds;

import android.os.Bundle;

import com.aadhk.kds.fragment.SettingsFragment;

public class SettingsActivity extends POSActivity {
	private static final String TAG = "KitchenSettingActivity";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle(R.string.menuSetting);
		// Display the fragment as the main content.
		getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
	}

//	@Override
//	public void onBackPressed() {
//
//	}

}
