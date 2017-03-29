/*******************************************************************************
 * Copyright (C) Hong Kong Android Technology Co.
 * All right reserved.
 ******************************************************************************/
package com.aadhk.kds.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.aadhk.kds.R;

/*
 * Clicl ok button, can be handle by setOkOnClickListener or onClick
 */
public class MessageDialog extends Dialog implements OnClickListener {

	private Button btnOk;
	private TextView msgTitle;
	private OnOkListener okListener;

	public MessageDialog(Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setCancelable(true);
		setContentView(R.layout.dialog_message);
		msgTitle = (TextView) findViewById(R.id.msgTitle);

		btnOk = (Button) findViewById(R.id.btnOk);
		btnOk.setOnClickListener(this);
	}

	public void setCustomTitle(String title) {
		msgTitle.setText(title);
	}

	public void setCustomTitle(int title) {
		msgTitle.setText(title);
	}

	@Override
	public void onClick(View v) {
		if (v == btnOk) {
			if (okListener != null) {
				okListener.onOk();
				dismiss();
			} else {
				dismiss();
			}
		}
	}

	public void setOkOnClickListener(OnOkListener okListener) {
		this.okListener = okListener;
	}

	public interface OnOkListener {
		void onOk();
	}
}
