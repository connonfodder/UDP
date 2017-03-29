package com.aadhk.kds;

import android.os.Parcel;
import android.os.Parcelable;

import com.aadhk.kds.bean.Order;

import java.util.List;

public class UDPMessage implements Parcelable {

    public final static short OP_INIT_KDS = 1001;
    public final static short OP_SEND = 1002;
    public final static short OP_PAY = 1003;
    public final static short OP_COOK = 1004;
    public final static short OP_UNCOOK = 1005;

    public final static short REV_SENDING = 0;
    public final static short REV_SENDED = 1;

    private int version;        // 用来校验是否与POS同步
    private String data;        // json实体数据
    private short operation;    // 操作类型  0 shutdown  1 init kds  2 taking order  3 pay order  4 cook order   5 uncook order
    private short rev;          // 0 未发送   1 已接受
    private String fromIp;          //用于处理多台连接的问题  例如服务器版POS使用的ip都是一样的
    private String toIp;
    private String kitchenName;
    private long orderId;
    private List<Order> orderList;
    private Order order;

    public UDPMessage(short operation, String fromIp, String data) {
        this.operation = operation;
        this.fromIp = fromIp;
        this.rev = REV_SENDING;
        this.data = data;
    }

    public UDPMessage(short operation, String fromIp) {
        this.operation = operation;
        this.fromIp = fromIp;
        this.rev = REV_SENDING;
    }

    public UDPMessage(String data, short operation, short rev, String fromIp) {
        this.version = -1;
        this.data = data;
        this.operation = operation;
        this.rev = rev;
        this.fromIp = fromIp;
    }

    public UDPMessage(String data, short operation, short rev, String fromIp, String toIp) {
        this.version = -1;
        this.data = data;
        this.operation = operation;
        this.rev = rev;
        this.fromIp = fromIp;
        this.toIp = toIp;
    }

    public UDPMessage clone() {
        Parcel p = Parcel.obtain();
        p.writeValue(this);
        p.setDataPosition(0);
        UDPMessage newOne = (UDPMessage) p.readValue(UDPMessage.class.getClassLoader());
        p.recycle();
        return newOne;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public short getOperation() {
        return operation;
    }

    public void setOperation(short operation) {
        this.operation = operation;
    }

    public short getRev() {
        return rev;
    }

    public void setRev(short rev) {
        this.rev = rev;
    }

    public String getFromIp() {
        return fromIp;
    }

    public void setFromIp(String fromIp) {
        this.fromIp = fromIp;
    }

    public String getToIp() {
        return toIp;
    }

    public void setToIp(String toIp) {
        this.toIp = toIp;
    }

    public String getKitchenName() {
        return kitchenName;
    }

    public void setKitchenName(String kitchenName) {
        this.kitchenName = kitchenName;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "UDPMessage{" +
                "version=" + version +
                ", data='" + data + '\'' +
                ", operation=" + operation +
                ", rev=" + rev +
                ", fromIp='" + fromIp + '\'' +
                ", toIp='" + toIp + '\'' +
                ", kitchenName='" + kitchenName + '\'' +
                ", orderId=" + orderId +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.version);
        dest.writeString(this.data);
        dest.writeInt(this.operation);
        dest.writeInt(this.rev);
        dest.writeString(this.fromIp);
        dest.writeString(this.toIp);
        dest.writeString(this.kitchenName);
        dest.writeLong(this.orderId);
        dest.writeTypedList(this.orderList);
        dest.writeParcelable(this.order, flags);
    }

    protected UDPMessage(Parcel in) {
        this.version = in.readInt();
        this.data = in.readString();
        this.operation = (short) in.readInt();
        this.rev = (short) in.readInt();
        this.fromIp = in.readString();
        this.toIp = in.readString();
        this.kitchenName = in.readString();
        this.orderId = in.readLong();
        this.orderList = in.createTypedArrayList(Order.CREATOR);
        this.order = in.readParcelable(Order.class.getClassLoader());
    }

    public static final Parcelable.Creator<UDPMessage> CREATOR = new Parcelable.Creator<UDPMessage>() {
        @Override
        public UDPMessage createFromParcel(Parcel source) {
            return new UDPMessage(source);
        }

        @Override
        public UDPMessage[] newArray(int size) {
            return new UDPMessage[size];
        }
    };
}
