package com.aadhk.product.dialog;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.aadhk.product.library.R;

public class MessageDialog extends AppCompatDialog implements OnClickListener {

	private Button btnOk;
	private TextView msgTitle;
	private OnOkListener okListener;

	public MessageDialog(Context context) {
		super(context);
		supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
		setCancelable(true);
		setContentView(R.layout.dialog_message);
		msgTitle = (TextView) findViewById(R.id.msgTitle);
		msgTitle.setMovementMethod(ScrollingMovementMethod.getInstance());
		btnOk = (Button) findViewById(R.id.btnOk);
		btnOk.setOnClickListener(this);
	}

	public void setTitle(String title) {
		msgTitle.setText(title);
	}

	public void setTitle(int title) {
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
