package com.aadhk.kds.util;

import java.lang.reflect.Method;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.aadhk.product.util.BasePreferenceUtil;


public class PreferenceUtil extends BasePreferenceUtil {
	private Context context;

	public PreferenceUtil(Context context) {
		super(context);
		this.context = context;
	}

	public int getRedMinutes() {
		return preference.getInt(Constant.PREF_KEY_REDMINUTES, 15);
	}

	public int getGreenMinutes() {
		return preference.getInt(Constant.PREF_KEY_GREENMINUTES, 10);
	}

	public int getYellowMinutes() {
		return preference.getInt(Constant.PREF_KEY_YELLOWMINUTES, 5);
	}

	public int getBlueMinutes() {
		return preference.getInt(Constant.PREF_KEY_BLUEMINUTES, 0);
	}

	public int getFontSize() {
		if (isTabletDevice()) {
			//Log.d("XXX", "is Pad ");
			return preference.getInt(Constant.PREF_KEY_FONTSIZE, 26);
		} else {
			//Log.d("XXX", "is not Pad ");
			return preference.getInt(Constant.PREF_KEY_FONTSIZE, 18);
		}
	}

	public int getRefresh() {
		return preference.getInt(Constant.PREF_KEY_REFRESH, 5);
	}
	
	/** 
	 * 判断是否为平板 
	 * @return 
	 */
	private boolean isPad() {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		// 屏幕宽度  
		float screenWidth = display.getWidth();
		// 屏幕高度  
		float screenHeight = display.getHeight();
		DisplayMetrics dm = new DisplayMetrics();
		display.getMetrics(dm);
		double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
		double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
		// 屏幕尺寸  
		double screenInches = Math.sqrt(x + y);
		// 大于6尺寸则为Pad  
		return screenInches >= 6.0;
	}

	private boolean isTabletDevice() {
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			Configuration con = context.getResources().getConfiguration();
			try {
				Method mIsLayoutSizeAtLeast = con.getClass().getMethod("isLayoutSizeAtLeast", int.class);
				Boolean r = (Boolean) mIsLayoutSizeAtLeast.invoke(con, 0x00000004); // Configuration.SCREENLAYOUT_SIZE_XLARGE
				return r;
			} catch (Exception x) {
				x.printStackTrace();
				return false;
			}
		}
		return false;
	}

	public boolean isFullFormat() {
		return preference.getBoolean(Constant.PREF_KEY_FULL_FORMAT, true);
	}
}
