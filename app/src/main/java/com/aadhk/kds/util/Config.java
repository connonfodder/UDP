/*******************************************************************************
 * Copyright (C) Hong Kong Android Technology Co.
 * All right reserved.
 ******************************************************************************/
package com.aadhk.kds.util;

public class Config {
	
	/*
	 * base64EncodedPublicKey should be YOUR APPLICATION'S PUBLIC KEY (that you got from the Google Play developer console). This is not your developer public key, it's the *app-specific* public key.
	 * 
	 * Instead of just storing the entire literal string here embedded in the program, construct the key at runtime from pieces or use bit manipulation (for example, XOR with some other string) to hide the actual key. The key itself is not secret information, but we don't want to make it easy for an attacker to replace the public key with one of their own and then fake messages from the server.
	 */
	public static final String BASE64_ENCODE_PUBLIC_KEY = "[APP_PUBLIC_KEY]";
	//In-app KDS
//	public static final String BASE64_ENCODE_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAliMFH+SxLOi8sOrVnWZw4M14IUBdc63jvXhvru4rj1vaSr0Ub6msFXzba8L3pkDzvRU6Jaui6wPrbw8HkwWd7bDt1ftwqIP6XmIwJ6zSFreZpe1NARvd4GDb21XqH/NzGxr8ja2XYsL5yav8MeJRxBZ5hjJvzpUGBihkRhbjK8Yz98M6ZDfbINEQawbgTSMnnkI0RBmLEq+AnfNVdmQWAA8QsbbcOGlt4adXQMGjjxkvuzx6Scwvp/03FcKO1ipMOwncy7//XuIs0zzvCBwE+tlPaBK8UFyVImRntazgAJgnjrz5Ia95uLnx9JW870ceASQSJquNmRqs8z3piLm+fQIDAQAB";

	public static final String PRODUCT_FULL = "com.aadhk.kds.full";
	// default orderid
	public static final String ORDER_ID_FULL = "10001";
	
	//hockeyapp
//	public static final String HOCKEYAPP_APPID = "2e29c9924a994b8d81116bc4aef91768";
	public static final String HOCKEYAPP_APPID = "[HOCKEYAPP_APPID]";

	public static final boolean DEVELOPER_VERSION = true;
	public static final boolean TRIAL_VERSION = false;
	public static final boolean CHINA_VERSION = false;
	public static final boolean INAPP_VERSION = false;
	public static final boolean PAID_VERSION = false;
	public static final boolean WHITE_LABLE_VERSION = false;

	public static final String SERVER_IP = "42.120.1.40";
}
