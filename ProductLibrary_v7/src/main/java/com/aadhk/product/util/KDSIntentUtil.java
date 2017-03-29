package com.aadhk.product.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Administrator on 2016/11/8.
 */

public class KDSIntentUtil extends IntentUtil {
    public static void purchaseWebsite(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://wnopos.com/android-pos-kds.html"));
        activity.startActivity(intent);
    }
}
