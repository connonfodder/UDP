package com.aadhk.kds.view;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.aadhk.kds.R;
import com.aadhk.kds.util.Constant;
import com.aadhk.product.asyn.TaskAsyncCallBack;
import com.aadhk.product.asyn.TaskAsyncCustom;
import com.aadhk.product.dialog.FieldDialog;
import com.aadhk.product.util.NetworkUtil;
import com.aadhk.product.util.PatternValidate;

import java.util.ArrayList;
import java.util.List;

public class ServerIpDialog extends FieldDialog implements OnClickListener {

	private static final String TAG = "ServerIpDialog";
	private Button btnSave, btnCancel;
	private EditText editIp;
	private ImageButton searchIp;
	private TextView tvConnectHint;
	private CharSequence errorMessage;
	private String serverAddress;
	private String tabletAddress;
	private List<String> ipAdress;
	private Context context;
	private int port;

	public ServerIpDialog(Context context, String serverAddress, int port) {
		super(context, R.layout.dialog_server_ip);
		this.context = context;
		this.port = port;
		this.serverAddress = serverAddress;
		ipAdress = new ArrayList<String>();
		btnSave = (Button) findViewById(R.id.btnSave);
		btnCancel = (Button) findViewById(R.id.btnCancel);

		editIp = (EditText) findViewById(R.id.ipValue);
		searchIp = (ImageButton) findViewById(R.id.searchIp);
		tvConnectHint = (TextView) findViewById(R.id.tvConnectHint);

		btnSave.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		searchIp.setOnClickListener(this);
		editIp.setText(serverAddress);
		errorMessage = resources.getString(R.string.errorEmpty);
		setConnectText();
	}

	// 設置連接
	private void setConnectText() {
		WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
		String ssid = wifiInfo.getSSID();
		tabletAddress = android.text.format.Formatter.formatIpAddress(wifiInfo.getIpAddress());
		String hint;
		if (TextUtils.isEmpty(ssid)) {
			hint = String.format(context.getString(R.string.msgNotConnected));
		} else {
			hint = String.format(context.getString(R.string.hintServerConnect), ssid, tabletAddress);
		}
		tvConnectHint.setText(hint);

		// if(TextUtils.isEmpty(serverAddress)){
		// TaskAsyncCustom dataTask = new TaskAsyncCustom(new TaskSearchIp(), context);
		// dataTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);
		// }
	}

	private boolean validate() {
		serverAddress = editIp.getText().toString();

		if (TextUtils.isEmpty(serverAddress)) {
			editIp.setError(errorMessage);
			editIp.requestFocus();
			return false;
		} else if (!PatternValidate.IP_ADDRESS.matcher(serverAddress).matches()) {
			editIp.setError(context.getString(R.string.errorIpFormat));
			editIp.requestFocus();
			return false;
		}

		// check same network
		String newAddress = serverAddress.substring(0, serverAddress.lastIndexOf("."));
		String oldAddress = tabletAddress.substring(0, tabletAddress.lastIndexOf("."));
		if (!newAddress.equals(oldAddress)) {
			editIp.setError(context.getString(R.string.hintSameNetWork));
			editIp.requestFocus();
			return false;
		}

		return true;
	}

	@Override
	public void onClick(View v) {
		if (v == btnSave) {
			if (validate()) {
				if (onConfirmListener != null) {
					onConfirmListener.onConfirm(serverAddress);
					dismiss();
				}
			}
		} else if (v == btnCancel) {
			dismiss();
		} else if (v == searchIp) {
			TaskAsyncCustom dataTask = new TaskAsyncCustom(new TaskSearchIp(), context);
			dataTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);
		}
	}

	private class TaskSearchIp implements TaskAsyncCallBack {
		String foundIp = "";

		@Override
		public void setupData() {
			String subnet = tabletAddress.substring(0, tabletAddress.lastIndexOf("."));
			List<String> printerList = NetworkUtil.findIp(subnet, Constant.HTTP_SERVER_PORT);
			if (!printerList.isEmpty()) {
				foundIp = printerList.get(0);
			}
		}

		@Override
		public void showView() {
			if (TextUtils.isEmpty(foundIp)) {
				// editIp.setText("");
				Toast.makeText(context, context.getString(R.string.serverNotFound), Toast.LENGTH_SHORT).show();
			} else {
				editIp.setText(foundIp);
			}
		}
	}
}
