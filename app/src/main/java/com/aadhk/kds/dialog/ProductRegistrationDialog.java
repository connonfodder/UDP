/*******************************************************************************
 * Copyright (C) Hong Kong Android Technology Co.
 * All right reserved.
 ******************************************************************************/
package com.aadhk.kds.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aadhk.kds.R;
import com.aadhk.kds.util.Config;
import com.aadhk.product.bean.License;
import com.aadhk.product.util.KDSIntentUtil;
import com.aadhk.product.util.PatternValidate;

public class ProductRegistrationDialog extends KDSDialog implements OnClickListener {

	private static final String TAG = "ProductRegistrationDialog";
	private Button btnRegister, btnBuy;
	private TextView lbLicense, tvDeviceId, tvLicenseMsg;
	private EditText etKey, etUserName, etPhone, etEmail, etWebsite;
	private OnUnlockListener onUnlockListener;
	private OnBackListener onBackListener;
	private License license;

	public ProductRegistrationDialog(Context context, License license) {
		super(context, R.layout.dialog_product_registration);
		setTitle(R.string.dlgTitleRegistration);
		setCancelable(false);
		this.license = license;
		setupView();
	}

	@Override
	public void onClick(View v) {
		if (v == btnRegister) {
			registerProduct();
		} else if (v == btnBuy) {
			if (Config.TRIAL_VERSION) {
				KDSIntentUtil.purchaseWebsite((Activity) context);
			} else {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("market://" + resources.getString(R.string.payUrl)));
				context.startActivity(intent);
			}
		}
	}

	private void registerProduct() {
		if (validate()) {
			if (onUnlockListener != null) {
				onUnlockListener.onUnlock(license);
			}
		}
	}

	@Override
	public void onBackPressed() {
		dismiss();
		if (onBackListener != null) {
			onBackListener.onBack();
		}
	}

	public void setOnBackListener(OnBackListener onBackListener) {
		this.onBackListener = onBackListener;
	}

	public interface OnBackListener {
		void onBack();
	}

	private boolean validate() {
		String key = etKey.getText().toString();
		String userName = etUserName.getText().toString();
		String email = etEmail.getText().toString();
		String phone = etPhone.getText().toString();
		String website = etWebsite.getText().toString();

		if (TextUtils.isEmpty(key)) {
			etKey.setError(resources.getString(R.string.errorEmpty));
			etKey.requestFocus();
			return false;
		} else {
			etKey.setError(null);
		}

		if (TextUtils.isEmpty(userName)) {
			etUserName.setError(resources.getString(R.string.errorEmpty));
			etUserName.requestFocus();
			return false;
		} else {
			etUserName.setError(null);
		}

		if (TextUtils.isEmpty(email)) {
			etEmail.setError(resources.getString(R.string.errorEmpty));
			etEmail.requestFocus();
			return false;
		} else {
			etEmail.setError(null);
		}

		if (!email.equals("") && !PatternValidate.EMAIL_ADDRESS.matcher(email).matches()) {
			etEmail.setError(resources.getString(R.string.errorEmailFormat));
			etEmail.requestFocus();
			return false;
		} else {
			etEmail.setError(null);
		}

		license.setActivationKey(key);
		license.setUserName(userName);
		license.setEmail(email);
		license.setPhone(phone);
		license.setWebsite(website);

		return true;
	}

	private void setupView() {
		btnRegister = (Button) findViewById(R.id.btnRegister);
		btnRegister.setOnClickListener(this);
		btnBuy = (Button) findViewById(R.id.btnBuy);
		btnBuy.setOnClickListener(this);

		tvDeviceId = (TextView) findViewById(R.id.tvDeviceId);
		tvLicenseMsg = (TextView) findViewById(R.id.tvLicenseMsg);
		etKey = (EditText) findViewById(R.id.etKey);
		etUserName = (EditText) findViewById(R.id.etUserName);
		etEmail = (EditText) findViewById(R.id.etEmail);
		etPhone = (EditText) findViewById(R.id.etPhone);
		etWebsite = (EditText) findViewById(R.id.etWebsite);

		etKey.setText(license.getActivationKey());
		etUserName.setText(license.getUserName());
		etPhone.setText(license.getPhone());
		etEmail.setText(license.getEmail());
		etWebsite.setText(license.getWebsite());

		tvDeviceId.setText(resources.getString(R.string.serialNumber) + " " + license.getSerialNumber());

/*		if (Config.TRIAL_VERSION) {
			btnBuy.setVisibility(View.GONE);
		}*/

		if (!TextUtils.isEmpty(license.getActivationKey())) {
			btnRegister.setVisibility(View.GONE);
			btnBuy.setVisibility(View.GONE);
			etKey.setEnabled(false);
			etUserName.setEnabled(false);
			etPhone.setEnabled(false);
			etEmail.setEnabled(false);
			etWebsite.setEnabled(false);

			setTitle(R.string.dlgTitleRegisted);

			tvLicenseMsg.setText(R.string.licenseRegisteredMsg);

			//			String message = String.format(resources.getString(R.string.registedContent), serialId);
			//			lbLicense.setText(message);
		}
	}

	public void setonUnlockListener(OnUnlockListener onUnlockListener) {
		this.onUnlockListener = onUnlockListener;
	}

	public interface OnUnlockListener {
		void onUnlock(License license);
	}
}
