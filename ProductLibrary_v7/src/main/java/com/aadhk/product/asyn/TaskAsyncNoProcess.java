package com.aadhk.product.asyn;

import android.os.AsyncTask;

public class TaskAsyncNoProcess extends AsyncTask<Void, Void, Void> {

	private TaskAsyncCallBack callBack;
	private static Object runLock = new Object();

	public TaskAsyncNoProcess(TaskAsyncCallBack callBack) {
		this.callBack = callBack;
	}

	protected Void doInBackground(Void... voidList) {
//		try {  
//            Thread.sleep(5 * 1000);  
//        } catch (InterruptedException e) {  
//            e.printStackTrace();  
//        }  
		synchronized (TaskAsyncNoProcess.runLock) {
			callBack.setupData();
		}
		return null;
	}

	protected void onPostExecute(Void voidValue) {
		if (!isCancelled()) {
			callBack.showView();
		}
	}
}
