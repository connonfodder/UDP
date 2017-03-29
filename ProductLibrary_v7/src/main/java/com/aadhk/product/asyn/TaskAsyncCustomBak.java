package com.aadhk.product.asyn;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.aadhk.product.dialog.CustomProgressDialog;

public class TaskAsyncCustomBak extends AsyncTask<Void, Void, Void> {

	private CustomProgressDialog progressDialog;
	private TaskAsyncCallBack callBack;
	private final Activity mParentActivity;
	private static Object runLock = new Object();
	private boolean isCancelable;

	public TaskAsyncCustomBak(TaskAsyncCallBack callBack, Context context,boolean isCancelable) {
		this.callBack = callBack;
		this.mParentActivity = (Activity) context;
		this.isCancelable = isCancelable;
	}
	
	public TaskAsyncCustomBak(TaskAsyncCallBack callBack, Context context) {
		this.callBack = callBack;
		this.mParentActivity = (Activity) context;
		this.isCancelable = false;
	}

	@Override
	protected void onPreExecute() {
		startProgressDialog(); 
		super.onPreExecute();
	}

	protected Void doInBackground(Void... voidList) {
//		try {  
//            Thread.sleep(10 * 1000);  
//        } catch (InterruptedException e) {  
//            e.printStackTrace();  
//        }  
		synchronized (TaskAsyncCustomBak.runLock) {
			callBack.setupData();
		}
		return null;
	}

	protected void onPostExecute(Void voidValue) {
		if (!isCancelled()) {
			if (mParentActivity != null && !mParentActivity.isFinishing() && progressDialog.isShowing()) {
				stopProgressDialog();
			}
			callBack.showView();
		}
	}
	
	@Override  
    protected void onCancelled() {  
        stopProgressDialog();  
        super.onCancelled();  
    }  
    
    private void startProgressDialog(){  
        if (progressDialog == null){  
            progressDialog = CustomProgressDialog.createDialog(mParentActivity);
            progressDialog.setCancelable(isCancelable);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
    		    @Override
    		    public void onCancel(DialogInterface dialog) {
    		    	cancel(true);
    		    }
    		});
        }  
        progressDialog.show();  
    }  
      
    private void stopProgressDialog(){  
        if (progressDialog != null){  
            progressDialog.dismiss();  
            progressDialog = null;  
        }  
    }  
}
