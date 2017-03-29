/*******************************************************************************
 * Copyright (C) Hong Kong Android Technology Co.
 * All right reserved.
 ******************************************************************************/
package com.aadhk.kds.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class OrderItem implements Parcelable {
	private long id;
	private long itemId;
	private long orderId;
	private long billId;
	private long categoryId;
	private String categoryName;
	private int categorySequence;
	protected String itemName;
	protected String kitchenItemName;
	private String tableName;
	protected double itemPrice;

	protected double itemCost;
	protected boolean stopSaleZeroQty;
	private double qty;
	private String remark;
	private String startTime;
	private String endTime;
	private String cancelReason;
	private int status;
	private List<OrderModifier> orderModifiers;
	private String printerIds;
	private String kitchenDisplayIds;
	private int tax1Id;
	private int tax2Id;
	private int tax3Id;
	private int takeoutTax1Id;
	private int takeoutTax2Id;
	private int takeoutTax3Id;
	private String modifierGroupId;
	private String kitchenNoteGroupId;
	private String discountName;
	private List<Long> combineItemIds;	 //for kitchen display
	private double discountAmt;

	private double orderQty;
	private boolean warn;
	private double warnQty;
	private boolean modifierMust;//
	private boolean kitchenNoteMust;
	private double memberPrice1;
	private double memberPrice2;
	private double memberPrice3;

	private boolean hasLine;

	// for split bill
	private boolean selected;

	public OrderItem() {
		combineItemIds = new ArrayList<>();
		orderModifiers = new ArrayList<>();

	}

	@Override
	public OrderItem clone() {
		Parcel p = Parcel.obtain();
		p.writeValue(this);
		p.setDataPosition(0);
		OrderItem newOrderItem = (OrderItem) p.readValue(OrderItem.class.getClassLoader());
		p.recycle();
		return newOrderItem;
	}

	public String getKitchenItemName() {
		return kitchenItemName;
	}

	public void setKitchenItemName(String kitchenItemName) {
		this.kitchenItemName = kitchenItemName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getPrinterIds() {
		return printerIds;
	}

	public void setPrinterIds(String printerIds) {
		this.printerIds = printerIds;
	}

	public String getKitchenDisplayIds() {
		return kitchenDisplayIds;
	}

	public void setKitchenDisplayIds(String kitchenDisplayIds) {
		this.kitchenDisplayIds = kitchenDisplayIds;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public double getItemCost() {
		return itemCost;
	}

	public void setItemCost(double itemCost) {
		this.itemCost = itemCost;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	public double getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(double orderQty) {
		this.orderQty = orderQty;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

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

	public List<OrderModifier> getOrderModifiers() {
		return orderModifiers;
	}

	public void setOrderModifiers(List<OrderModifier> orderModifiers) {
		this.orderModifiers = orderModifiers;
	}

	public int getTakeoutTax1Id() {
		return takeoutTax1Id;
	}

	public void setTakeoutTax1Id(int takeoutTax1Id) {
		this.takeoutTax1Id = takeoutTax1Id;
	}

	public int getTakeoutTax2Id() {
		return takeoutTax2Id;
	}

	public void setTakeoutTax2Id(int takeoutTax2Id) {
		this.takeoutTax2Id = takeoutTax2Id;
	}

	public int getTakeoutTax3Id() {
		return takeoutTax3Id;
	}

	public void setTakeoutTax3Id(int takeoutTax3Id) {
		this.takeoutTax3Id = takeoutTax3Id;
	}

	public int getTax1Id() {
		return tax1Id;
	}

	public void setTax1Id(int tax1Id) {
		this.tax1Id = tax1Id;
	}

	public int getTax2Id() {
		return tax2Id;
	}

	public void setTax2Id(int tax2Id) {
		this.tax2Id = tax2Id;
	}

	public int getTax3Id() {
		return tax3Id;
	}

	public void setTax3Id(int tax3Id) {
		this.tax3Id = tax3Id;
	}

	public String getDiscountName() {
		return discountName;
	}

	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}

	public double getDiscountAmt() {
		return discountAmt;
	}

	public void setDiscountAmt(double discountAmt) {
		this.discountAmt = discountAmt;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public long getBillId() {
		return billId;
	}

	public void setBillId(long billId) {
		this.billId = billId;
	}

	public String getModifierGroupId() {
		return modifierGroupId;
	}

	public void setModifierGroupId(String modifierGroupId) {
		this.modifierGroupId = modifierGroupId;
	}

	public String getKitchenNoteGroupId() {
		return kitchenNoteGroupId;
	}

	public void setKitchenNoteGroupId(String kitchenNoteGroupId) {
		this.kitchenNoteGroupId = kitchenNoteGroupId;
	}

	public boolean isWarn() {
		return warn;
	}

	public void setWarn(boolean warn) {
		this.warn = warn;
	}

	public double getWarnQty() {
		return warnQty;
	}

	public void setWarnQty(double warnQty) {
		this.warnQty = warnQty;
	}

	public boolean isStopSaleZeroQty() {
		return stopSaleZeroQty;
	}

	public void setStopSaleZeroQty(boolean stopSaleZeroQty) {
		this.stopSaleZeroQty = stopSaleZeroQty;
	}

	public int getCategorySequence() {
		return categorySequence;
	}

	public void setCategorySequence(int categorySequence) {
		this.categorySequence = categorySequence;
	}

	public boolean isModifierMust() {
		return modifierMust;
	}

	public void setModifierMust(boolean modifierMust) {
		this.modifierMust = modifierMust;
	}

	public boolean isKitchenNoteMust() {
		return kitchenNoteMust;
	}

	public void setKitchenNoteMust(boolean kitchenNoteMust) {
		this.kitchenNoteMust = kitchenNoteMust;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", itemId=" + itemId + ", orderId=" + orderId + ", billId=" + billId + ", categoryId=" + categoryId + ", categoryName=" + categoryName + ", categorySequence=" + categorySequence + ", itemName=" + itemName + ", kitchenItemName=" + kitchenItemName + ", tableName=" + tableName + ", itemPrice=" + itemPrice + ", itemCost=" + itemCost + ", stopSaleZeroQty=" + stopSaleZeroQty + ", qty=" + qty + ", remark=" + remark + ", startTime=" + startTime + ", endTime=" + endTime + ", cancelReason=" + cancelReason + ", status=" + status + ", orderModifiers=" + orderModifiers + ", printerIds=" + printerIds + ", kitchenDisplayIds=" + kitchenDisplayIds + ", tax1Id=" + tax1Id + ", tax2Id=" + tax2Id + ", tax3Id=" + tax3Id + ", takeoutTax1Id=" + takeoutTax1Id + ", takeoutTax2Id=" + takeoutTax2Id + ", takeoutTax3Id=" + takeoutTax3Id + ", modifierGroupId=" + modifierGroupId + ", kitchenNoteGroupId=" + kitchenNoteGroupId + ", discountName=" + discountName
				+ ", discountAmt=" + discountAmt + ", orderQty=" + orderQty + ", warn=" + warn + ", warnQty=" + warnQty + ", modifierMust=" + modifierMust + ", kitchenNoteMust=" + kitchenNoteMust + ", memberPrice1=" + memberPrice1 + ", memberPrice2=" + memberPrice2 + ", memberPrice3=" + memberPrice3 + ", selected=" + selected + ",orderItemIds=" + combineItemIds + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (itemId ^ (itemId >>> 32));
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
		OrderItem other = (OrderItem) obj;
		return itemId == other.itemId;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	protected OrderItem(Parcel in) {
		id = in.readLong();
		itemId = in.readLong();
		orderId = in.readLong();
		billId = in.readLong();
		categoryId = in.readLong();
		categoryName = in.readString();
		itemName = in.readString();
		kitchenItemName = in.readString();
		tableName = in.readString();
		itemPrice = in.readDouble();
		itemCost = in.readDouble();
		qty = in.readDouble();
		remark = in.readString();
		startTime = in.readString();
		endTime = in.readString();
		cancelReason = in.readString();
		status = in.readInt();
		categorySequence = in.readInt();
		if (in.readByte() == 0x01) {
			orderModifiers = new ArrayList<OrderModifier>();
			in.readList(orderModifiers, OrderModifier.class.getClassLoader());
		} else {
			orderModifiers = null;
		}
		printerIds = in.readString();
		kitchenDisplayIds = in.readString();
		tax1Id = in.readInt();
		tax2Id = in.readInt();
		tax3Id = in.readInt();
		takeoutTax1Id = in.readInt();
		takeoutTax2Id = in.readInt();
		takeoutTax3Id = in.readInt();
		modifierGroupId = in.readString();
		kitchenNoteGroupId = in.readString();
		discountName = in.readString();
		discountAmt = in.readDouble();
		selected = in.readByte() != 0x00;
		orderQty = in.readDouble();
		warn = in.readByte() != 0x00;
		stopSaleZeroQty = in.readByte() != 0x00;
		modifierMust = in.readByte() != 0x00;
		kitchenNoteMust = in.readByte() != 0x00;
		warnQty = in.readDouble();
		memberPrice1 = in.readDouble();
		memberPrice2 = in.readDouble();
		memberPrice3 = in.readDouble();
		hasLine = in.readByte() != 0x00;
		if (in.readByte() == 0x01) {
			combineItemIds = new ArrayList<Long>();
			in.readList(combineItemIds, Long.class.getClassLoader());
		} else {
			combineItemIds = null;
		}
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeLong(itemId);
		dest.writeLong(orderId);
		dest.writeLong(billId);
		dest.writeLong(categoryId);
		dest.writeString(categoryName);
		dest.writeString(itemName);
		dest.writeString(kitchenItemName);
		dest.writeString(tableName);
		dest.writeDouble(itemPrice);
		dest.writeDouble(itemCost);
		dest.writeDouble(qty);
		dest.writeString(remark);
		dest.writeString(startTime);
		dest.writeString(endTime);
		dest.writeString(cancelReason);
		dest.writeInt(status);
		dest.writeInt(categorySequence);
		if (orderModifiers == null) {
			dest.writeByte((byte) (0x00));
		} else {
			dest.writeByte((byte) (0x01));
			dest.writeList(orderModifiers);
		}
		dest.writeString(printerIds);
		dest.writeString(kitchenDisplayIds);
		dest.writeInt(tax1Id);
		dest.writeInt(tax2Id);
		dest.writeInt(tax3Id);
		dest.writeInt(takeoutTax1Id);
		dest.writeInt(takeoutTax2Id);
		dest.writeInt(takeoutTax3Id);
		dest.writeString(modifierGroupId);
		dest.writeString(kitchenNoteGroupId);
		dest.writeString(discountName);
		dest.writeDouble(discountAmt);
		dest.writeByte((byte) (selected ? 0x01 : 0x00));
		dest.writeDouble(orderQty);
		dest.writeByte((byte) (warn ? 0x01 : 0x00));
		dest.writeByte((byte) (stopSaleZeroQty ? 0x01 : 0x00));
		dest.writeByte((byte) (modifierMust ? 0x01 : 0x00));
		dest.writeByte((byte) (kitchenNoteMust ? 0x01 : 0x00));
		dest.writeDouble(warnQty);
		dest.writeDouble(memberPrice1);
		dest.writeDouble(memberPrice2);
		dest.writeDouble(memberPrice3);
		dest.writeByte((byte) (hasLine ? 0x01 : 0x00));
		if (combineItemIds == null) {
			dest.writeByte((byte) (0x00));
		} else {
			dest.writeByte((byte) (0x01));
			dest.writeList(combineItemIds);
		}
	}

	public double getMemberPrice1() {
		return memberPrice1;
	}

	public void setMemberPrice1(double memberPrice1) {
		this.memberPrice1 = memberPrice1;
	}

	public double getMemberPrice2() {
		return memberPrice2;
	}

	public void setMemberPrice2(double memberPrice2) {
		this.memberPrice2 = memberPrice2;
	}

	public double getMemberPrice3() {
		return memberPrice3;
	}

	public void setMemberPrice3(double memberPrice3) {
		this.memberPrice3 = memberPrice3;
	}

	public boolean isHasLine() {
		return hasLine;
	}

	public void setHasLine(boolean hasLine) {
		this.hasLine = hasLine;
	}

	public List<Long> getCombineItemIds() {
		return combineItemIds;
	}

	public void setCombineItemIds(List<Long> combineItemIds) {
		this.combineItemIds = combineItemIds;
	}

	@SuppressWarnings("unused")
	public static final Creator<OrderItem> CREATOR = new Creator<OrderItem>() {
		@Override
		public OrderItem createFromParcel(Parcel in) {
			return new OrderItem(in);
		}

		@Override
		public OrderItem[] newArray(int size) {
			return new OrderItem[size];
		}
	};

}
