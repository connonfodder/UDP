package com.aadhk.kds.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Modifier implements Parcelable {

	private long id;
	private long groupId;
	private String name;
	private double price;
	private double cost;
	private int qty;
	private int type;
	private boolean required;

	public Modifier() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
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

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Modifier other = (Modifier) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Modifier [id=" + id + ", groupId=" + groupId + ", name=" + name + ", price=" + price + ", cost=" + cost + ", qty=" + qty + ", type=" + type + ", required=" + required + "]";
	}

	protected Modifier(Parcel in) {
		id = in.readLong();
		groupId = in.readLong();
		name = in.readString();
		price = in.readDouble();
		cost = in.readDouble();
		qty = in.readInt();
		type = in.readInt();
		required = in.readByte() != 0x00;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeLong(groupId);
		dest.writeString(name);
		dest.writeDouble(price);
		dest.writeDouble(cost);
		dest.writeInt(qty);
		dest.writeInt(type);
		dest.writeByte((byte) (required ? 0x01 : 0x00));
	}

	public static final Creator<Modifier> CREATOR = new Creator<Modifier>() {
		@Override
		public Modifier createFromParcel(Parcel in) {
			return new Modifier(in);
		}

		@Override
		public Modifier[] newArray(int size) {
			return new Modifier[size];
		}
	};
}