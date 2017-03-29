package com.aadhk.product.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.aadhk.product.bean.Currency;
import com.aadhk.product.bean.Field;
import com.aadhk.product.library.R;

public class CurrencyDB {
	static final String DATABASE_NAME = "currency.db";
	static final int DATABASE_VERSION = 1;
	static final String TABLE_CURRENCY = "currency";

	public static final String COLUMN_CODE = "currencyCode";
	public static final String COLUMN_SYMBOL = "currencySymbol";
	public static final String COLUMN_DESC = "currencyDesc";
	public static final String COLUMN_IS_DEFAULT = "isDefault";
	public static final String[] TABLE_COLUMNS = { COLUMN_CODE, COLUMN_SYMBOL, COLUMN_DESC, COLUMN_IS_DEFAULT };
	public static final int CURRENCY_DEFAULT_INDEX = 27;

	SQLiteDatabase mDb;
	private final DatabaseHelper mDatabaseHelper;
	private final Context context;

	public CurrencyDB(Context context) {
		this.context=context;
		mDatabaseHelper = new DatabaseHelper(context);
		mDb = mDatabaseHelper.getWritableDatabase();
	}

	public void close() {
		mDatabaseHelper.close();
	}

	public void add(Currency bean) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(COLUMN_CODE, bean.getCode());
		initialValues.put(COLUMN_SYMBOL, bean.getSign());
		initialValues.put(COLUMN_DESC, bean.getDesc());
		initialValues.put(COLUMN_IS_DEFAULT, bean.isUse());
		mDb.insert(TABLE_CURRENCY, null, initialValues);
	}

	public void delete(String code) {
		mDb.delete(TABLE_CURRENCY, COLUMN_CODE + "='" + code+"'", null);
	}

	public void setDefault(String code) {
		mDb.beginTransaction();
		//reset default
		ContentValues initialValues = new ContentValues();
		initialValues.put(COLUMN_IS_DEFAULT, false);
		mDb.update(TABLE_CURRENCY, initialValues, null, null);

		//set default
		initialValues.put(COLUMN_IS_DEFAULT, true);
		mDb.update(TABLE_CURRENCY, initialValues, COLUMN_CODE + "='" + code+"'", null);
		mDb.setTransactionSuccessful();
		mDb.endTransaction();
	}

	public void update(Currency bean) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(COLUMN_CODE, bean.getCode());
		initialValues.put(COLUMN_SYMBOL, bean.getSign());
		initialValues.put(COLUMN_DESC, bean.getDesc());
		initialValues.put(COLUMN_IS_DEFAULT, bean.isUse());
		mDb.update(TABLE_CURRENCY, initialValues, COLUMN_CODE + "='" + bean.getCode()+"'", null);
	}
	
	public Currency fetch(String code) {
		Currency bean=null;
		Cursor cursor = mDb.query(false, TABLE_CURRENCY, TABLE_COLUMNS, COLUMN_CODE + "='"+code+"'", null, null, null, COLUMN_CODE+" asc", null);
		if (cursor.moveToFirst()) {
//				String code = cursor.getString(0);
				String sign = cursor.getString(1);
				String desc = cursor.getString(2);
				boolean isDefault = cursor.getInt(3)!= 0;
				bean = new Currency();
				bean.setCode(code);
				bean.setSign(sign);
				bean.setDesc(desc);
				bean.setUse(isDefault);
		}
		cursor.close();
		return bean;
	}

	public List<Currency> fetch() {
		List<Currency> productList = new ArrayList<Currency>();
		Cursor cursor = mDb.query(false, TABLE_CURRENCY, TABLE_COLUMNS, null, null, null, null, COLUMN_CODE+" asc", null);
		if (cursor.moveToFirst()) {
			do {
				String code = cursor.getString(0);
				String sign = cursor.getString(1);
				String desc = cursor.getString(2);
				boolean isDefault = cursor.getInt(3)!= 0;

				Currency bean = new Currency();
				bean.setCode(code);
				bean.setSign(sign);
				bean.setDesc(desc);
				bean.setUse(isDefault);
				productList.add(bean);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return productList;
	}

	public Currency fetchDefault() {
		Currency bean = null;
		Cursor cursor = mDb.query(false, TABLE_CURRENCY, TABLE_COLUMNS, COLUMN_IS_DEFAULT + "= 1", null, null, null, null, null);
		if (cursor.moveToFirst()) {
			String code = cursor.getString(0);
			String sign = cursor.getString(1);
			String desc = cursor.getString(2);
			bean = new Currency();
			bean.setCode(code);
			bean.setSign(sign);
			bean.setDesc(desc);
			bean.setUse(true);
		}
		
		if(bean==null){
			bean = new Currency();
			bean.setCode("USD");
			bean.setSign("$");
			bean.setDesc("United States Dollar");
			bean.setUse(true);
		}
		
		cursor.close();
		return bean;
	}

	public List<Field> fetchField() {
		List<Field> productList = new ArrayList<Field>();
		String[] columns = {COLUMN_CODE };

		Cursor cursor = mDb.query(false, TABLE_CURRENCY, columns, null, null, null, null, COLUMN_CODE+" asc", null);
		if (cursor.moveToFirst()) {
			do {
				productList.add(new Field(0, cursor.getString(0)));
			} while (cursor.moveToNext());
		}
		cursor.close();

		return productList;
	}

	private class DatabaseHelper extends SQLiteOpenHelper {
		private final Context context;

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			this.context = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + TABLE_CURRENCY + "(" + COLUMN_CODE + " TEXT PRIMARY KEY, " + COLUMN_SYMBOL + " TEXT, " + COLUMN_DESC + " TEXT, " + COLUMN_IS_DEFAULT + " numeric)");

			//insert data
			Resources resources = context.getResources();
			String[] currencyArray = resources.getStringArray(R.array.currencyData);
			for (String currency : currencyArray) {
				Log.i("test", "currency:"+currency);
				String[] result = currency.split(",");
				String insertCurrency = "INSERT INTO " + TABLE_CURRENCY + "(" + COLUMN_CODE + ", " + COLUMN_SYMBOL + ", " + COLUMN_DESC + ", " + COLUMN_IS_DEFAULT + ") values ('" + result[0] + "','" + result[1] + "','" + result[2] + "'," + result[3] + ")";
				db.execSQL(insertCurrency);
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			db.beginTransaction();
			
			//handle default currency
			ContentValues initialValues = new ContentValues();
			Cursor cursor = db.query(false, TABLE_CURRENCY, TABLE_COLUMNS, COLUMN_IS_DEFAULT + "= 1", null, null, null, null, null);
			if (cursor.moveToFirst()) {
				initialValues.put(COLUMN_CODE, cursor.getString(0));
				initialValues.put(COLUMN_SYMBOL, cursor.getString(1));
				initialValues.put(COLUMN_DESC, cursor.getString(2));
				initialValues.put(COLUMN_IS_DEFAULT, cursor.getInt(3));
			}
			cursor.close();
			
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENCY);
			onCreate(db);
			
			//reset default
			ContentValues resetValues = new ContentValues();
			resetValues.put(COLUMN_IS_DEFAULT, false);
			db.update(TABLE_CURRENCY, resetValues, null, null);

			db.replace(TABLE_CURRENCY, null,initialValues);
			db.setTransactionSuccessful();
			db.endTransaction();
		}

	}
}
