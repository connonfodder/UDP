package com.aadhk.kds.view;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.aadhk.kds.R;
import com.aadhk.product.dialog.FieldDialog;

public class SelectItemFontSizeDialog extends FieldDialog implements OnClickListener, OnItemSelectedListener {

	private static final String TAG = "SelectItemFontSizeDialog";
	private Button btnSave, btnCancel;
	private Spinner spinnerFontSize;
	private String[] fontSize;

	public SelectItemFontSizeDialog(Context context, int fontValue) {
		super(context, R.layout.dialog_item_font_size);

		btnSave = (Button) findViewById(R.id.btnSave);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		spinnerFontSize = (Spinner) findViewById(R.id.spinnerFontSize);

		btnSave.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		spinnerFontSize.setOnItemSelectedListener(this);
		fontSize = context.getResources().getStringArray(R.array.itemFontSize);
		spinnerFontSize.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, fontSize));
		for (int i = 0; i < fontSize.length; i++) {
			if (String.valueOf(fontValue).equals(fontSize[i])) {
				spinnerFontSize.setSelection(i);
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnCancel:
			dismiss();
			break;
		case R.id.btnSave:
			if (onConfirmListener != null) {
				int font = (Integer.parseInt(spinnerFontSize.getSelectedItem().toString()));
				onConfirmListener.onConfirm(font);
				dismiss();
			}
			break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}
}
