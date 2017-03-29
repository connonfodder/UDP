package com.aadhk.product.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aadhk.product.library.R;

public class CreateFileMessageDialog extends Dialog implements OnClickListener {

	private Button btnOk;
	private TextView message;
	private EditText fileMessage;
	private OnOkClickListener onOkClickListener;

	public CreateFileMessageDialog(Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setCancelable(true);
		setContentView(R.layout.dialog_filecreate_message);
		message = (TextView) findViewById(R.id.message);
		fileMessage = (EditText) findViewById(R.id.fileNameMessage);

		btnOk = (Button) findViewById(R.id.btnOk);
		btnOk.setOnClickListener(this);
	}

	public void setOnOkClickListener(View.OnClickListener okListener) {
		btnOk.setOnClickListener(okListener);
	}

	public void setTitle(String title) {
		message.setText(title);
	}

	public void setTitle(int title) {
		message.setText(title);
	}
	
	public void setOnOkClickListener(OnOkClickListener onOkClickListener) {
		this.onOkClickListener=onOkClickListener;
	}
	
	public interface OnOkClickListener{
		void onConfirm(Object object);
	}
	@Override
	public void onClick(View v) {
		if (v == btnOk) {
			onOkClickListener.onConfirm(fileMessage.getText().toString());
		}
		dismiss();

	}

}
