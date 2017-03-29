package com.aadhk.kds.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Customer implements Parcelable {
	private int id;
	private String name;
	private String address1;
	private String address2;
	private String address3;
	private String tel;
	private String email;
	private int companyId;
	private double expenseAmount;

	public double getExpenseAmount() {
		return expenseAmount;
	}

	public void setExpenseAmount(double expenseAmount) {
		this.expenseAmount = expenseAmount;
	}

	public Customer() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", address1=" + address1 + ", address2=" + address2 + ", address3=" + address3 + ", tel=" + tel + ", email=" + email + ", companyId=" + companyId + ", expenseAmount=" + expenseAmount + "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeString(address1);
		dest.writeString(address2);
		dest.writeString(address3);
		dest.writeString(tel);
		dest.writeString(email);
		dest.writeInt(companyId);
		dest.writeDouble(expenseAmount);
	}

	public Customer(Parcel in) {
		id = in.readInt();
		name = in.readString();
		address1 = in.readString();
		address2 = in.readString();
		address3 = in.readString();
		tel = in.readString();
		email = in.readString();
		companyId = in.readInt();
		expenseAmount = in.readDouble();
	}

	@SuppressWarnings("unused")
	public static final Creator<Customer> CREATOR = new Creator<Customer>() {
		@Override
		public Customer createFromParcel(Parcel in) {
			return new Customer(in);
		}

		@Override
		public Customer[] newArray(int size) {
			return new Customer[size];
		}
	};

}
