package com.aadhk.kds.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aadhk.kds.KitchenActivity;
import com.aadhk.kds.KitchenHelper;
import com.aadhk.kds.R;
import com.aadhk.kds.bean.Order;
import com.aadhk.kds.bean.OrderItem;
import com.aadhk.kds.bean.OrderModifier;
import com.aadhk.kds.util.Constant;
import com.aadhk.kds.util.POSCalendarUtil;
import com.aadhk.kds.util.PreferenceUtil;
import com.aadhk.kds.view.ObservableScrollView;
import com.aadhk.kds.view.ObservableScrollView.ScrollViewListener;
import com.aadhk.product.util.BaseConstant;
import com.aadhk.product.util.CalendarUtil;
import com.aadhk.product.util.Formatter;

import java.util.List;

/*
 * http://blog.csdn.net/listening_music/article/details/7192629
 */
public class KitchenFragment extends Fragment {
	private static final String TAG = "KitchenFragment";
	private List<Order> orderList;
	private KitchenActivity activity;
	private KitchenHelper mHelper;
	private LinearLayout headLayout_1, headLayout_2, headLayout_3;
	private LinearLayout layout_1, layout_2, layout_3;
	private LinearLayout kitchenItemLayout_1, kitchenItemLayout_2, kitchenItemLayout_3;
	private TextView tvTable1, tvTable2, tvTable3;
	private TextView tvOrder1, tvOrder2, tvOrder3;
	private TextView tvDate1, tvDate2, tvDate3;
	private TextView tvMin1, tvMin2, tvMin3;
	private TextView tvServer1, tvServer2, tvServer3;
	private TextView tvCustomer1, tvCustomer2, tvCustomer3;
	private ObservableScrollView scrollView;
	private PreferenceUtil prefUtil;
	private Resources resources;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = (KitchenActivity) activity;
		mHelper = this.activity.getHelper();
		prefUtil = new PreferenceUtil(activity);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		orderList = activity.getCurrentPageData();
		resources =  activity.getResources();
	}

	@Override
	public void onResume() {
		super.onResume();
		createKitchenView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.kitchen_fragment_container, null);
		scrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);
		layout_1 = (LinearLayout) view.findViewById(R.id.layout_1);
		layout_2 = (LinearLayout) view.findViewById(R.id.layout_2);
		layout_3 = (LinearLayout) view.findViewById(R.id.layout_3);
		headLayout_1 = (LinearLayout) view.findViewById(R.id.head_layout_1);
		headLayout_2 = (LinearLayout) view.findViewById(R.id.head_layout_2);
		headLayout_3 = (LinearLayout) view.findViewById(R.id.head_layout_3);
		kitchenItemLayout_1 = (LinearLayout) view.findViewById(R.id.kitchen_item_layout_1);
		kitchenItemLayout_2 = (LinearLayout) view.findViewById(R.id.kitchen_item_layout_2);
		kitchenItemLayout_3 = (LinearLayout) view.findViewById(R.id.kitchen_item_layout_3);
		tvTable1 = (TextView) view.findViewById(R.id.tvTable1);
		tvTable2 = (TextView) view.findViewById(R.id.tvTable2);
		tvTable3 = (TextView) view.findViewById(R.id.tvTable3);
		
		tvOrder1 = (TextView) view.findViewById(R.id.tvOrder1);
		tvOrder2 = (TextView) view.findViewById(R.id.tvOrder2);
		tvOrder3 = (TextView) view.findViewById(R.id.tvOrder3);
		
		tvDate1 = (TextView) view.findViewById(R.id.tvDate1);
		tvDate2 = (TextView) view.findViewById(R.id.tvDate2);
		tvDate3 = (TextView) view.findViewById(R.id.tvDate3);
		
		tvMin1 = (TextView) view.findViewById(R.id.tvMin1);
		tvMin2 = (TextView) view.findViewById(R.id.tvMin2);
		tvMin3 = (TextView) view.findViewById(R.id.tvMin3);

		tvServer1 = (TextView) view.findViewById(R.id.tvServer1);
		tvServer2 = (TextView) view.findViewById(R.id.tvServer2);
		tvServer3 = (TextView) view.findViewById(R.id.tvServer3);

		tvCustomer1 = (TextView) view.findViewById(R.id.tvCustomer1);
		tvCustomer2 = (TextView) view.findViewById(R.id.tvCustomer2);
		tvCustomer3 = (TextView) view.findViewById(R.id.tvCustomer3);

		scrollView.setScrollViewListener(new ScrollViewListener() {
			@Override
			public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
				activity.setPositionY(y);
			}
		});
		return view;
	}

	private void setOrderData(final Order order, LinearLayout headLayout, TextView tvTable,TextView tvOrder,TextView tvDate, TextView tvMin, TextView tvServer, TextView tvCustomer) {
		long time = POSCalendarUtil.getMins(order.getOrderTime(), CalendarUtil.getDateTime());
		if (time >= prefUtil.getRedMinutes()) {
			headLayout.setBackgroundResource(R.color.after30min_red);
		} else if (time < prefUtil.getRedMinutes() && time >= prefUtil.getGreenMinutes()) {
			headLayout.setBackgroundResource(R.color.after15min_green);
		} else if (time < prefUtil.getGreenMinutes() && time >= prefUtil.getYellowMinutes()) {
			headLayout.setBackgroundResource(R.color.after0min_yellow);
		} else if(time >= prefUtil.getBlueMinutes()){	
			headLayout.setBackgroundResource(R.color.after0min_blue);
		}

		String dateTimeFormat = BaseConstant.DATE_FORMAT_NO_YEAR + " " + ( prefUtil.isFullFormat() ? BaseConstant.TIME_FORMAT_24 : BaseConstant.TIME_FORMAT_12);

		String tableName =order.getTableName();
		String arriveTime = order.getDeliveryArriveTime();
		if(order.getTableId() == Constant.TABLE_ID_DELIVERY){
			if(arriveTime.contains(":")){
				tableName += " - " + POSCalendarUtil.displayDateTime24(arriveTime, dateTimeFormat);
			}else{
				tableName += " - " + arriveTime;
			}
		}
		tvTable.setText(tableName);

		tvOrder.setText("#" + order.getOrderNum());

		tvDate.setText(POSCalendarUtil.displayDateTime24(order.getOrderTime(), dateTimeFormat));

		long orderTime = POSCalendarUtil.getMins(order.getOrderTime(), CalendarUtil.getDateTime());
		String min;
		if (orderTime <= 0) {
			min= "1 " + resources.getString(R.string.lbMin);
		} else {
			min= orderTime+" "+resources.getString(R.string.lbMin);
		}
		tvMin.setText(min);

		tvServer.setText(getString(R.string.lbServer) + ": " + order.getWaiterName());

		String customerName = order.getCustomerName();
		if (!TextUtils.isEmpty(customerName)) {
			tvCustomer.setText(customerName);
		}

		//即时刷新问题
		tvTable.setTextSize(prefUtil.getFontSize() - 2);
		tvOrder.setTextSize(prefUtil.getFontSize() - 2);
		tvDate.setTextSize(prefUtil.getFontSize() - 2);
		tvMin.setTextSize(prefUtil.getFontSize() - 2);
		tvServer.setTextSize(prefUtil.getFontSize() - 2);
		tvCustomer.setTextSize(prefUtil.getFontSize() - 2);
		headLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mHelper.cookOrder(order);
			}
		});
	}

	private void setOrderItemData(LinearLayout kitchenItemLayout, final Order order) {
		kitchenItemLayout.removeAllViewsInLayout();
		List<OrderItem> orderItems = order.getOrderItems();
		for (int i = 0; i < orderItems.size(); i++) {
			final OrderItem item = orderItems.get(i);
			View layoutView = activity.getLayoutInflater().inflate(R.layout.activity_kitchen_item, null);
			LinearLayout itemLayout = (LinearLayout) layoutView.findViewById(R.id.kitchen_item_layout);
			TextView name = (TextView) layoutView.findViewById(R.id.orderItemName);
			TextView number = (TextView) layoutView.findViewById(R.id.orderItemNumber);

			String itemName = item.getKitchenItemName();
			if (TextUtils.isEmpty(itemName)) {
				itemName = item.getItemName();
			}

			//Log.i(TAG, "===>itemName:" + itemName);
			if (!TextUtils.isEmpty(itemName)) {
				String qty = "x" + Formatter.displayUsWithoutZero(item.getQty());
				if (item.getStatus() == Constant.STATUS_ORDERITEM_CANCELITEM) {
					itemName += "(" + activity.getString(R.string.lbVoid) + ")";
				} else if (item.getStatus() == Constant.STATUS_ORDERITEM_WAIT) {
					itemName += "(" + activity.getString(R.string.lbWait) + ")";
				} else if (item.getStatus() == Constant.STATUS_ORDERITEM_ORDERUP) {
					itemName += "(" + activity.getString(R.string.lbOrderUp) + ")";
				}
				for (OrderModifier bean : item.getOrderModifiers()) {
					if (bean.getType() == Constant.MODIFIER_TYPE_PLUS) {
						itemName += "\n   +" + bean.getModifierName();
						qty += "\nx" + bean.getQty();
					} else if (bean.getType() == Constant.MODIFIER_TYPE_MINUS) {
						itemName += "\n   -" + bean.getModifierName();
						qty += "\nx" + bean.getQty();
					}
				}
				if (item.getRemark() != null) {
					itemName += "\n" + item.getRemark();
					qty += "\n";
				}
				name.setText(itemName);
				number.setText(qty);
				name.setTextSize(prefUtil.getFontSize());
				number.setTextSize(prefUtil.getFontSize());
				itemLayout.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						mHelper.cookItem(order, item );
					}
				});
			} else {
				if (item.isHasLine() && i < orderItems.size() - 1) {
					layoutView.findViewById(R.id.line).setVisibility(View.VISIBLE);
				}
				layoutView.findViewById(R.id.itemLayout).setVisibility(View.GONE);
			}
			kitchenItemLayout.addView(layoutView);
		}
	}

	/**
	 * 根据Order集合对厨房试图的上面部分初始化，例如设置可见，数据和控件的绑定
	 */
	private void createKitchenView() {
		//Log.i(TAG, "START CREATE KITCHEN VIEW ");
		// 清空
		layout_1.setVisibility(View.INVISIBLE);
		layout_2.setVisibility(View.INVISIBLE);
		layout_3.setVisibility(View.INVISIBLE);
		// 初始值化
		for (int i = 0; i < orderList.size(); i++) {
			if (i == 0) {
				Order order_1 = orderList.get(0);
				setOrderData(order_1, headLayout_1, tvTable1, tvOrder1, tvDate1, tvMin1, tvServer1, tvCustomer1);
				setOrderItemData(kitchenItemLayout_1, order_1);
				layout_1.setVisibility(View.VISIBLE);
			} else if (i == 1) {
				Order order_2 = orderList.get(1);
				setOrderData(order_2, headLayout_2, tvTable2, tvOrder2, tvDate2, tvMin2, tvServer2, tvCustomer2);
				setOrderItemData(kitchenItemLayout_2, order_2);
				layout_2.setVisibility(View.VISIBLE);
			} else {
				Order order_3 = orderList.get(2);
				setOrderData(order_3, headLayout_3, tvTable3, tvOrder3, tvDate3, tvMin3, tvServer3, tvCustomer3);
				setOrderItemData(kitchenItemLayout_3, order_3);
				layout_3.setVisibility(View.VISIBLE);
			}
		}

		if (scrollView != null) {
			Handler handler = new Handler();
			handler.post(new Runnable() {
				@Override
				public void run() {
					scrollView.scrollTo(0, activity.getPositionY());
				}
			});
		}
	}

	public void createKitchenView(List<Order> orderList) {
		this.orderList.clear();
		this.orderList.addAll(orderList);
		createKitchenView();
	}
}
