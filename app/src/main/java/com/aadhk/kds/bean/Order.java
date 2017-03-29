package com.aadhk.kds.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Order implements Parcelable {

    private long id;
    private String orderNum;
    private long billId;
    private String orderTime;
    private String deliveryArriveTime;
    private String endTime;
    private long tableId;
    private long customerId;
    private String customerName;
    private int status;
    private short splitType;
    private boolean hasUnpaidBill;
    private boolean hasVoidItem;
    private boolean printReceipt;
    private int personNum;
    private String tableName;
    private String cancelReason;
    private String cancelPerson;
    private String waiterName;
    private String cashierName;
    private String kitchenRemark;

    private double discountAmt;
    private String discountReason;
    private String serviceFeeName;
    private String gratuityName;
    private String receiptNote;

    private double tax1Amt;
    private double tax2Amt;
    private double tax3Amt;
    private String tax1Name;
    private String tax2Name;
    private String tax3Name;
    private double serviceAmt;
    private double subTotal;
    private double amount;
    private int orderCount;
    private double gratuity;
    private double rounding;

    private List<OrderItem> orderItems;
    private List<OrderPayment> orderPayments;
    private Customer customer;
    private String ip;

    public Order() {
        super();
        orderPayments = new ArrayList<>();
        orderItems = new ArrayList<>();
        customer = new Customer();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNum='" + orderNum + '\'' +
                ", billId=" + billId +
                ", orderTime='" + orderTime + '\'' +
                ", deliveryArriveTime='" + deliveryArriveTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", tableId=" + tableId +
                ", customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", status=" + status +
                ", splitType=" + splitType +
                ", hasUnpaidBill=" + hasUnpaidBill +
                ", hasVoidItem=" + hasVoidItem +
                ", printReceipt=" + printReceipt +
                ", personNum=" + personNum +
                ", tableName='" + tableName + '\'' +
                ", cancelReason='" + cancelReason + '\'' +
                ", cancelPerson='" + cancelPerson + '\'' +
                ", waiterName='" + waiterName + '\'' +
                ", cashierName='" + cashierName + '\'' +
                ", kitchenRemark='" + kitchenRemark + '\'' +
                ", discountAmt=" + discountAmt +
                ", discountReason='" + discountReason + '\'' +
                ", serviceFeeName='" + serviceFeeName + '\'' +
                ", gratuityName='" + gratuityName + '\'' +
                ", receiptNote='" + receiptNote + '\'' +
                ", tax1Amt=" + tax1Amt +
                ", tax2Amt=" + tax2Amt +
                ", tax3Amt=" + tax3Amt +
                ", tax1Name='" + tax1Name + '\'' +
                ", tax2Name='" + tax2Name + '\'' +
                ", tax3Name='" + tax3Name + '\'' +
                ", serviceAmt=" + serviceAmt +
                ", subTotal=" + subTotal +
                ", amount=" + amount +
                ", orderCount=" + orderCount +
                ", gratuity=" + gratuity +
                ", rounding=" + rounding +
                ", orderItems=" + orderItems +
                ", orderPayments=" + orderPayments +
                ", customer=" + customer +
                '}';
    }

    @Override
    public Order clone() {
        Parcel p = Parcel.obtain();
        p.writeValue(this);
        p.setDataPosition(0);
        Order newOrder = (Order) p.readValue(Order.class.getClassLoader());
        p.recycle();
        return newOrder;
    }

    public boolean isHasVoidItem() {
        return hasVoidItem;
    }

    public void setHasVoidItem(boolean hasVoidItem) {
        this.hasVoidItem = hasVoidItem;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getDeliveryArriveTime() {
        return deliveryArriveTime;
    }

    public void setDeliveryArriveTime(String deliveryArriveTime) {
        this.deliveryArriveTime = deliveryArriveTime;
    }

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    public int getPersonNum() {
        return personNum;
    }

    public void setPersonNum(int personNum) {
        this.personNum = personNum;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public short getSplitType() {
        return splitType;
    }

    public void setSplitType(short splitType) {
        this.splitType = splitType;
    }

    public double getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(double discountAmt) {
        this.discountAmt = discountAmt;
    }

    public double getTax1Amt() {
        return tax1Amt;
    }

    public void setTax1Amt(double tax1Amt) {
        this.tax1Amt = tax1Amt;
    }

    public double getTax2Amt() {
        return tax2Amt;
    }

    public void setTax2Amt(double tax2Amt) {
        this.tax2Amt = tax2Amt;
    }

    public double getTax3Amt() {
        return tax3Amt;
    }

    public void setTax3Amt(double tax3Amt) {
        this.tax3Amt = tax3Amt;
    }

    public String getTax3Name() {
        return tax3Name;
    }

    public void setTax3Name(String tax3Name) {
        this.tax3Name = tax3Name;
    }

    public double getServiceAmt() {
        return serviceAmt;
    }

    public void setServiceAmt(double serviceAmt) {
        this.serviceAmt = serviceAmt;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getTax1Name() {
        return tax1Name;
    }

    public void setTax1Name(String tax1Name) {
        this.tax1Name = tax1Name;
    }

    public String getTax2Name() {
        return tax2Name;
    }

    public void setTax2Name(String tax2Name) {
        this.tax2Name = tax2Name;
    }

    public String getCancelPerson() {
        return cancelPerson;
    }

    public void setCancelPerson(String cancelPerson) {
        this.cancelPerson = cancelPerson;
    }

    public long getBillId() {
        return billId;
    }

    public void setBillId(long billId) {
        this.billId = billId;
    }

    public String getWaiterName() {
        return waiterName;
    }

    public void setWaiterName(String waiterName) {
        this.waiterName = waiterName;
    }

    public String getServiceFeeName() {
        return serviceFeeName;
    }

    public void setServiceFeeName(String serviceFeeName) {
        this.serviceFeeName = serviceFeeName;
    }

    public String getDiscountReason() {
        return discountReason;
    }

    public void setDiscountReason(String discountReason) {
        this.discountReason = discountReason;
    }

    public String getGratuityName() {
        return gratuityName;
    }

    public void setGratuityName(String gratuityName) {
        this.gratuityName = gratuityName;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getReceiptNote() {
        return receiptNote;
    }

    public void setReceiptNote(String receiptNote) {
        this.receiptNote = receiptNote;
    }

    public boolean isHasUnpaidBill() {
        return hasUnpaidBill;
    }

    public void setHasUnpaidBill(boolean hasUnpaidBill) {
        this.hasUnpaidBill = hasUnpaidBill;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<OrderPayment> getOrderPayments() {
        return orderPayments;
    }

    public void setOrderPayments(List<OrderPayment> orderPayments) {
        this.orderPayments = orderPayments;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getKitchenRemark() {
        return kitchenRemark;
    }

    public void setKitchenRemark(String kitchenRemark) {
        this.kitchenRemark = kitchenRemark;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public double getGratuity() {
        return gratuity;
    }

    public void setGratuity(double gratuity) {
        this.gratuity = gratuity;
    }

    public void setRounding(double rounding) {
        this.rounding = rounding;
    }

    public double getRounding() {
        return rounding;
    }

    public boolean isPrintReceipt() {
        return printReceipt;
    }

    public void setPrintReceipt(boolean printReceipt) {
        this.printReceipt = printReceipt;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
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
        Order other = (Order) obj;
        return id == other.id;
    }


    protected Order(Parcel in) {
        id = in.readLong();
        orderNum = in.readString();
        billId = in.readLong();
        orderTime = in.readString();
        deliveryArriveTime = in.readString();
        endTime = in.readString();
        tableId = in.readLong();
        status = in.readInt();
        splitType = (Short) in.readValue(Short.class.getClassLoader());
        hasUnpaidBill = in.readByte() != 0x00;
        hasVoidItem = in.readByte() != 0x00;
        printReceipt = in.readByte() != 0x00;
        personNum = in.readInt();
        tableName = in.readString();
        cancelReason = in.readString();
        cancelPerson = in.readString();
        waiterName = in.readString();
        discountAmt = in.readDouble();
        discountReason = in.readString();
        serviceFeeName = in.readString();
        gratuityName = in.readString();
        receiptNote = in.readString();
        tax1Amt = in.readDouble();
        tax2Amt = in.readDouble();
        tax3Amt = in.readDouble();
        tax1Name = in.readString();
        tax2Name = in.readString();
        tax3Name = in.readString();
        serviceAmt = in.readDouble();
        subTotal = in.readDouble();
        amount = in.readDouble();
        orderCount = in.readInt();
        gratuity = in.readDouble();
        rounding = in.readDouble();
        if (in.readByte() == 0x01) {
            orderItems = new ArrayList<OrderItem>();
            in.readList(orderItems, OrderItem.class.getClassLoader());
        } else {
            orderItems = null;
        }
        cashierName = in.readString();
        customerId = in.readLong();
        customerName = in.readString();
        kitchenRemark = in.readString();
        customer = (Customer) in.readValue(Customer.class.getClassLoader());
        ip = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(orderNum);
        dest.writeLong(billId);
        dest.writeString(orderTime);
        dest.writeString(deliveryArriveTime);
        dest.writeString(endTime);
        dest.writeLong(tableId);
        dest.writeInt(status);
        dest.writeValue(splitType);
        dest.writeByte((byte) (hasUnpaidBill ? 0x01 : 0x00));
        dest.writeByte((byte) (hasVoidItem ? 0x01 : 0x00));
        dest.writeByte((byte) (printReceipt ? 0x01 : 0x00));
        dest.writeInt(personNum);
        dest.writeString(tableName);
        dest.writeString(cancelReason);
        dest.writeString(cancelPerson);
        dest.writeString(waiterName);
        dest.writeDouble(discountAmt);
        dest.writeString(discountReason);
        dest.writeString(serviceFeeName);
        dest.writeString(gratuityName);
        dest.writeString(receiptNote);
        dest.writeDouble(tax1Amt);
        dest.writeDouble(tax2Amt);
        dest.writeDouble(tax3Amt);
        dest.writeString(tax1Name);
        dest.writeString(tax2Name);
        dest.writeString(tax3Name);
        dest.writeDouble(serviceAmt);
        dest.writeDouble(subTotal);
        dest.writeDouble(amount);
        dest.writeInt(orderCount);
        dest.writeDouble(gratuity);
        dest.writeDouble(rounding);
        if (orderItems == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(orderItems);
        }
        dest.writeString(cashierName);
        dest.writeLong(customerId);
        dest.writeString(customerName);
        dest.writeString(kitchenRemark);
        dest.writeValue(customer);
        dest.writeString(ip);
    }

    @SuppressWarnings("unused")
    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

}
