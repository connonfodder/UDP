package com.aadhk.kds.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderModifier implements Parcelable {

	private long id;
	private long itemid;
	private long orderid;
	private String modifierName;
	private long orderItemId;
	private double modifierPrice;
	private double modifierCost;
	private int qty;
	private int type;

	public OrderModifier(long itemId, String modifierName, double modifierPrice, int type) {
		this.itemid = itemId;
		this.modifierName = modifierName;
		this.modifierPrice = modifierPrice;
		this.type = type;
	}

	public OrderModifier() {
	}

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

	public double getModifierPrice() {
		return modifierPrice;
	}

	public void setModifierPrice(double modifierPrice) {
		this.modifierPrice = modifierPrice;
	}

	public double getModifierCost() {
		return modifierCost;
	}

	public void setModifierCost(double modifierCost) {
		this.modifierCost = modifierCost;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public String getModifierName() {
		return modifierName;
	}

	public void setModifierName(String modifierName) {
		this.modifierName = modifierName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (itemid ^ (itemid >>> 32));
		result = prime * result + ((modifierName == null) ? 0 : modifierName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderModifier other = (OrderModifier) obj;
		if (itemid != other.itemid)
			return false;
		if (modifierName == null) {
			if (other.modifierName != null)
				return false;
		} else if (!modifierName.equals(other.modifierName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderModifier [id=" + id + ", itemid=" + itemid + ", orderid=" + orderid + ", modifierName=" + modifierName + ", orderItemId=" + orderItemId + ", modifierPrice=" + modifierPrice + ", modifierCost=" + modifierCost + ", qty=" + qty + ", type=" + type + "]";
	}

	protected OrderModifier(Parcel in) {
		id = in.readLong();
		itemid = in.readLong();
		orderid = in.readLong();
		modifierName = in.readString();
		orderItemId = in.readLong();
		modifierPrice = in.readDouble();
		modifierCost = in.readDouble();
		qty = in.readInt();
		type = in.readInt();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeLong(itemid);
		dest.writeLong(orderid);
		dest.writeString(modifierName);
		dest.writeLong(orderItemId);
		dest.writeDouble(modifierPrice);
		dest.writeDouble(modifierCost);
		dest.writeInt(qty);
		dest.writeInt(type);
	}

	@SuppressWarnings("unused")
	public static final Creator<OrderModifier> CREATOR = new Creator<OrderModifier>() {
		@Override
		public OrderModifier createFromParcel(Parcel in) {
			return new OrderModifier(in);
		}

		@Override
		public OrderModifier[] newArray(int size) {
			return new OrderModifier[size];
		}
	};
}