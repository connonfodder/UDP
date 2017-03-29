package com.aadhk.product.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.aadhk.product.library.R;

public class SelectRadioDialog extends FieldDialog implements OnClickListener {

	private static final String TAG = "SelectRadioDialog";
	private Button btnConfirm, btnCancel;
	private ListView listView;
	private String[] listData;
	private int mLastSelected;

	//	private OnCancelListener onCancelListener;

	public SelectRadioDialog(Context context, String[] listData, int selectedPosition) {
		super(context, R.layout.dialog_select);
		this.listData = listData;
		mLastSelected = selectedPosition;

		btnConfirm = (Button) findViewById(R.id.btnConfirm);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnConfirm.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(new RadioAdapter(context));
	}

	@Override
	public void onClick(View v) {

		if (v == btnConfirm) {
			if (onConfirmListener != null) {
				onConfirmListener.onConfirm(mLastSelected);
				dismiss();
			}
		} else if (v == btnCancel) {
			//			if (onCancelListener != null) {
			//				onCancelListener.onCancel();
			//			}
			dismiss();
		}
	}

	//	public interface OnCancelListener {
	//		void onCancel();
	//	}
	//
	//	public void setOnCancleListener(OnCancelListener onCancelListener) {
	//		this.onCancelListener = onCancelListener;
	//	}

	private class RadioAdapter extends BaseAdapter {
		private Context mContext;

		public RadioAdapter(Context context) {
			mContext = context;

		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = convertView;
			if (view == null) {
				LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.dialog_select_radio, null);
			}

			final String variation = listData[position];

			TextView name = (TextView) view.findViewById(R.id.name);
			RadioButton radio = (RadioButton) view.findViewById(R.id.radio);

			name.setText(variation);
			if (position == mLastSelected)
				radio.setChecked(true);
			else
				radio.setChecked(false);

			view.setOnClickListener(new android.view.View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mLastSelected = position;
					notifyDataSetChanged();
				}

			});

			return view;
		}

		@Override
		public int getCount() {
			return listData.length;
		}

		@Override
		public Object getItem(int position) {
			return listData[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
	}

}
