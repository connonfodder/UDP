package com.aadhk.kds.view;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.aadhk.kds.R;
import com.aadhk.product.dialog.FieldDialog;

public class ConfirmDialog extends FieldDialog implements OnClickListener {

	private static final String TAG = "ConfirmDialog";
	private Button btnConfirm, btnCancel;
	private OnCancelListener onCancelListener;
	private OnConfirmListener onConfirmListener;

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
				onConfirmListener.onConfirm();
				dismiss();
			}
		} else if (v == btnCancel) {
			if (onCancelListener != null) {
				onCancelListener.onCancel();
			}
			dismiss();
		}
	}

	public void setConfirmLable(int lable) {
		btnConfirm.setText(lable);
	}

	public void setOnCancelListener(OnCancelListener onCancelListener) {
		this.onCancelListener = onCancelListener;
	}

	public interface OnCancelListener {
		void onCancel();
	}

	public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
		this.onConfirmListener = onConfirmListener;
	}

	public interface OnConfirmListener {
		void onConfirm();
	}

}
