package com.aadhk.product.dialog;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.aadhk.product.library.R;

public class FieldDialog extends AppCompatDialog {

	private static final String TAG = "FieldDialog";
	public OnConfirmListener onConfirmListener;
	public OnDeleteListener onDeleteListener;
	public Resources resources;
	public TextView dlgTitle;
	public Context context;

	public FieldDialog(Context context, int layout) {
		super(context);
		supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
		setCancelable(true);
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

	public void hideTitle() {
		dlgTitle.setVisibility(View.GONE);
	}

	public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
		this.onConfirmListener = onConfirmListener;
	} 

	public interface OnConfirmListener {
		void onConfirm(Object object);
	}

	public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
		this.onDeleteListener = onDeleteListener;
	}

	public interface OnDeleteListener {
		void onDelete();
	}

}
