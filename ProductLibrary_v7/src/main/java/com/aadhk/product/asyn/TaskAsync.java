package com.aadhk.product.asyn;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

public class TaskAsync extends AsyncTask<Void, Void, Void> {

	private ProgressDialog progressDialog;
	private TaskAsyncCallBack callBack;
	private final Activity mParentActivity;
	private static Object runLock = new Object();
	private String message;
	private boolean isCancelable;

	public TaskAsync(TaskAsyncCallBack callBack, Context context,  String message, boolean isCancelable) {
		this.callBack = callBack;
		this.mParentActivity = (Activity) context;
		this.message = message;
		this.isCancelable = isCancelable;
	}
	
	public TaskAsync(TaskAsyncCallBack callBack, Context context,  String message) {
		this.callBack = callBack;
		this.mParentActivity = (Activity) context;
		this.message = message;
		this.isCancelable = false;
	}

	@Override
	protected void onPreExecute() {
		progressDialog = new ProgressDialog(mParentActivity);
		progressDialog.setCancelable(isCancelable);
		progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
		    @Override
		    public void onCancel(DialogInterface dialog) {
		    	cancel(true);
		    }
		});
		progressDialog.setMessage(message);
		progressDialog.show();
		super.onPreExecute();
	}

	protected Void doInBackground(Void... voidList) {
		synchronized (TaskAsync.runLock) {
			callBack.setupData();
		}
		return null;
	}

	protected void onPostExecute(Void voidValue) {
		if (!isCancelled()) {
			if (mParentActivity != null && !mParentActivity.isFinishing() && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			callBack.showView();
		}
	}
}
