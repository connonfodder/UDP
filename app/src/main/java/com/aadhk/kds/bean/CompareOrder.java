package com.aadhk.kds.bean;

import java.util.List;

public class CompareOrder {
	private long id;
	private List<CompareOrderItem> orderItems;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<CompareOrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<CompareOrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	@Override
	public String toString() {
		return "CompareOrder [id=" + id + ", orderItems=" + orderItems + "]";
	}

}
