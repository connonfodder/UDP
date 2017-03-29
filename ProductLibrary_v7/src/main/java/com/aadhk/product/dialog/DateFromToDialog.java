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
import android.widget.Toast;

import com.aadhk.product.library.R;
import com.aadhk.product.util.CalendarUtil;

public class DateFromToDialog extends FieldDialog implements OnClickListener {

	private static final String TAG = "DateDialog";
	private final Button btnConfirm, btnCancel;
	private DatePicker datePickerFrom, datePickerTo;
	private OnConfirmListener onConfirmListener;

	public DateFromToDialog(Context context, String dateValue) {
		super(context, R.layout.dialog_date_from_to);
//		setTitle(R.string.CustomDateFromTo);

		btnConfirm = (Button) findViewById(R.id.btnConfirm);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnConfirm.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		datePickerFrom = (DatePicker) findViewById(R.id.datePickerFrom);
		datePickerTo = (DatePicker) findViewById(R.id.datePickerTo);

		//show off canlendar view in > android v11 and avoid method not found in <android v11
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= 11) {
			try {
				//datePicker.setCalendarViewShown(false);
				Method methodFrom = datePickerFrom.getClass().getMethod("setCalendarViewShown", boolean.class);
				methodFrom.invoke(datePickerFrom, false);
				
				Method methodTo = datePickerTo.getClass().getMethod("setCalendarViewShown", boolean.class);
				methodTo.invoke(datePickerTo, false);
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

		datePickerFrom.init(mYear, mMonth, mDay, null);
		datePickerTo.init(mYear, mMonth, mDay, null);
	}

	@Override
	public void onClick(View v) {
		if (v == btnConfirm) {
			if (onConfirmListener != null) {
				datePickerFrom.clearFocus();//fix bug: DatePicker not responding to user input from Keyboard
				int month = datePickerFrom.getMonth() + 1;
				String strM = month >= 10 ? "" + month : "0" + month;
				int day = datePickerFrom.getDayOfMonth();
				String strD = day >= 10 ? "" + day : "0" + day;
				String dateValueFrom = "" + datePickerFrom.getYear() + "-" + strM + "-" + strD;
				
				datePickerTo.clearFocus();
				month = datePickerTo.getMonth() + 1;
				strM = month >= 10 ? "" + month : "0" + month;
				day = datePickerTo.getDayOfMonth();
				strD = day >= 10 ? "" + day : "0" + day;
				String dateValueTo = "" + datePickerTo.getYear() + "-" + strM + "-" + strD;
				
				//Log.i(TAG, "===>dateValueFrom:"+dateValueFrom+", dateValueTo:"+dateValueTo);
				if(dateValueTo.compareTo(dateValueFrom)>=0){
					onConfirmListener.onConfirm(dateValueFrom, dateValueTo);
					dismiss();
				}else {
					Toast.makeText(context, R.string.errorChoosePeriod, Toast.LENGTH_LONG).show();
				}
			}
		}else if (v == btnCancel) {
			dismiss();
		}
		
	}
	
	public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
		this.onConfirmListener = onConfirmListener;
	}

	public interface OnConfirmListener {
		void onConfirm(String fromValue, String toValue);
	}

	public DatePicker getDatePicker() {
		return datePickerFrom;
	}

	public void setDatePicker(DatePicker datePicker) {
		this.datePickerFrom = datePicker;
	}
}
