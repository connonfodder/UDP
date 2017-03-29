/*******************************************************************************
 * Copyright (C) Hong Kong Android Technology Co.
 * All right reserved.
 ******************************************************************************/
package com.aadhk.kds.dialog;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatDialog;
import android.view.Window;
import android.widget.TextView;

import com.aadhk.kds.R;

public class KDSDialog extends AppCompatDialog {

	public Resources resources;
	public Context context;
	private TextView dlgTitle;

	public KDSDialog(Context context, int layout) {
		super(context);
		supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
		//setCancelable(true);
		setContentView(layout);
		this.context = context;
		resources = context.getResources();
		

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
