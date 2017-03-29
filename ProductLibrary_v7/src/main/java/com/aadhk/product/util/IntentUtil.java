package com.aadhk.product.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.acra.ACRA;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.net.Uri;
import android.widget.Toast;

import com.aadhk.apprate.PrefsContract;
import com.aadhk.product.library.R;

public class IntentUtil {

	public static void rateReview(Context context) {
		showAppInGooglePlay(context);
		
		SharedPreferences preferences = context.getSharedPreferences(PrefsContract.SHARED_PREFS_NAME, 0);
		Editor editor = preferences.edit();
		editor.putBoolean(PrefsContract.PREF_DONT_SHOW_AGAIN, true);
		editor.commit();
	}

	//提供二維碼下載路徑
	public static void barcodeDownload(Context context) {
		try {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("market://details?id=" + "com.google.zxing.client.android"));
			context.startActivity(intent);
		} catch (ActivityNotFoundException e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}
	}

	public static void showAppInGooglePlay(Context context) {
		try {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("market://details?id=" + context.getPackageName()));
			context.startActivity(intent);
		} catch (ActivityNotFoundException e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}
	}

	public static void openPDFBrowser(Context context, String pdfUrl) {
		try {
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdfUrl));
			context.startActivity(browserIntent);
		} catch (ActivityNotFoundException e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}
	}

	// 调用系统的安装方法
	public static void installAPK(Activity activity, File savedFile) {
		Intent intent = new Intent();
		intent.setAction(intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(savedFile), "application/vnd.android.package-archive");
		activity.startActivity(intent);
	}

	public static void share(Activity activity) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		Resources resources = activity.getResources();
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.msgShare));
		activity.startActivity(intent);
	}

	public static void emailUs(Activity activity) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		String emailList[] = { activity.getString(R.string.companyEmail) };
		intent.putExtra(Intent.EXTRA_EMAIL, emailList);
		intent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.aadhk_app_name));
		intent.putExtra(android.content.Intent.EXTRA_TEXT, createAppInfo (activity));
		activity.startActivity(Intent.createChooser(intent, activity.getString(R.string.emailChooser)));
		// activity.startActivity(intent);
	}
	
	public static String createAppInfo (Context context){
		BasePreferenceUtil prefUtil = new BasePreferenceUtil(context);
		String content = "";
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			content = context.getString(R.string.appInfo) + "\n" + "------------------------" + "\n" + context.getString(R.string.aadhk_app_name) + " " + packageInfo.versionName + "\n" + packageInfo.packageName + "\n" + android.os.Build.MODEL + " " + android.os.Build.VERSION.SDK_INT + "\n" + "app:" + Locale.getDefault() + ", sys:" + prefUtil.getSysLang() + "\n" + "------------------------\n\n";
		} catch (NotFoundException e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		} catch (NameNotFoundException e) {
			ACRA.getErrorReporter().handleException(e);
			e.printStackTrace();
		}
		return content;
	}
	
	public static void emailPurchase(Context context) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		String emailList[] = { context.getString(R.string.companyEmail) };
		intent.putExtra(Intent.EXTRA_EMAIL, emailList);
		intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.emailSubjectPurchase));
		intent.putExtra(android.content.Intent.EXTRA_TEXT, createAppInfo (context));
		context.startActivity(intent);
	}

	public static void companySite(Activity activity) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(activity.getString(R.string.companyUrl)));
		activity.startActivity(intent);
	}

	public static void emailDb(Activity activity, String email, String sdPath) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("application/octet-stream");
		String emailList[] = { email };
		intent.putExtra(Intent.EXTRA_EMAIL, emailList);
		String subject = String.format(activity.getString(R.string.emailSubjectDatabase), activity.getString(R.string.aadhk_app_name));
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		Uri localUri = Uri.parse("file://" + sdPath);
		intent.putExtra(Intent.EXTRA_STREAM, localUri);
		activity.startActivity(intent);
	}

	public static void emailFile(Context context, String type, String email, String subject, String file) {
		String[] emailList = { email };
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType(type);
		intent.putExtra(Intent.EXTRA_EMAIL, emailList);
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file));
		context.startActivity(intent);
	}

	public static void emailFile(Context context, String type, String email, String subject, Uri uri) {
		String[] emailList = { email };
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType(type);
		intent.putExtra(Intent.EXTRA_EMAIL, emailList);
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_STREAM, uri);
		context.startActivity(intent);
	}

	public static void localeAction(Activity activity, Locale newLocale) {
		Configuration conf = new Configuration();
		conf.locale = newLocale;
		Locale.setDefault(newLocale);
		activity.getBaseContext().getResources().updateConfiguration(conf, activity.getBaseContext().getResources().getDisplayMetrics());

		Intent intent = activity.getIntent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		activity.finish();
		activity.startActivity(intent);
	}

	public static void cropImage(Activity activity, String folder, String file) {
		new File(folder).mkdirs();
		File photoFile = new File(folder, file);
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("output", Uri.fromFile(photoFile));
		intent.putExtra("outputFormat", "JPEG");
		activity.startActivityForResult(intent, BaseConstant.REQUEST_CODE_IMAGE);
	}

	public static void selectImage(Activity activity) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		activity.startActivityForResult(intent, BaseConstant.REQUEST_CODE_IMAGE);
	}

	public static void openPdf(Activity activity, String pdfPath) {
		File file = new File(pdfPath);
		PackageManager packageManager = activity.getPackageManager();
		Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
		pdfIntent.setType("application/pdf");
		List list = packageManager.queryIntentActivities(pdfIntent, PackageManager.MATCH_DEFAULT_ONLY);
		if (list.size() > 0) {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			Uri uri = Uri.fromFile(file);
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			intent.setDataAndType(uri, "application/pdf");
			activity.startActivity(intent);
		} else {
			Toast.makeText(activity, R.string.msgNoPdfReader, Toast.LENGTH_LONG).show();
		}
	}

	public static void openAnotherApp(Context context, String packageName) {
		final PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
		List<String> pName = new ArrayList<String>();
		if (pinfo != null) {
			for (int i = 0; i < pinfo.size(); i++) {
				String pn = pinfo.get(i).packageName;
				pName.add(pn);
			}
		}
		Intent intent = null;
		if (pName.contains(packageName)) {
			intent = packageManager.getLaunchIntentForPackage(packageName);
		} else {
			intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("market://details?id=" + packageName));
		}
		if (intent == null) {
			Toast.makeText(context, R.string.not_app_store, Toast.LENGTH_LONG).show();
		} else {
			context.startActivity(intent);
		}
	}
}
