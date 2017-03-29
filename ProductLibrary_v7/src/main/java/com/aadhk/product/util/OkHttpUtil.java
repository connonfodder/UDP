package com.aadhk.product.util;

import android.text.TextUtils;

import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

//https://github.com/square/okhttp/wiki/Recipes
//https://github.com/wtsky1111/okhttputil/blob/master/OkHttp/okhttp_sky/src/main/java/com/wt_sky/okHttp/network/OkHttpHelper.java
public class OkHttpUtil {
	final MediaType TYPE_JSON= MediaType.parse("application/json; charset=utf-8");
	private OkHttpClient okhttpclient;
	private static OkHttpUtil okHttpUtil;
	private static final int CONNECTION_TIMEOUT = 20; //10
	private static final int WRITE_TIMEOUT = 60; //30
	private static final int READ_TIMEOUT = 60; //30

	private OkHttpUtil() {
		okhttpclient = new OkHttpClient();
		ConfigureTimeouts();
	}
    public static synchronized OkHttpUtil getInstance() {
        if (okHttpUtil == null) {
        	okHttpUtil = new OkHttpUtil();
        }
        return okHttpUtil;
    }
	public void setAuthenticator(){
		okhttpclient.setAuthenticator(new Authenticator() {

			@Override
			public Request authenticate(java.net.Proxy proxy, Response response) throws IOException {
				 String credential = Credentials.basic("jesse", "password1");
			        return response.request().newBuilder()
			            .header("Authorization", credential)
			            .build();
			}
			@Override
			public Request authenticateProxy(java.net.Proxy proxy, Response response) throws IOException {
				return null;
			} 
		    });
	}
	public void setCookie(CookieManager cookieManager) {
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        okhttpclient.setCookieHandler(cookieManager);
    }
	public void ConfigureTimeouts(){
		okhttpclient.setConnectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
		okhttpclient.setWriteTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
		okhttpclient.setReadTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
	}
	public String get(String url) throws IOException {
		  Request request = new Request.Builder()
		      .url(url)
		      .build();

		  Response response = okhttpclient.newCall(request).execute();
		  if (response.isSuccessful()) {
		        return response.body().string();
		  } else {
		        throw new IOException("Unexpected code " + response);
		  }
	}
	public String postJson(String url, String json) throws IOException {
		RequestBody body = RequestBody.create(TYPE_JSON, json);
		Request request = new Request.Builder()
			.url(url)
			.post(body).build();
		Response response = okhttpclient.newCall(request).execute();
		if (response.isSuccessful()) {
	        return response.body().string();
	    } else {
	        throw new IOException("Unexpected code " + response);
	    }
	}
	//postValuePair("www.google.com",new String["username","abc"],new String["password","123"]);
	public String postValuePair(String url,String[]... namevaluepairs) throws IOException{
		
		FormEncodingBuilder formbuilder = new FormEncodingBuilder();
		for(String[] namevaluepair:namevaluepairs){
			if(!TextUtils.isEmpty(namevaluepair[0])&& !TextUtils.isEmpty(namevaluepair[1])){
				formbuilder.add(namevaluepair[0], namevaluepair[1]);
			}
		}
		RequestBody body = formbuilder.build();
		Request request = new Request.Builder()
			.url(url)
			.post(body).
			build();
		Response response = okhttpclient.newCall(request).execute();
		if (response.isSuccessful()) {
	        return response.body().string();
	    } else {
	        throw new IOException("Unexpected code " + response);
	    }
	}
	
}
