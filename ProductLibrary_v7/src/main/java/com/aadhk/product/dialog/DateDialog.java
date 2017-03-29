package com.aadhk.product.dialog;


import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Calendar;

import org.acra.ACRA;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;

import com.aadhk.product.library.R;
import com.aadhk.product.util.CalendarUtil;

public class DateDialog extends FieldDialog implements OnClickListener {

	private static final String TAG = "DateDialog";
	private final Button btnConfirm, btnCancel;
	private DatePicker datePicker;

	public DateDialog(Context context, String dateValue) {
		super(context, R.layout.dialog_date_field);
		setTitle(R.string.dlgTitleDate);

		btnConfirm = (Button) findViewById(R.id.btnConfirm);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnConfirm.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		datePicker = (DatePicker) findViewById(R.id.datePicker);

		//show off canlendar view in > android v11 and avoid method not found in <android v11
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= 11) {
			try {
				//datePicker.setCalendarViewShown(false);
				Method m = datePicker.getClass().getMethod("setCalendarViewShown", boolean.class);
				m.invoke(datePicker, false);
			} catch (Exception e) {
				ACRA.getErrorReporter().handleException(e);
				e.printStackTrace();
			} 
		}

		Calendar calendar = Calendar.getInstance();
		if (dateValue != null && !"".equals(dateValue)) {
			try {
				calendar = CalendarUtil.getCalendarByDay(dateValue);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int mYear = calendar.get(Calendar.YEAR);
		int mMonth = calendar.get(Calendar.MONTH);
		int mDay = calendar.get(Calendar.DAY_OF_MONTH);

		datePicker.init(mYear, mMonth, mDay, null);
	}
	/*
	public void setMaxDate(String dateValue){
		datePicker.setMaxDate(CalendarUtil.getCalendarByDay(dateValue).getTimeInMillis());
	}
	
	public void setMinDate(String dateValue){
		datePicker.setMinDate(CalendarUtil.getCalendarByDay(dateValue).getTimeInMillis());
	}*/

	@Override
	public void onClick(View v) {
		if (v == btnConfirm) {
			if (onConfirmListener != null) {
				datePicker.clearFocus();//fix bug: DatePicker not responding to user input from Keyboard
				int m = datePicker.getMonth() + 1;
				String strM = m >= 10 ? "" + m : "0" + m;
				int d = datePicker.getDayOfMonth();
				String strD = d >= 10 ? "" + d : "0" + d;
				String mcdate_value = "" + datePicker.getYear() + "-" + strM + "-" + strD;
				onConfirmListener.onConfirm(mcdate_value);
			}
		}
		dismiss();
	}

	public DatePicker getDatePicker() {
		return datePicker;
	}

	public void setDatePicker(DatePicker datePicker) {
		this.datePicker = datePicker;
	}
}
