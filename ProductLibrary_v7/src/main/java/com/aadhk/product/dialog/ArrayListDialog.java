package com.aadhk.product.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aadhk.product.bean.Field;
import com.aadhk.product.library.R;

public class ArrayListDialog extends FieldDialog implements OnItemClickListener, OnClickListener {

	private static final String TAG = "ArrayListDialog";
	private OnEmptyListener onEmptyListener;
	private String[] listData;

	public ArrayListDialog(Context context, String[] listData) {
		super(context, R.layout.dialog_array_list);
		this.listData = listData;
		setupListView();
	}

	private void setupListView() {
		ListView listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(new FieldListAdapter(context, listData));
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (onEmptyListener != null) {
			onEmptyListener.onEmpty();
		}
		dismiss();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// get choose value
		if (onConfirmListener != null) {
			onConfirmListener.onConfirm(position);
		}
		dismiss();
	}

	public void setOnEmptyListener(OnEmptyListener onEmptyListener) {
		this.onEmptyListener = onEmptyListener;
	}

	public interface OnEmptyListener {
		void onEmpty();
	}

	private class FieldListAdapter extends BaseAdapter {

		private static final String TAG = "FieldListAdapter";
		private final String[] listData;
		private final Context context;
		private final LayoutInflater layoutInflater;

		public FieldListAdapter(Context context, String[] listData) {
			this.context = context;
			this.listData = listData;
			layoutInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return listData.length;
		}

		@Override
		public Object getItem(int arg0) {
			return listData.length;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup par) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = layoutInflater.inflate(R.layout.field_list_item, null);
				holder = new ViewHolder();
				holder.name = (TextView) convertView.findViewById(R.id.name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			Field field = new Field();
			field.setName(listData[position]);
			holder.name.setText(field.getName());
			return convertView;
		}

		/* class ViewHolder */
		private class ViewHolder {
			TextView name;
		}
	}

}
