/*******************************************************************************
 * Copyright (C) Hong Kong Android Technology Co.
 * All right reserved.
 ******************************************************************************/
package com.aadhk.kds.view;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

import com.aadhk.kds.R;
import com.aadhk.product.util.IntentUtil;


public class ChangeLogDialog extends RestPosDialog implements OnClickListener {

	private static final String TAG = "ChangeLogDialog";

	private Button btnCancel, btnRate;
	private WebView msgContent;
	private Context context;

	public ChangeLogDialog(Context context) {
		super(context, R.layout.dialog_change_log);
		this.context = context;
		msgContent = (WebView) findViewById(R.id.msgContent);
		msgContent.setBackgroundColor(0);
		btnRate = (Button) findViewById(R.id.btnRate);
		btnRate.setOnClickListener(this);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(this);
	}

	public void setOkOnClickListener(View.OnClickListener okListener) {
		btnCancel.setOnClickListener(okListener);
	}

	public void setCustomView(String data) {
		msgContent.loadData(data, "text/html", "UTF-8");
	}

	@Override
	public void onClick(View v) {
		if (v == btnRate) {
			IntentUtil.rateReview(context);
		}
		dismiss();

	}

}
