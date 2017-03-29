package com.aadhk.product.util;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.aadhk.product.library.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class ImageLoaderUtil {
	private static ImageLoader imageLoader;
	public static void imageLoader(Context context,String filePath,final ImageView image){
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));// 在使用前要初始化
		imageLoader.displayImage("file://" + filePath, image, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				image.setImageResource(R.drawable.no_media);
				super.onLoadingStarted(imageUri, view);
			}
		});
	}
}
