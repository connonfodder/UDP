package com.aadhk.kds.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.aadhk.kds.R;
import com.aadhk.kds.util.PreferenceUtil;
import com.aadhk.product.dialog.FieldDialog;

public class EditMintuesDialog extends FieldDialog implements OnClickListener {

	private static final String TAG = "ConfirmDialog";
	private Button btnConfirm, btnCancel;
	private ImageButton redAddNumber, redSubtractNumber, greenAddNumber, greenSubtractNumber, yellowAddNumber, yellowSubtractNumber;
	private EditText redMinutes, greenMinutes, yellowMinutes;
	private CharSequence errorMessage;
	private PreferenceUtil prefUtil;

	public EditMintuesDialog(Context context) {
		super(context, R.layout.dialog_edit_mintues);

		prefUtil = new PreferenceUtil(context);
		redMinutes = (EditText) findViewById(R.id.redMinutes);
		greenMinutes = (EditText) findViewById(R.id.greenMinutes);
		yellowMinutes = (EditText) findViewById(R.id.yellowMinutes);
		redAddNumber = (ImageButton) findViewById(R.id.redAddNumber);
		redSubtractNumber = (ImageButton) findViewById(R.id.redSubtractNumber);
		greenAddNumber = (ImageButton) findViewById(R.id.greenAddNumber);
		greenSubtractNumber = (ImageButton) findViewById(R.id.greenSubtractNumber);
		yellowAddNumber = (ImageButton) findViewById(R.id.yellowAddNumber);
		yellowSubtractNumber = (ImageButton) findViewById(R.id.yellowSubtractNumber);

		btnConfirm = (Button) findViewById(R.id.btnConfirm);
		btnCancel = (Button) findViewById(R.id.btnCancel);

		redAddNumber.setOnClickListener(this);
		redSubtractNumber.setOnClickListener(this);
		greenAddNumber.setOnClickListener(this);
		greenSubtractNumber.setOnClickListener(this);
		yellowAddNumber.setOnClickListener(this);
		yellowSubtractNumber.setOnClickListener(this);
		btnConfirm.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		redMinutes.setText(prefUtil.getRedMinutes() + "");
		greenMinutes.setText(prefUtil.getGreenMinutes() + "");
		yellowMinutes.setText(prefUtil.getYellowMinutes() + "");
		errorMessage = resources.getString(R.string.errorEmpty);
	}

	@Override
	public void onClick(View v) {
		if (v == btnConfirm) {
			if (onConfirmListener != null) {
				String red = redMinutes.getText().toString();
				String green = greenMinutes.getText().toString();
				String yellow = yellowMinutes.getText().toString();
				if (TextUtils.isEmpty(red)) {
					redMinutes.setError(errorMessage);
				} else if (TextUtils.isEmpty(green)) {
					greenMinutes.setError(errorMessage);
				} else if (TextUtils.isEmpty(yellow)) {
					yellowMinutes.setError(errorMessage);
				} else {
					String minutes = red + "," + green + "," + yellow;
					onConfirmListener.onConfirm(minutes);
					dismiss();
				}
			}
		} else if (v == btnCancel) {
			dismiss();
		} else if (v == redAddNumber) {
			redMinutes.requestFocus();
			int number = Integer.parseInt(redMinutes.getText().toString());
			redMinutes.setText(number + 1 + "");
		} else if (v == redSubtractNumber) {
			redMinutes.requestFocus();
			int number = Integer.parseInt(redMinutes.getText().toString());
			if (number > 0)
				redMinutes.setText(number - 1 + "");
		} else if (v == greenAddNumber) {
			greenMinutes.requestFocus();
			int number = Integer.parseInt(greenMinutes.getText().toString());
			greenMinutes.setText(number + 1 + "");
		} else if (v == greenSubtractNumber) {
			greenMinutes.requestFocus();
			int number = Integer.parseInt(greenMinutes.getText().toString());
			if (number > 0)
				greenMinutes.setText(number - 1 + "");
		} else if (v == yellowAddNumber) {
			yellowMinutes.requestFocus();
			int number = Integer.parseInt(yellowMinutes.getText().toString());
			yellowMinutes.setText(number + 1 + "");
		} else if (v == yellowSubtractNumber) {
			yellowMinutes.requestFocus();
			int number = Integer.parseInt(yellowMinutes.getText().toString());
			if (number > 0)
				yellowMinutes.setText(number - 1 + "");
		}
	}
}
