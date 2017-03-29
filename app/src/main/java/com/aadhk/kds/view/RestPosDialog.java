/*******************************************************************************
 * Copyright (C) Hong Kong Android Technology Co.
 * All right reserved.
 ******************************************************************************/
package com.aadhk.kds.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatDialog;
import android.view.Window;
import android.widget.TextView;

import com.aadhk.kds.R;


public class RestPosDialog extends AppCompatDialog {

	public Resources resources;
	public SharedPreferences preference;

	public String dateFormat, timeFormat;
	public int firstDayofWeek;
	public String password;
	public boolean useDefault;

	public Context context;
	private TextView dlgTitle;

	public RestPosDialog(Context context, int layout) {
		super(context);
		supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
		//setCancelable(true);
		setContentView(layout);
		this.context = context;

		resources = context.getResources();
		preference = PreferenceManager.getDefaultSharedPreferences(context);

		/*
		PreferenceUtil prefUtil = new PreferenceUtil(context);
		currSign = prefUtil.getCurrencySign();
		shortCurrency =prefUtil.getShortCurrency();
		password = prefUtil.getPassword();
		useDefault = prefUtil.useDefault();
		*/

		dlgTitle = (TextView) findViewById(R.id.dlgTitle);
	}

	@Override
	public void setTitle(CharSequence title) {
		dlgTitle.setText(title);
	}

	@Override
	public void setTitle(int titleId) {
		dlgTitle.setText(titleId);
	}

}
