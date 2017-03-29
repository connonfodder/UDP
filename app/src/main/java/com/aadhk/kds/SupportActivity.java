package com.aadhk.kds;

import android.os.Bundle;

import com.aadhk.kds.fragment.SupportFragment;

public class SupportActivity extends POSActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle(R.string.titleSupport);
		getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new SupportFragment()).commit();
	}

}