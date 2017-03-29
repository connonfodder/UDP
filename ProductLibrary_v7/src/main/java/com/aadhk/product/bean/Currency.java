package com.aadhk.product.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Currency implements Parcelable {

	private int id;
	private String code;
	private String sign;
	private String desc;
	private boolean use;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isUse() {
		return use;
	}

	public void setUse(boolean use) {
		this.use = use;
	}

	@Override
	public String toString() {
		return "Currency [id=" + id + ", code=" + code + ", sign=" + sign + ", desc=" + desc + ", use=" + use + "]";
	}
	
    public Currency() {
		super();
	}

	protected Currency(Parcel in) {
        id = in.readInt();
        code = in.readString();
        sign = in.readString();
        desc = in.readString();
        use = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(code);
        dest.writeString(sign);
        dest.writeString(desc);
        dest.writeByte((byte) (use ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Currency> CREATOR = new Parcelable.Creator<Currency>() {
        @Override
        public Currency createFromParcel(Parcel in) {
            return new Currency(in);
        }

        @Override
        public Currency[] newArray(int size) {
            return new Currency[size];
        }
    };
}