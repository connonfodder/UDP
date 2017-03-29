package com.aadhk.product.asyn;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.aadhk.product.dialog.WaitDialog;

public class TaskAsyncCustom extends AsyncTask<Void, Void, Void> implements DialogInterface.OnCancelListener{

	private WaitDialog waitDialog;
	private com.aadhk.product.asyn.TaskAsyncCallBack callBack;
	private final Activity mParentActivity;
	private static Object runLock = new Object();
	private boolean isCancelable;

	public TaskAsyncCustom(com.aadhk.product.asyn.TaskAsyncCallBack callBack, Context context, boolean isCancelable) {
		this.callBack = callBack;
		this.mParentActivity = (Activity) context;
		this.isCancelable = isCancelable;
	}

	public TaskAsyncCustom(com.aadhk.product.asyn.TaskAsyncCallBack callBack, Context context) {
		this.callBack = callBack;
		this.mParentActivity = (Activity) context;
		this.isCancelable = false;
	}

	@Override
	protected void onPreExecute() {
		waitDialog = WaitDialog.show(mParentActivity, null, null, false, isCancelable, this);
		super.onPreExecute();
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		cancel(true);
	}

	protected Void doInBackground(Void... voidList) {
		synchronized (TaskAsyncCustom.runLock) {
			callBack.setupData();
		}
		return null;
	}

	protected void onPostExecute(Void voidValue) {
		if (!isCancelled()&& mParentActivity != null && !mParentActivity.isFinishing()) {
			//if (mParentActivity != null && !mParentActivity.isFinishing()  && !mParentActivity.isDestroyed() && waitDialog.isShowing()) {
			if (waitDialog!=null && waitDialog.isShowing()) {
				waitDialog.dismiss();
			}
			callBack.showView();
		}
	}

	@Override
	protected void onCancelled() {
		if (waitDialog != null){
			waitDialog.dismiss();
			waitDialog = null;
		}
		super.onCancelled();
	}
}
