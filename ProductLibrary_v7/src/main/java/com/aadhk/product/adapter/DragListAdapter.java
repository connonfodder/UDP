package com.aadhk.product.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

public abstract class DragListAdapter extends BaseAdapter {
	private static final String TAG = "DragListAdapter";

	public Context context;
	public LayoutInflater layoutInflater;
	public Resources resources;
	public int gonePos = -1;

	public DragListAdapter(Context context) {
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		resources = context.getResources();
	}

	public void setViewInvisible(int position) {
		this.gonePos = position;
		notifyDataSetChanged();
	}

	public void setViewVisible() {
		gonePos = -1;
		notifyDataSetChanged();
	}

	public abstract void move(int oriPosition, int toPosition);

	public abstract void drop();

}
