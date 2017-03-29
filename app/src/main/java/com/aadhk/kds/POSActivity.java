package com.aadhk.kds;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.aadhk.kds.util.PreferenceUtil;
import com.aadhk.kds.view.ConfirmDialog;
import com.google.firebase.analytics.FirebaseAnalytics;

public abstract class POSActivity extends AppCompatActivity {

	private static final String TAG = "POSActivity";

	protected Resources resources;
	protected PreferenceUtil prefUtil;
	private FirebaseAnalytics mFirebaseAnalytics;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		resources = getResources();
		prefUtil = new PreferenceUtil(this);
		mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 返回時提示用戶是否退出，以免誤操作導致廚房顯示沒數據
	 */
	@Override
	public void onBackPressed() {
		ConfirmDialog dialog = new ConfirmDialog(this);
		dialog.setTitle(R.string.confirmExit);
		dialog.setOnConfirmListener(new ConfirmDialog.OnConfirmListener() {
			@Override
			public void onConfirm() {
				finish();
			}
		});
		dialog.show();
	}

	protected <T extends View> T $(int id){
		return (T)findViewById(id);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
