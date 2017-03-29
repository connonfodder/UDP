package com.aadhk.kds.util;

import java.util.Comparator;

import com.aadhk.kds.bean.OrderItem;

/*
 * 菜式排序
 */
public class ComparatorOrderItem implements Comparator<OrderItem> {

	@Override
	public int compare(OrderItem arg0, OrderItem arg1) {
		return arg0.getItemName().compareTo(arg1.getItemName());
	}
}
