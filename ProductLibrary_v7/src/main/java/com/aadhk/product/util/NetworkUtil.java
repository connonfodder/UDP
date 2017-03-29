package com.aadhk.product.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.aadhk.product.dialog.MessageDialog;
import com.aadhk.product.library.R;

public class NetworkUtil {
	private static final String TAG = "NetworkUtil";
	public static boolean sameNetwork(Activity activity,  String testIp) throws UnknownHostException {
		
		WifiManager wifiMgr = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
		DhcpInfo  dhcpInfo =wifiMgr.getDhcpInfo();
		String wifiIP = android.text.format.Formatter.formatIpAddress(wifiInfo.getIpAddress());
		String wifiMask = android.text.format.Formatter.formatIpAddress(dhcpInfo.netmask);
		InetAddress wifiIpAddress = InetAddress.getByName(wifiIP);
		InetAddress testIpAddress = InetAddress.getByName(testIp);
		InetAddress maskIpAddress = InetAddress.getByName(wifiMask);
		
		byte[] a1 = wifiIpAddress.getAddress();
		byte[] a2 = testIpAddress.getAddress();
		byte[] m = maskIpAddress.getAddress();

		for (int i = 0; i < a1.length; i++){
			if ((a1[i] & m[i]) != (a2[i] & m[i])){
				return false;
			}
		}
		return true;
	}
	
	public static List<String> findIp(final String subnet, final int port){
		List<Future<String>> futureList = new ArrayList<Future<String>>();
		ArrayList<String> foundIp = new ArrayList<String>();
//		Log.i(TAG, port + "===================port==============");
		final int timeout = 200;
		final int nthred = 20;
		ExecutorService es = Executors.newFixedThreadPool(nthred);
		for (int i = 1; i < 255; i++) {
			final String host = subnet + "." + i;
			Future<String> result = es.submit(new Callable<String>() {
				public String call() throws Exception {
					try {
						Socket socket = new Socket();
						//Log.i(TAG, host);
						socket.connect(new InetSocketAddress(host, port), timeout);
						socket.close();
						//Log.i(TAG, host + "===================found==============");
						return host;
					} catch (IOException e) {
						return null;
					}
				}
			});
			futureList.add(result);
		}

		for (Future<String> future : futureList) {
			try {
				if(future.get()!=null){
					foundIp.add(future.get());
					//Log.i(TAG, future.get() + "===================found 2==============");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		es.shutdown();
		return foundIp;
	}
	
	

	public static boolean isWifiOn(Activity activity) {
		ConnectivityManager connMgr = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (!networkInfo.isConnected()) {
			Toast.makeText(activity, activity.getString(R.string.turnOnWifi), Toast.LENGTH_LONG).show();
			activity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
			return false;
		} else {
			return true;
		}
	}

	public static boolean isInternetConnection(final Activity activity) {
		NetworkInfo networkInfo = ((ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
//		Log.i(TAG,"networkInfo:"+networkInfo+ ", networkInfo.isConnected():"+networkInfo.isConnected());

		if (networkInfo != null && networkInfo.isConnected()) {
			try {
				InetAddress inetAddress = InetAddress.getByName("baidu.com");
//				Log.i(TAG,"===>inetAddress:"+inetAddress);
				return inetAddress!=null && !inetAddress.equals("");
	        } catch (UnknownHostException e) {
	        	e.printStackTrace();
	        	return false;
	        }

		}
		return false;
	}
	
	public static boolean checkNetworkConnection(final Activity activity) {
		NetworkInfo networkInfo = ((ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		if (networkInfo == null || !networkInfo.isConnected()) {

			MessageDialog msgDialog = new MessageDialog(activity);
			msgDialog.setTitle(R.string.networkMsgChecking);
			msgDialog.setOkOnClickListener(new MessageDialog.OnOkListener() {
				@Override
				public void onOk() {
					activity.finish();
				}
			});
			msgDialog.show();

			return false;
		}
		return true;
	}

	public static boolean checkNetwork(final Activity activity, int message) {
		NetworkInfo networkInfo = ((ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		if (networkInfo == null || !networkInfo.isConnected()) {
			MessageDialog msgDialog = new MessageDialog(activity);
			msgDialog.setTitle(message);
			msgDialog.show();
			return false;
		}
		return true;
	}

	public static boolean hasNetworkConnection(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager != null) {
			// 获得网络连接管理的对象
			NetworkInfo info = manager.getActiveNetworkInfo();
			if (info != null && info.isConnected()) {
				// 判断当前网络是否已连接
				if (info.getState() == NetworkInfo.State.CONNECTED)
					return true;
			}
		}
		return false;
	}
	
	public static boolean checkNetwork(final Activity activity) {
		return checkNetwork(activity, R.string.networkMsgChecking);
	}

	
}
