package com.aadhk.product.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;

import com.aadhk.product.library.R;

public class WaitDialog extends Dialog {

	public static WaitDialog show(Context context, CharSequence title, CharSequence message) {
		return show(context, title, message, false);
	}

	public static WaitDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate) {
		return show(context, title, message, indeterminate, false, null);
	}

	public static WaitDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable) {
		return show(context, title, message, indeterminate, cancelable, null);
	}

	public static WaitDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable, OnCancelListener cancelListener) {
		WaitDialog dialog = new WaitDialog(context);
		dialog.setTitle(title);
		dialog.setCancelable(cancelable);
		dialog.setOnCancelListener(cancelListener);
		/* The next line will add the ProgressBar to the dialog. */
		// ProgressBar bar = new ProgressBar(context);
		LayoutInflater inflater = LayoutInflater.from(context);
		ProgressBar bar = (ProgressBar) inflater.inflate(R.layout.custom_progress_bar, null);
		bar.setIndeterminate(true);

		bar.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.progress_custom));
		//bar.setIndeterminate(true);
		//bar.setProgressDrawable(context.getResources().getDrawable(R.drawable.process));
		dialog.addContentView(bar, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		dialog.show();

		return dialog;
	}

	public WaitDialog(Context context) {
		super(context, R.style.ProgressTheme);
		//super(context);
	}

}
