package com.aadhk.kds.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.os.Parcel;

import com.aadhk.kds.bean.CompareOrder;
import com.aadhk.kds.bean.CompareOrderItem;
import com.aadhk.kds.bean.CompareOrderModifie;
import com.aadhk.kds.bean.Order;
import com.aadhk.kds.bean.OrderItem;
import com.aadhk.kds.bean.OrderModifier;
import com.aadhk.product.util.Formatter;

public class POSUtil extends Formatter {

	public static List<OrderItem> cloneOrderItemList(List<OrderItem> orderItems) {
		Parcel p = Parcel.obtain();
		p.writeList(orderItems);
		p.setDataPosition(0);
		List<OrderItem> orderItemList = p.readArrayList(OrderItem.class.getClassLoader());
		p.recycle();
		return orderItemList;
	}

	public static List<Order> cloneOrderList(List<Order> orders) {
		Parcel p = Parcel.obtain();
		p.writeList(orders);
		p.setDataPosition(0);
		List<Order> orderList = p.readArrayList(Order.class.getClassLoader());
		p.recycle();
		return orderList;
	}

	/**
	 * 思路分析: 
	 * 	进入这个方法的条件 新订单
	 * 1.Order比较
	 * 		1) 操作分为俩个, 上下整个订单, 那么数量改变, 但是没有新订单是不管比较的. 操作排除   
	 * 		        那么在没有操作的情况下, 除非加了一个新单, 也就是说在订单数量相同的情况下不需要一个一个的比较总的Order数量
	 * 
	 * 2.OrderItem & OrderModifier 的比较
	 * 		1) 比较size()
	 * 		2) 单个比较qty
	 * 		3) 比较配料Modifier
	 * 			1) 比较size()
	 * 			2) 单个比较qty
	 * @param cashList
	 * @param currList
	 * @return
	 */
	public static boolean compare(List<CompareOrder> cashList, List<Order> currList) {
		if (cashList.size() != currList.size()) {
			return false;
		}

		Map<Long, List<OrderItem>> currMap = new HashMap<Long, List<OrderItem>>();
		Map<Long, List<CompareOrderItem>> cashMap = new HashMap<Long, List<CompareOrderItem>>();
		for (Order o : currList) {
			currMap.put(o.getId(), o.getOrderItems());
		}
		for (CompareOrder o : cashList) {
			cashMap.put(o.getId(), o.getOrderItems());
		}

		for (Entry<Long, List<OrderItem>> entry : currMap.entrySet()) {
			Long key = entry.getKey();
			List<OrderItem> value = entry.getValue();
			if (cashMap.containsKey(key)) {
				//比较OrderItem
				List<CompareOrderItem> cashOMList = cashMap.get(key);
				if (!compareOrderItemList(value, cashOMList)) {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}

	private static boolean compareOrderItemList(List<OrderItem> currList, List<CompareOrderItem> cashList) {
		Map<Long, OrderItem> currMap = new HashMap<Long, OrderItem>();
		Map<Long, CompareOrderItem> cashMap = new HashMap<Long, CompareOrderItem>();
		for (OrderItem o : currList) {
			currMap.put(o.getId(), o);
		}
		for (CompareOrderItem o : cashList) {
			cashMap.put(o.getId(), o);
		}

		for (Entry<Long, OrderItem> entry : currMap.entrySet()) {
			Long key = entry.getKey();
			OrderItem curr = entry.getValue();
			if (cashMap.containsKey(key)) {
				CompareOrderItem cash = cashMap.get(key);
				if (cash.getQty() != curr.getQty()) {
					return false;
				}
				if (cash.getItemId() != curr.getItemId()) {
					return false;
				}
				List<OrderModifier> currMList = curr.getOrderModifiers();
				List<CompareOrderModifie> cashMList = cash.getOrderModifiers();
				if (currMList.size() != cashMList.size()) {
					return false;
				} else {
					if (!compareOrderModifiesList(currMList, cashMList)) {
						return false;
					}
				}
			} else {
				return false;
			}
		}
		return true;
	}

	private static boolean compareOrderModifiesList(List<OrderModifier> currList, List<CompareOrderModifie> cashList) {
		Map<Long, OrderModifier> currMap = new HashMap<Long, OrderModifier>();
		Map<Long, CompareOrderModifie> cashMap = new HashMap<Long, CompareOrderModifie>();
		for (OrderModifier o : currList) {
			currMap.put(o.getId(), o);
		}
		for (CompareOrderModifie o : cashList) {
			cashMap.put(o.getId(), o);
		}

		for (Entry<Long, OrderModifier> entry : currMap.entrySet()) {
			Long key = entry.getKey();
			OrderModifier curr = entry.getValue();
			if (cashMap.containsKey(key)) {
				CompareOrderModifie cash = cashMap.get(key);
				if (cash.getQty() != curr.getQty()) {
					return false;
				}
				if (cash.getItemid() != curr.getItemid()) {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}

	/*
	 * 赋值给缓存
	 */
	public static List<CompareOrder> getCompareOrders(List<Order> orders) {
		List<CompareOrder> compareOrders = new ArrayList<CompareOrder>();
		CompareOrder order;
		for (Order o : orders) {
			order = new CompareOrder();
			order.setId(o.getId());
			order.setOrderItems(getCompareOrderItems(o.getOrderItems()));
			compareOrders.add(order);
		}
		return compareOrders;
	}

	private static List<CompareOrderItem> getCompareOrderItems(List<OrderItem> list) {
		List<CompareOrderItem> result = new ArrayList<CompareOrderItem>();
		CompareOrderItem tem;
		for (OrderItem oi : list) {
			tem = new CompareOrderItem();
			tem.setId(oi.getId());
			tem.setOrderId(oi.getOrderId());
			tem.setItemId(oi.getItemId());
			tem.setQty(oi.getQty());
			tem.setOrderModifiers(getCompareOrderModifies(oi.getOrderModifiers()));
			result.add(tem);
		}
		return result;
	}

	private static List<CompareOrderModifie> getCompareOrderModifies(List<OrderModifier> list) {
		List<CompareOrderModifie> result = new ArrayList<CompareOrderModifie>();
		CompareOrderModifie tem;
		for (OrderModifier oi : list) {
			tem = new CompareOrderModifie();
			tem.setId(oi.getId());
			tem.setItemid(oi.getItemid());
			tem.setOrderid(oi.getOrderid());
			tem.setOrderItemId(oi.getOrderItemId());
			tem.setQty(oi.getQty());
			result.add(tem);
		}
		return result;
	}

}
