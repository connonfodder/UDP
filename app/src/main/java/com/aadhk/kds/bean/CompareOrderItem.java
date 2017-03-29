package com.aadhk.kds.bean;

import java.util.List;

public class CompareOrderItem {
	private long id;
	private long itemId;
	private long orderId;
	private double qty;
	private List<CompareOrderModifie> orderModifiers;

	
	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	public List<CompareOrderModifie> getOrderModifiers() {
		return orderModifiers;
	}

	public void setOrderModifiers(List<CompareOrderModifie> orderModifiers) {
		this.orderModifiers = orderModifiers;
	}

	@Override
	public String toString() {
		return "CompareOrderItem [itemId=" + itemId + ", orderId=" + orderId + ", qty=" + qty + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
