package com.aadhk.kds.util;

import android.os.Environment;

import com.aadhk.product.util.BaseConstant;

public class Constant extends BaseConstant {

	public static final String APP_NAME = "WnOKDS_ST";
	public static final String SDFOLDER = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Constant.APP_NAME;
	public static final String SDFOLDER_CRASH_FILE_NAME = SDFOLDER+ "/crash";

	public final static int TYPE_FONTSIZE = 1;
	public final static int TYPE_REFRESH = 2;

	public static final int TABLE_ID_DELIVERY = -1 ;

	//return key
	public static final String PREF_KEY_REDMINUTES = "redMinutes";
	public static final String PREF_KEY_GREENMINUTES = "greenMinutes";
	public static final String PREF_KEY_YELLOWMINUTES = "yellowMinutes";
	public static final String PREF_KEY_BLUEMINUTES = "blueMinutes";
	public static final String PREF_KEY_FONTSIZE = "fontSize";
	public static final String PREF_KEY_FULL_FORMAT = "prefFullHoursFormat";
	public static final String PREF_KEY_REFRESH = "refresh";

	public final static String PREF_MINUTES = "prefMinutes";
	public final static String PREF_FONTSIZE = "prefFontSize";
	public final static String PREF_REFRESH = "prefRefresh";

	public static final int REQUEST_CODE_TABLE = 1;

	public final static int MODIFIER_TYPE_NULL = 0;
	public final static int MODIFIER_TYPE_PLUS = 1;
	public final static int MODIFIER_TYPE_MINUS = 2;

	public static final int STATUS_ORDERITEM_NEW = 0;
	public static final int STATUS_ORDERITEM_CANCELITEM = 1;
	public static final int STATUS_ORDERITEM_WAIT = 2;
	public static final int STATUS_ORDERITEM_COOK = 3;
	public static final int STATUS_ORDERITEM_ORDERUP = 4;

	public static final int STATUS_RECEIVE_DATA = 1;
	public static final int STATUS_DISCONNECT = 2;

	public static final int SOCKET_KDS_PORT = 8988;
	public static final int HTTP_SERVER_PORT = 8080;
}
