package com.aadhk.kds;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Message;
import android.os.StrictMode;
import android.preference.PreferenceManager;

import com.aadhk.kds.util.Config;
import com.aadhk.kds.util.Constant;
import com.aadhk.kds.util.LocaleUtil;
import com.aadhk.kds.util.PreferenceUtil;
import com.aadhk.product.util.BaseConstant;
import com.aadhk.product.util.HockeySender;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

//@ReportsCrashes(formUri = "https://collector.tracepot.com/9d0fec86")
@ReportsCrashes(formUri = Config.HOCKEYAPP_APPID)
public class POSApp extends Application {
    private static final String TAG = "POSApp";
    private static POSApp instance;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        setLocale();
        super.onConfigurationChanged(newConfig);
    }

    public static POSApp getInstance() {
        return instance;
    }

    private KitchenActivity mActivity;

    public void setKitchenActivity(KitchenActivity activity) {
        this.mActivity = activity;
    }

    public void sendMsg(UDPMessage msg) {
        if (mActivity == null) return;  //防止内存泄漏
        Message m = new Message();
        m.obj = msg;
        m.what = Constant.STATUS_RECEIVE_DATA;
        this.mActivity.sendMessage(m);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = this;
        //日志文件
//		CrashHandler crashHandler = CrashHandler.getInstance();
//		crashHandler.init(this, Constant.SDFOLDER_CRASH_FILE_NAME);

        if (Config.DEVELOPER_VERSION) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork() // or .detectAll() for all detectable problems
                    .penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        }

        // The following line triggers the initialization of ACRA
        ACRA.init(this);
        ACRA.getErrorReporter().setReportSender(new HockeySender());

        //Instabug.initialize(this, "3b453a48845782c018de3f1e59b6d6b9");

        // initial language from device locale, save to preference
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);
        String prefLan = preference.getString(BaseConstant.PREF_LANG, "");
        //Log.i(TAG, "===>prefLan:"+prefLan);
        SharedPreferences.Editor editor = preference.edit();
        String defaultLang = Locale.getDefault().toString();
        //Log.i(TAG, "===>defaultLang:"+defaultLang);
        editor.putString(BaseConstant.PREF_LANG_SYS, defaultLang);
        if (prefLan.equals("")) {
            int appLang = LocaleUtil.getLangValue(Locale.getDefault().toString());
            editor.putString(BaseConstant.PREF_LANG, appLang + "");
        }
        editor.commit();
        setLocale();
    }

    public void setLocale() {
        // check language, and set timesheet language from preference. timesheet
        // language may be different from device.
        // disable use can choose language: prevent time picker error.

        PreferenceUtil prefUtil = new PreferenceUtil(this);
        String lang = LocaleUtil.getLang(prefUtil.getLang());
        String localeValue[] = lang.split("_");
        Locale newLocale = new Locale(localeValue[0], localeValue[1]);
        Configuration config = getBaseContext().getResources().getConfiguration();
        if (!config.locale.equals(newLocale)) {
            config.locale = newLocale;
            Locale.setDefault(newLocale);
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }
    }

    private Map<String, Integer> versionSendRecord = new HashMap<>(6);      //发送版本
    private Map<String, Integer> versionReceiveRecord = new HashMap<>(6);   //接受版本

    /**
     * 用来比较传递数据的version对照, 如果不成功则重新发送数据
     * 基本思路: 每台设备对应每台设备都维护一个发送和接受version 初始化为0, 后期每次操作都需要自增1
     * 例如从POS发送数据到KDS, 其发送version+1, 发送后与KDS的接受version做比较,如果相差1 那么中间没有断掉联系
     * 否则表示中间发送操作失败过一次重新发送全部的数据, 发送version=1
     * <p>
     * 由于俩个设备都是独立的, 不受影响的, 所以这里需要维护俩个version记录表表示独立的发送与接收
     * <p>
     * 基本上 发送端在发送时 version+1,  接收方 version+1
     *
     * @param ip
     * @param version
     */
    public boolean compareReceiveVersion(String ip, int version) {
        if (versionReceiveRecord.containsKey(ip)) {
            int localVersion = versionReceiveRecord.get(ip);
            if (localVersion == version - 1) {
                versionReceiveRecord.put(ip, version);     //1. 成功校验
                return true;
            }
        } else {
            if (version == 1) {
                versionReceiveRecord.put(ip, version);     //2. 第一次接受全部数据
                return true;
            }
        }
        return false;
    }

    /**
     * 获取发送version
     *
     * @param ip
     * @return
     */
    public int getSendVersion(String ip) {
        int localVersion = 0;
        if (versionSendRecord.containsKey(ip)) {
            localVersion = versionSendRecord.get(ip);
        }
        versionSendRecord.put(ip, ++localVersion);
        return localVersion;
    }
}
