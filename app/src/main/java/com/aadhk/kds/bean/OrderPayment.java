package com.aadhk.kds.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderPayment implements Parcelable {

	private long id;
	private long orderId;
	private double amount;
	private String paymentTime;
	private String paymentName;
	private int paymentTypeId;
	private String cashierName;
	private double changeAmt;
	private double paid;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getPaymentName() {
		return paymentName;
	}

	public int getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(int paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	public String getCashierName() {
		return cashierName;
	}

	public void setCashierName(String cashierName) {
		this.cashierName = cashierName;
	}

	public double getChangeAmt() {
		return changeAmt;
	}

	public void setChangeAmt(double changeAmt) {
		this.changeAmt = changeAmt;
	}

	public double getPaid() {
		return paid;
	}

	public void setPaid(double paid) {
		this.paid = paid;
	}

	@Override
	public String toString() {
		return "OrderPayment [id=" + id + ", orderId=" + orderId + ", amount=" + amount + ", paymentTime=" + paymentTime + ", paymentType=" + paymentName + ", paymentTypeId=" + paymentTypeId + ", cashierName=" + cashierName + ", changeAmt=" + changeAmt + ", paid=" + paid + "]";
	}

	public OrderPayment() {
		super();
	}

	protected OrderPayment(Parcel in) {
		id = in.readLong();
		orderId = in.readLong();
		amount = in.readDouble();
		paymentTime = in.readString();
		paymentName = in.readString();
		paymentTypeId = in.readInt();
		cashierName = in.readString();
		changeAmt = in.readDouble();
		paid = in.readDouble();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeLong(orderId);
		dest.writeDouble(amount);
		dest.writeString(paymentTime);
		dest.writeString(paymentName);
		dest.writeInt(paymentTypeId);
		dest.writeString(cashierName);
		dest.writeDouble(changeAmt);
		dest.writeDouble(paid);
	}

	@SuppressWarnings("unused")
	public static final Creator<OrderPayment> CREATOR = new Creator<OrderPayment>() {
		@Override
		public OrderPayment createFromParcel(Parcel in) {
			return new OrderPayment(in);
		}

		@Override
		public OrderPayment[] newArray(int size) {
			return new OrderPayment[size];
		}
	};
}