package com.aadhk.product.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.aadhk.product.library.R;

public class SelectCheckBoxDialog extends FieldDialog implements OnClickListener {

	private static final String TAG = "SelectCheckBoxDialog";
	private Button btnConfirm, btnCancel;
	private ListView listView;
	private String[] listData;
	private boolean[] mSelected;

	public SelectCheckBoxDialog(Context context, String[] listData, boolean[] selected) {
		super(context, R.layout.dialog_select);
		this.listData = listData;
		mSelected = selected; 

		btnConfirm = (Button) findViewById(R.id.btnConfirm);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnConfirm.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(new CheckBoxAdapter(context));
	}

	@Override
	public void onClick(View v) {
		if (v == btnConfirm) {
			if (onConfirmListener != null) {
				onConfirmListener.onConfirm(mSelected);
				dismiss();
			}
		} else if (v == btnCancel) {
			dismiss();
		}
	}

	private class CheckBoxAdapter extends BaseAdapter {
		private Context mContext;

		public CheckBoxAdapter(Context context) {
			mContext = context;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = convertView;
			if (view == null) {
				LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.dialog_select_checkbox, null);
			}

			final String variation = listData[position];

			TextView name = (TextView) view.findViewById(R.id.name);
			CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);

			name.setText(variation);
			checkBox.setChecked(mSelected[position]);

			view.setOnClickListener(new android.view.View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mSelected[position] = !mSelected[position];
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
