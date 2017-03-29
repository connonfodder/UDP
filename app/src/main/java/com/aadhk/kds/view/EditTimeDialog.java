package com.aadhk.kds.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.aadhk.kds.R;
import com.aadhk.kds.util.Constant;
import com.aadhk.kds.util.PreferenceUtil;
import com.aadhk.product.dialog.FieldDialog;

public class EditTimeDialog extends FieldDialog implements OnClickListener {

	private static final String TAG = "EditTextTimeDialog";
	private Button btnConfirm, btnCancel;
	private EditText editTimeTitle;
	private ImageButton addNumber, subtractNumber;
	private PreferenceUtil prefUtil;

	public EditTimeDialog(Context context, int type) {
		super(context, R.layout.dialog_time);
		prefUtil = new PreferenceUtil(context);
		editTimeTitle = (EditText) findViewById(R.id.editTimeTitle);
		addNumber = (ImageButton) findViewById(R.id.addNumber);
		subtractNumber = (ImageButton) findViewById(R.id.subtractNumber);
		btnConfirm = (Button) findViewById(R.id.btnConfirm);
		btnCancel = (Button) findViewById(R.id.btnCancel);

		addNumber.setOnClickListener(this);
		subtractNumber.setOnClickListener(this);
		btnConfirm.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		if (type == Constant.TYPE_FONTSIZE) {
			editTimeTitle.setHint(resources.getString(R.string.hintFontSize));
			editTimeTitle.setText(prefUtil.getFontSize() + "");
		} else {
			editTimeTitle.setHint(resources.getString(R.string.hintRefresh));
			editTimeTitle.setText(prefUtil.getRefresh() + "");
		}
	}

	@Override
	public void onClick(View v) {
		if (v == btnConfirm) {
			if (onConfirmListener != null) {
				String time = editTimeTitle.getText().toString();
				if (TextUtils.isEmpty(time)) {
					editTimeTitle.setError(resources.getString(R.string.errorEmpty));
				} else {
					onConfirmListener.onConfirm(time);
					dismiss();
				}
			}
		} else if (v == btnCancel) {
			dismiss();
		} else if (v == addNumber) {
			int number = Integer.parseInt(editTimeTitle.getText().toString());
			editTimeTitle.setText(number + 1 + "");
		} else if (v == subtractNumber) {
			int number = Integer.parseInt(editTimeTitle.getText().toString());
			if (number > 0)
				editTimeTitle.setText(number - 1 + "");
		}

	}

}
