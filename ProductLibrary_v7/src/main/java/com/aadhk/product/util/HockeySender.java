package com.aadhk.product.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Point;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.WindowManager;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

public class HockeySender implements ReportSender {
    private static final String BASE_URL = "https://rink.hockeyapp.net/api/2/apps/";
    private static final String CRASHES_PATH = "/crashes";

    private String createCrashLog(CrashReportData report, Context context) {
        Date now = new Date();
        StringBuilder log = new StringBuilder();

        log.append("Package: ").append(report.get(ReportField.PACKAGE_NAME)).append("\n");
        log.append("Version: ").append(report.get(ReportField.APP_VERSION_CODE)).append("\n");
        log.append("Android: ").append(report.get(ReportField.ANDROID_VERSION)).append("\n");
        log.append("Manufacturer: ").append(android.os.Build.MANUFACTURER).append("\n");
        log.append("Model: ").append(android.os.Build.MODEL).append("\n");

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        String prefLan = preference.getString(BaseConstant.PREF_LANG, "");
        log.append("display: ").append(size.x+"x"+size.y).append("\n");
        log.append("Default Locale: ").append(Locale.getDefault()+", choose locale:"+prefLan).append("\n");

        //Determine screen size
        String layout;
        if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            layout = "XLarge screen";
        }
        else if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            layout = "Large screen";
        }
        else if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            layout = "Normal sized screen";
        }
        else if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            layout = "Small sized screen";
        }
        else {
            layout = "\"Screen size is neither large, normal or small";
        }

        log.append("layout: ").append(layout).append("\n");

        log.append("Date: ").append(now).append("\n");
        log.append("\n");
        log.append("custom data:"+report.get(ReportField.CUSTOM_DATA));
        log.append("\n");
        log.append(report.get(ReportField.STACK_TRACE));

        return log.toString();
    }

    @Override
    public void send(Context arg0, CrashReportData report) throws ReportSenderException {
        String log = createCrashLog(report, arg0);
        String url = BASE_URL + ACRA.getConfig().formUri() + CRASHES_PATH;

        try {
            OkHttpUtil.getInstance().postValuePair(url,new String[]{"raw", log},new String[]{"userID",report.get(ReportField.INSTALLATION_ID)},new String[]{"contact",report.get(ReportField.USER_EMAIL)},new String[]{"description",report.get(ReportField.USER_COMMENT)});
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
