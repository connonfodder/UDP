/*******************************************************************************
 * Copyright (C) Hong Kong Android Technology Co.
 * All right reserved.
 ******************************************************************************/
package com.aadhk.product.bean;

public class License {

	//user info
	private String userName;
	private String email;
	private String phone;
	private String website;

	//device info
	private String serialNumber;
	private String deviceModel;
	private String locale;

	//install info
	private String installedDate;

	//purchase info
	private int purchaseId;

	//license info
	private String activationKey;
	private String item;
	//0=basic, 1=advance, 2=premium
	private int purchaseType;

	//check serial code or mac address
	private String deviceSerial;
	private String deviceMacAddress;

	private boolean enable;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getInstalledDate() {
		return installedDate;
	}

	public void setInstalledDate(String installedDate) {
		this.installedDate = installedDate;
	}

	public int getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}

	public String getActivationKey() {
		return activationKey;
	}

	public void setActivationKey(String activationKey) {
		this.activationKey = activationKey;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getDeviceSerial() {
		return deviceSerial;
	}

	public void setDeviceSerial(String deviceSerial) {
		this.deviceSerial = deviceSerial;
	}

	public String getDeviceMacAddress() {
		return deviceMacAddress;
	}

	public void setDeviceMacAddress(String deviceMacAddress) {
		this.deviceMacAddress = deviceMacAddress;
	}

	public int getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(int purchaseType) {
		this.purchaseType = purchaseType;
	}

	@Override
	public String toString() {
		return "License{" +
				"userName='" + userName + '\'' +
				", email='" + email + '\'' +
				", phone='" + phone + '\'' +
				", website='" + website + '\'' +
				", serialNumber='" + serialNumber + '\'' +
				", deviceModel='" + deviceModel + '\'' +
				", locale='" + locale + '\'' +
				", installedDate='" + installedDate + '\'' +
				", purchaseId=" + purchaseId +
				", activationKey='" + activationKey + '\'' +
				", item='" + item + '\'' +
				", purchaseType=" + purchaseType +
				", deviceSerial='" + deviceSerial + '\'' +
				", deviceMacAddress='" + deviceMacAddress + '\'' +
				", enable=" + enable +
				'}';
	}

}
