package com.aadhk.kds.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderKitchen implements Parcelable {
	private long orderId;
	private long orderItemId;
	private String tableName;
	private String time;

	public OrderKitchen(long orderId, long orderItemId, String tableName, String time) {
		super();
		this.orderId = orderId;
		this.orderItemId = orderItemId;
		this.tableName = tableName;
		this.time = time;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderItemId=" + orderItemId + ", tableName=" + tableName + ", time=" + time + "]";
	}

	protected OrderKitchen(Parcel in) {
		orderId = in.readLong();
		orderItemId = in.readLong();
		tableName = in.readString();
		time = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(orderId);
		dest.writeLong(orderItemId);
		dest.writeString(tableName);
		dest.writeString(time);
	}

	@SuppressWarnings("unused")
	public static final Creator<OrderKitchen> CREATOR = new Creator<OrderKitchen>() {
		@Override
		public OrderKitchen createFromParcel(Parcel in) {
			return new OrderKitchen(in);
		}

		@Override
		public OrderKitchen[] newArray(int size) {
			return new OrderKitchen[size];
		}
	};
}
