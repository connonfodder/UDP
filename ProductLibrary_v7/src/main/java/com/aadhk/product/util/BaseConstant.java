package com.aadhk.product.util;



public class BaseConstant {

	// database preference
	public final static String PREF_BACKUP = "prefBackup";
	public final static String PREF_RESTORE = "prefRestore";
	public final static String PREF_RESTORE_DEMO = "prefRestoreDemo";
	public final static String PREF_DELETE = "prefDeleteAll";
	public final static String PREF_AUTODELETE = "prefAutoDelete";
	public final static String PREF_AUTODROPBOXBACKUP = "prefAutoBackupDropBox";
	public final static String PREF_LINK_DROPBOX = "prefLinkDropBox";
	public final static String PREF_AUTOBACKUP_SD = "prefAutoBackup";
	public final static String PREF_EMAIL_DB = "prefEmailDb";
	public final static String PREF_EMAIL = "prefEmail";
	public final static String PREF_CSV = "prefCsv";
	public final static String PREF_AUTOBACKUP = "prefAutoBackup";
	public final static String PREF_UPGRADE = "prefUpgrade";
	public final static String PREF_REGITER = "prefRegister";
	public final static String PREF_EMAIL_US = "prefEmailUs";
	public final static String PREF_SUPPORT = "prefSupport";

	public final static String PREF_CURRENCY = "prefCurrency";
	public final static String PREF_CURRENCY_CODE = "prefCurrencyCode";
	public final static String PREF_CURRENCY_SIGN = "prefCurrencySign";
	public final static String PREF_LANG_SYS = "prefLangSys";
	public final static String PREF_PASSWORD = "prefPassword";
	public final static String PREF_EMAIL_DEF = "prefEmailDef";
	public final static String PREF_LOG = "prefLog";
	public final static String PREF_BUY = "prefBuy";
	public final static String PREF_APP_INFO = "appInfo";
	
	// server status
	public static final String SERVICE_STATUS = "serviceStatus";
	public static final String SERVICE_DATA = "serviceData";
	public static final String STATUS_SUCCESS = "1";
	public static final String SERVER_FAIL = "9";
	public static final String SERVER_REGISTER_FAIL = "90";
	public static final String SERVER_DELETE_ERROR = "91";
	public static final String SERVER_HOST_ERROR = "92";

	//license
	public static final String PREF_LICENSE_INSTALLED_DATE = "licenseInstalledDate";
	public static final String PREF_LICENSE_USER_NAME = "licenseUserName";
	public static final String PREF_LICENSE_EMAIL = "licenseEmail";
	public static final String PREF_LICENSE_PHONE = "licensePhone";
	public static final String PREF_LICENSE_ACTIVATION_KEY = "licenseActivationKey";
	public static final String PREF_LICENSE_KEY = "licenseKey";
	public static final String PREF_LICENSE_SERIAL_NUMBER = "licenseSerialNumber";
	public static final String PREF_LICENSE_ITEM_ID = "licenseItemId";
	public static final String PREF_LICENSE_DEVICE_MODEL = "licenseDeviceModel";
	public static final String PREF_LICENSE_LOCALE = "licenseDeviceLocale";
	public static final String PREF_LICENSE_DEVICE_SERIAL = "licenseDeviceSerial";
	public static final String PREF_LICENSE_DEVICE_MACADDRESS = "licenseDeviceMacAddress";
	public static final String PREF_LICENSE_PURCHASE_TYPE = "licensePurchaseType";

	// action type
	public static final int ACTION_ADD = 1;
	public static final int ACTION_UPDATE = 2;
	public static final int ACTION_DELETE = 3;

	// bundle
	public static final String BUNDLE_CURRENCY = "currency";
	public static final String BUNDLE_CURRENCY_POSITION = "position";

	// for result activity
	public final static int ACTIVITY_REQUEST_CURRENCY = 10;
	public static final int ACTIVITY_REQUEST_PICK_CONTACT = 2;
	public static final int REQUEST_CODE_IMAGE = 200;

	// database preference default
	public final static boolean PRE_DEF_AUTOBACKUP_SD = true;
	public final static boolean PRE_DEF_AUTOBACKUP_DROPBOX = false;

	public final static int DATABASE_BACKUP_LIMIT = 2;

	// File location
	public final static int LOC_SDCARD = 0;
	public final static int LOC_GOOGLEDRIVE = 1;
//	public final static int LOC_DROPBOX = 1;

	// setting
	public final static String PREF_PERIOD = "prefPeriod";
	public final static String PREF_DATE_FORMAT = "prefDateFormat";
	public final static String PREF_DATE_FORMAT_POSITION = "prefDateFormatPosition";
	public final static String PREF_LANG = "prefLang";
	public final static String PREF_FIRST_DAY_WEEK = "prefFirstDayofWeek";
	public final static String PREF_DATE = "prefDate";
	public final static String PREF_TIME = "prefTime";
	public final static String PREF_DEFAULT = "prefDefault";
	public final static String PREF_RECEIPT_EMAIL = "prefReceiptEmail";

	// calendar
	public static final String PEROID_TYPE = "period_type";
	public static final int PEROID_YEAR = 1;
	public static final int PEROID_MONTH = 2;
	public static final int PEROID_WEEK = 3;
	public static final int PEROID_DAY = 4;
	public static final int PEROID_CUSTOM = 5;

	// preference default 
	public final static String PREF_DEF_DATE_TIME = "yyyy-MM-dd HH:mm";
	public final static String PREF_DEF_DATE_TIME_SECOND = "yyyy-MM-dd HH:mm:ss";
	public final static String PREF_DEF_DATE = "yyyy-MM-dd";
	public final static String PREF_DEF_DATE_YYYYMMDD = "yyyyMMdd";
	public final static String PREF_DEF_MONTH_DAY_YEAR = "MMM d, yyyy";
	public final static String PREF_DEF_MONTH_YEAR = "MMM yyyy";
	public final static String PREF_DEF_MONTH_DAY = "MMM d";
	public final static String PREF_DEF_YEAR = "yyyy";
	public final static String FILE_DATE_FORMAT = "yyyy_MM_dd";
	public final static String DATE_FORMAT_NO_YEAR = "MM-dd";
	public final static String TIME_FORMAT_24 = "HH:mm";
	public final static String TIME_FORMAT_12 = "hh:mm a";
	public final static String TIME_FORMAT_NO_YEAR = "MM-dd HH:mm";
	public final static String TIME_FORMAT_24_SECOND = "HH:mm:ss";
	public final static String TIME_FORMAT_SECOND = "hh:mm:ss a";
	public final static String TIME_FORMAT = "hh:mm a";
	public final static String TIME_FORMAT_LOGIN = "MMM d. EEE";
	public final static String TIME_FORMAT_PUNCH = "yyyy-MM-dd hh:mm a";
	public final static boolean PRE_DEF_AUTOBACKUP = true;
	public final static String PREF_DEF_CURRENCY_SIGN = "$";
	public final static String PREF_DEF_CURRENCY_CODE = "USD";
	public final static int PREF_DEF_PERIOD = PEROID_DAY;
	
	//auto backup
//	public static final String AUTO_BACKUP = "autoBackup";
	public static final String AUTO_DELETE_BACKUP = "autoDeleteBackup";
	public static final String BACKUP_TIME_CLOCK = "backupTime_clock";
	public static final String BACKUP_TIME_PERIOD = "backupTime_period";
	public static final String BACKUP_LATEST_TIME = "backupLatestTime";
	public static final String KEEP_DAY = "keepDay";
	public static final String DAY_NUM_DEFAULT = "1";
	public static final String BACKUP_TIME_DEFAULT = "00:00";
	public final static boolean BACKUP_DEFAULT = true;
	public final static String BACKUP_MODEL = "backup_model";
	public final static String SB_PERIOD_MONTH = "sbPeriodMonth";
	public final static String SB_PERIOD_DAY = "sbPeriodDay";
	public final static String SB_PERIOD_HOUR = "sbPeriodHour";
}
