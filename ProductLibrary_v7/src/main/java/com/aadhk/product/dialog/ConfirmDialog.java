package com.aadhk.product.dialog;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.aadhk.product.library.R;

public class ConfirmDialog extends FieldDialog implements OnClickListener {

	private static final String TAG = "ConfirmDialog";
	private final Button btnConfirm, btnCancel;

	public ConfirmDialog(Context context) {
		super(context, R.layout.dialog_confirm);

		btnConfirm = (Button) findViewById(R.id.btnConfirm);
		btnCancel = (Button) findViewById(R.id.btnCancel);

		btnConfirm.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == btnConfirm) {
			if (onConfirmListener != null) {
				onConfirmListener.onConfirm(null);
			}
		} else if (v == btnCancel) {
			dismiss();
		}
	}

}
