package com.aadhk.kds.bean;

public class CompareOrderModifie {
	private long id;
	private long itemid;
	private long orderid;
	private long orderItemId;
	private int qty;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getItemid() {
		return itemid;
	}
	public void setItemid(long itemid) {
		this.itemid = itemid;
	}
	public long getOrderid() {
		return orderid;
	}
	public void setOrderid(long orderid) {
		this.orderid = orderid;
	}
	public long getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(long orderItemId) {
		this.orderItemId = orderItemId;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	@Override
	public String toString() {
		return "CompareOrderModifie [id=" + id + ", itemid=" + itemid + ", orderid=" + orderid + ", orderItemId=" + orderItemId + ", qty=" + qty + "]";
	}
}
