package com.aadhk.kds.service;

import java.util.Date;
import java.util.Map;

import org.acra.ACRA;

import android.content.Context;
import android.widget.Toast;

import com.aadhk.billing.IabHelper;
import com.aadhk.billing.Purchase;
import com.aadhk.kds.R;
import com.aadhk.kds.dialog.ProductRegistrationDialog;
import com.aadhk.kds.util.Config;
import com.aadhk.kds.util.Constant;
import com.aadhk.kds.util.PreferenceUtil;
import com.aadhk.license.LicenseException;
import com.aadhk.license.LicenseManager;
import com.aadhk.product.asyn.TaskAsyncCallBack;
import com.aadhk.product.bean.License;
import com.aadhk.product.service.LicenseService;

public class TaskRegisterLicense implements TaskAsyncCallBack {
	private Map<String, Object> taskResult;
	private License license;
	private Context context;
	private LicenseService licenseService;
	private PreferenceUtil prefUtil;
	private ProductRegistrationDialog dialog;

	public TaskRegisterLicense(Context context, License license, ProductRegistrationDialog dialog) {
		this.license = license;
		this.context = context;
		this.dialog = dialog;
		licenseService = new LicenseService();
		prefUtil = new PreferenceUtil(context);
	}

	@Override
	public void setupData() {
		taskResult = licenseService.register(license);
	}

	@Override
	public void showView() {
		String status = (String) taskResult.get(Constant.SERVICE_STATUS);
		if (Constant.STATUS_SUCCESS.equals(status)) {
			// save license to preference
			License license = (License) taskResult.get(Constant.SERVICE_DATA);
			prefUtil.saveLicense(license);

			//must need
			Purchase purchase = new Purchase(IabHelper.ITEM_TYPE_INAPP, Config.ORDER_ID_FULL, Config.PRODUCT_FULL, (new Date()).getTime());
			try {
				LicenseManager.addPurchase(context, purchase);
				dialog.dismiss();
			} catch (LicenseException e) {
				ACRA.getErrorReporter().handleException(e);
				e.printStackTrace();
			}
		} else if (Constant.SERVER_REGISTER_FAIL.equals(status)) {
			Toast.makeText(context.getApplicationContext(), context.getString(R.string.errorKey), Toast.LENGTH_SHORT).show();
		} else if (Constant.SERVER_FAIL.equals(status)) {
			Toast.makeText(context, R.string.errorServerExcetpion, Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(context, R.string.errorServer, Toast.LENGTH_LONG).show();
		}
	}
}
