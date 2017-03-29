package com.aadhk.kds.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aadhk.kds.KitchenActivity;
import com.aadhk.kds.R;
import com.aadhk.kds.bean.Order;
import com.aadhk.kds.bean.OrderItem;
import com.aadhk.kds.util.PreferenceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PopupListAdapter extends BaseAdapter {
	private List<OrderItem> list;
	private KitchenActivity activity;
	private int total = 0;
	private PreferenceUtil preferenceUtil;

	public PopupListAdapter(Context context, List<Order> listOrder) {
		activity = (KitchenActivity) context;
		preferenceUtil = new PreferenceUtil(activity);
		Map<Long, OrderItem> map = new HashMap<Long, OrderItem>();
		map.clear();
		for (Order order : listOrder) {
			for (OrderItem item : order.getOrderItems()) {
				if (item.getCategoryName() == null || item.getItemName() == null) {
					//filter fake data 
				} else {
					Long key = item.getItemId();
					if (map.containsKey(key)) {
						OrderItem tem = map.get(key).clone();
//						OrderItem tem = map.get(key); //tem为变量，只是指向了该实例的地址，并没有重新复制
						map.remove(key);
						tem.setQty(item.getQty() + tem.getQty());
						map.put(key, tem);
					} else {
						map.put(key, item);
					}
				}
			}
		}
		list = new ArrayList<OrderItem>();
		for (Entry<Long, OrderItem> entry : map.entrySet()) {
			OrderItem t = entry.getValue();
			total += t.getQty();
			list.add(t);
		}
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(activity).inflate(R.layout.popup_listview, null);
			holder = new ViewHolder();
			holder.itemName = (TextView) convertView.findViewById(R.id.tvItemName);
			holder.itemQty = (TextView) convertView.findViewById(R.id.tvItemQty);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		OrderItem tem = (OrderItem) getItem(position);
		holder.itemName.setText(tem.getItemName());
		holder.itemQty.setText((int)tem.getQty() + "");
		float size = preferenceUtil.getFontSize();
		holder.itemName.setTextSize(size);
		holder.itemQty.setTextSize(size);
		return convertView;
	}

	private class ViewHolder {
		TextView itemName;
		TextView itemQty;
	}

	public int getItemTotalSize() {
		return total;
	}
}
