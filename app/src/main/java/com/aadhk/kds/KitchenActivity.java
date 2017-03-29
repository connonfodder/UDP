package com.aadhk.kds;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.aadhk.billing.IabHelper;
import com.aadhk.billing.IabResult;
import com.aadhk.billing.Inventory;
import com.aadhk.billing.Purchase;
import com.aadhk.kds.adapter.PopupListAdapter;
import com.aadhk.kds.bean.Order;
import com.aadhk.kds.bean.OrderItem;
import com.aadhk.kds.dialog.ProductRegistrationDialog;
import com.aadhk.kds.fragment.KitchenFragment;
import com.aadhk.kds.util.Config;
import com.aadhk.kds.util.Constant;
import com.aadhk.kds.util.POSCalendarUtil;
import com.aadhk.kds.view.MessageDialog;
import com.aadhk.license.LicenseException;
import com.aadhk.license.LicenseManager;
import com.aadhk.license.dialog.AppInPurchaseDialog;
import com.aadhk.product.asyn.TaskAsyncCallBack;
import com.aadhk.product.asyn.TaskAsyncCustom;
import com.aadhk.product.asyn.TaskAsyncNoProcess;
import com.aadhk.product.bean.License;
import com.aadhk.product.service.LicenseService;
import com.aadhk.product.util.BaseConstant;
import com.aadhk.product.util.CalendarUtil;
import com.aadhk.product.util.KDSIntentUtil;
import com.aadhk.product.util.NetworkUtil;
import com.aadhk.product.util.VersionUtil;
import com.google.gson.Gson;

import org.acra.ACRA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/*
 * http://blog.csdn.net/listening_music/article/details/7192629
 */
public class KitchenActivity extends POSActivity implements OnClickListener {
    private static final String TAG = "KitchenActivity";
    private KitchenFragment fragment;
    private LinearLayout bottomLayout;         // 上面布局
    private TextView tvMessage, menuSummary;
    private LinearLayout orderItemLayout;    // 下面布局
    private FrameLayout fragmentLayout;
    private List<Order> allListOrder = new ArrayList<>();   //总
    private List<Order> listOrder = new ArrayList<>();      //当前的
    private LinearLayout tempLayout;
    private TextView titleName;
    private LinearLayout currentLayout, historyLayout, settingLayout, summaryLayout;
    private KitchenHelper mHelper;

    private int currentPage;
    private int positionY;
    private Menu menu;
    private boolean isHistory = false;
    private PopupWindow popupWindow;
    private View popupLayout;
    private ListView listView;
    private PopupListAdapter adapter;
    private int itemTotalSize = 0;
    public static String localIP;
//    private IabHelper mIabHelper;
    private boolean isIA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        POSApp.getInstance().setKitchenActivity(this);
        setContentView(R.layout.kitchen_container);
        initView();
        mHelper = new KitchenHelper(this);
        setupTitleInfo();

      /*  mIabHelper = new IabHelper(KitchenActivity.this, Config.BASE64_ENCODE_PUBLIC_KEY);
        try {
            mIabHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                @Override
                public void onIabSetupFinished(IabResult result) {
                    if (!result.isSuccess()) {
                        // complain("Problem setting up in-app billing: " + result);
                        return;
                    }
                    isIA = true;
                    final List<String> moreSkus = new ArrayList<String>();
                    moreSkus.add(Config.PRODUCT_FULL);
                    mIabHelper.queryInventoryAsync(true, moreSkus, mGotInventoryListener);
                }
            });
        } catch (Exception e) {
            ACRA.getErrorReporter().handleException(e);
            e.printStackTrace();
        }*/


        VersionUtil initial = new VersionUtil(this);
        if (initial.firstRun()) {
            // handle license: server, local registeration in preference
            try {
                License license = new License();
                license.setSerialNumber(LicenseManager.getSerialId(this, Config.PRODUCT_FULL));
                license.setItem(Config.PRODUCT_FULL);
                license.setDeviceModel(android.os.Build.MODEL + " " + android.os.Build.VERSION.SDK_INT);
                license.setLocale(Locale.getDefault() + "");
                //use server time
//                license.setInstalledDate(CalendarUtil.getDate());
                license.setDeviceSerial(android.os.Build.SERIAL);

                WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                license.setDeviceMacAddress(wifi.getConnectionInfo().getMacAddress());

                prefUtil.saveLicense(license);
            } catch (LicenseException e) {
                e.printStackTrace();
                ACRA.getErrorReporter().handleException(e);
            } catch (NoSuchFieldError ignored) {
            }

            if (Config.TRIAL_VERSION) {// standalone trial version, server trial versions
                if (NetworkUtil.checkNetworkConnection(this)) {
                    TaskAsyncCustom dataTask = new TaskAsyncCustom(taskInstallLicense, this);
                    dataTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);
                }
            } else if (Config.DEVELOPER_VERSION) {// developer version
                addPurchaseLicense();
                showFragment();
            } else {// Other version: in-app purchase version
                showFragment();
            }
        } else {// everytime
            if (initial.isChange()) {
                // alert message before version 1.7.2
                if ("1.0.4".compareTo(initial.getSavedVersion()) > 0) {
                    MessageDialog msgDialog = new MessageDialog(this);
                    msgDialog.setCustomTitle(R.string.msgNote);
                    msgDialog.show();
                }

                if (Config.TRIAL_VERSION && !TextUtils.isEmpty(prefUtil.getActivationKey())) {
                    TaskAsyncNoProcess drawerTask = new TaskAsyncNoProcess(taskCheckLicense);
                    drawerTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);
                }
            }

//			Log.i(TAG, "is purchase:"+LicenseManager.isPurchased(this, Config.PRODUCT_FULL));
            if (LicenseManager.isPurchased(this, Config.PRODUCT_FULL) || Config.INAPP_VERSION || !TextUtils.isEmpty(prefUtil.getActivationKey())) {
                showFragment();
            } else {
                showLicenseInfo();
            }
        }
        initial.setManifestVersion();
    }

    public void showFragment() {
        mHelper.initSocket();
        mHelper.setupSound();
        setFragment();
        setupBottomView(Constant.STATUS_DISCONNECT);
    }

    // 設置連接
    private void setupTitleInfo() {
        WifiManager wifiMgr = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        localIP = android.text.format.Formatter.formatIpAddress(wifiInfo.getIpAddress());
        titleName.setText(getString(R.string.aadhk_app_name) + " (" + localIP + ")");
    }

    private void setFragment() {
        fragment = new KitchenFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        if (v == currentLayout) {        // 显示新的当前记录
            isHistory = false;
            currentLayout.setVisibility(View.GONE);
            historyLayout.setVisibility(View.VISIBLE);
            listOrder = mHelper.separateData(allListOrder, isHistory);
            setupView();
            Toast.makeText(this, R.string.loadingCook, Toast.LENGTH_LONG).show();
        } else if (v == historyLayout) { // 显示新的历史记录
            isHistory = true;
            currentLayout.setVisibility(View.VISIBLE);
            historyLayout.setVisibility(View.GONE);
            listOrder = mHelper.separateData(allListOrder, isHistory);
            setupView();
            Toast.makeText(this, R.string.loadingHistory, Toast.LENGTH_SHORT).show();
        } else if (v == settingLayout) {
            Intent intent = new Intent();
            intent.setClass(this, SettingsActivity.class);
            startActivityForResult(intent, Constant.REQUEST_CODE_TABLE);
        } else if (v == summaryLayout) {
            setupOrderSummary(false);
        }
    }

    public void sendMessage(Message msg) {
        clientHandler.sendMessage(msg);
    }

    // open message dialog,net disconnected
    private void handleDisconnect() {
        if (popupWindow != null) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
        }
        titleName.setText(getString(R.string.aadhk_app_name) + " (" + localIP + ")");
        allListOrder.clear();
        summaryLayout.setVisibility(View.GONE);
        listOrder.clear();
        fragment.createKitchenView(getCurrentPageData());
        setupBottomView(Constant.STATUS_DISCONNECT);
        titleName.setText(getString(R.string.aadhk_app_name) + " (" + localIP + ")");
    }

    private Order removeOrder = null;
    private OrderItem removeOrderItem = null;

    public void removeOrder(Order order) {
        removeOrder = order;
        int index = listOrder.indexOf(removeOrder);
        listOrder.remove(index);
        for (Order item : allListOrder) {
            if (item.getId() == removeOrder.getId()) {
                for (OrderItem i : item.getOrderItems()) {
                    if (isHistory && i.getStatus() != Constant.STATUS_ORDERITEM_CANCELITEM) {  //修改为未上菜
                        i.setStatus(Constant.STATUS_ORDERITEM_NEW);
                        item.setHasVoidItem(false);
                    } else {                                                                  //上菜
                        if (i.getStatus() == Constant.STATUS_ORDERITEM_CANCELITEM)
                            item.setHasVoidItem(true);
                        i.setStatus(Constant.STATUS_ORDERITEM_COOK);
                    }
                }
                break;
            }
        }
        setupView();
    }


    public void removeOrderItem(Order order, OrderItem item) {
        removeOrderItem = item;
        for (Order tem : listOrder) {
            if (tem.getId() == order.getId()) {
                List<OrderItem> list = tem.getOrderItems();
                int index = list.indexOf(removeOrderItem);
                list.remove(index);
                if (list.size() == 0) {
                    removeOrder(order);
                    return;
                }
            }
        }
        for (Order tem : allListOrder) {
            if (tem.getId() == order.getId()) {
                for (OrderItem i : tem.getOrderItems()) {
                    if (i.getId() == item.getId()) {
                        if (!isHistory) {  //由 当前-> 历史  处理的是 STATUS_ORDERITEM_NEW || STATUS_ORDERITEM_WAIT
                            if (i.getStatus() == Constant.STATUS_ORDERITEM_NEW || i.getStatus() == Constant.STATUS_ORDERITEM_WAIT)
                                i.setStatus(Constant.STATUS_ORDERITEM_COOK);
                        } else {
                            if (i.getStatus() != Constant.STATUS_ORDERITEM_CANCELITEM)
                                i.setStatus(Constant.STATUS_ORDERITEM_NEW);
                        }
                        break;
                    }
                }
                break;
            }
        }
        setupView();
    }

    public long start;
    public long deal;
    public long end;

    public void handleReceive(Message msg) {
        deal = System.currentTimeMillis();   //
        summaryLayout.setVisibility(View.VISIBLE);
        UDPMessage receive = (UDPMessage) msg.obj;
        //拿到数据后开始处理   init  send  pay
        String dataStr = receive.getData();
        if (TextUtils.isEmpty(dataStr)) return;
        //对比version
        if (!POSApp.getInstance().compareReceiveVersion(receive.getFromIp(), receive.getVersion())) {   //接收时校验version
            MessageQueue queue = MessageQueue.getInstance();
            queue.put(new UDPMessage("", UDPMessage.OP_INIT_KDS, UDPMessage.REV_SENDING, receive.getToIp(), receive.getFromIp()));
            return;
        }
        Gson gson = new Gson();
        switch (receive.getOperation()) {
            case UDPMessage.OP_INIT_KDS:    //一般用于初始化或者数据
                List<Order> allDataList = receive.getOrderList();
                if (allDataList != null && allDataList.size() > 0 && !TextUtils.isEmpty(receive.getFromIp())) {
                   Log.d("jack", "-------OP_INIT_KDS  set ip----------"+receive.getFromIp() + ", toIp=" + receive.getToIp());
                    mHelper.setIp(allDataList, receive.getToIp());
                    allListOrder.clear();
                    allListOrder.addAll(allDataList);
                    String kitchenName = receive.getKitchenName();
                    if (!TextUtils.isEmpty(kitchenName)) {
                        titleName.setText(getString(R.string.aadhk_app_name) + " - " + kitchenName);
                    } else {
                        titleName.setText(getString(R.string.aadhk_app_name) + " ( - " + localIP + ")");
                    }
                }
                break;
            case UDPMessage.OP_SEND:       //下单时只有一个Order
                mHelper.playSound(0);
                Order sendOrder = receive.getOrder();
                sendOrder.setIp(receive.getFromIp());
                allListOrder.add(sendOrder);
                break;
            case UDPMessage.OP_PAY:         //付款只有一个orderId
                try {
                    long orderId = receive.getOrderId();
                    payOrder(orderId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        listOrder.clear();
        listOrder.addAll(mHelper.separateData(allListOrder, isHistory));
        setupView();
    }

    private void payOrder(long orderId) {
        for (Iterator<Order> orderIterator = allListOrder.iterator(); orderIterator.hasNext(); ) {
            Order item = orderIterator.next();
            if (item.getId() == orderId) {
                List<OrderItem> orderItemList = item.getOrderItems();
                for (Iterator<OrderItem> iterator = orderItemList.iterator(); iterator.hasNext(); ) {
                    OrderItem bean = iterator.next();
                    if (bean.getStatus() == Constant.STATUS_ORDERITEM_COOK)
                        iterator.remove();
                }
                if (orderItemList.size() != 0) {
                    item.setOrderItems(orderItemList);
                } else {
                    orderIterator.remove();
                }
                break;
            }
        }
    }


    //获取当前页面的Order集合，厨房显示端会以三个为一组的显示 int型默认为0，刚刚开始显示的是第一页 currentPage=0
    public List<Order> getCurrentPageData() {
        List<Order> listPageOrder = new ArrayList<>();
        for (int i = currentPage * 3; i < currentPage * 3 + 3 && i < listOrder.size(); i++) {
            listPageOrder.add(listOrder.get(i));
        }
        if (listPageOrder.size() == 0 && currentPage >= 1) {
            currentPage = currentPage - 1;
            return getCurrentPageData();
        }
        return listPageOrder;
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "========onDestroy============");
    /*    if (mIabHelper != null) {
            mIabHelper.dispose();
            mIabHelper = null;
        }*/
        super.onDestroy();
    }

    @Override
    public void finish() {
        mHelper.closeSocket();
        super.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.kitchen, menu);
        this.menu = menu;
        menu.findItem(R.id.menu_current).setVisible(false);
        menu.findItem(R.id.menu_history).setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    private Handler clientHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Constant.STATUS_RECEIVE_DATA) {
                handleReceive(msg);
            } else if (msg.what == Constant.STATUS_DISCONNECT) {
                handleDisconnect();
            }
        }
    };

    private void setupView() {
        Collections.sort(listOrder, new ComparatorOrder());
        setupOrderSummary(true);
        fragment.createKitchenView(getCurrentPageData());
        setupBottomView(Constant.STATUS_RECEIVE_DATA);
    }

    private void setupOrderSummary(boolean isFresh) {
        String text;
        if (isFresh) {        // 如果是刷新
            if (popupWindow != null) {
                if (popupWindow.isShowing()) {
                    preparePop();
                    popupWindow.showAsDropDown(summaryLayout);
                } else {
                    preparePop();
                }
            } else { // 第一次
                preparePop();
            }
        } else {
            if (popupWindow != null) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    preparePop();
                    popupWindow.showAsDropDown(summaryLayout);
                }
            } else {
                preparePop();
                popupWindow.showAsDropDown(summaryLayout);
            }
        }
        text = String.format(getString(R.string.menuSummary), itemTotalSize);
        menuSummary.setText(text);
    }

    private void preparePop() {
        popupWindow = new PopupWindow(this);
        popupLayout = LayoutInflater.from(this).inflate(R.layout.list, null);
        popupWindow.setContentView(popupLayout);
        popupWindow.setWidth(getResources().getDimensionPixelSize(R.dimen.popwindown_width));
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        listView = (ListView) popupLayout.findViewById(R.id.listView);
        adapter = new PopupListAdapter(this, listOrder);
        listView.setAdapter(adapter);
        itemTotalSize = adapter.getItemTotalSize();
    }

    private void addOnClickListener(final LinearLayout orderItemLayout, final int i) {
        orderItemLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                currentPage = i;
                setPositionY(0);
                if (tempLayout != orderItemLayout) {
                    tempLayout.setBackgroundResource(0);
                    orderItemLayout.setBackgroundResource(R.drawable.bg_order_select_layout);
                } else {
                    tempLayout.setBackgroundResource(R.drawable.bg_order_select_layout);
                }
                tempLayout = orderItemLayout;
                setFragment();
            }
        });
    }

    //根据Order集合对厨房试图的下面部分初始化，例如设置可见，数据和控件的绑定
    private void setupBottomView(int action) {
        if (listOrder.size() == 0) {
            fragmentLayout.setVisibility(View.GONE);
            findViewById(R.id.bottom_container).setVisibility(View.GONE);

            tvMessage.setVisibility(View.VISIBLE);
            String message = getString(R.string.lbNoOrder);
            if (action == Constant.STATUS_DISCONNECT) {
                message = getString(R.string.lbNoConnection);
            }
            tvMessage.setText(message);
        } else {
            fragmentLayout.setVisibility(View.VISIBLE);
            findViewById(R.id.bottom_container).setVisibility(View.VISIBLE);
            tvMessage.setVisibility(View.GONE);
        }

        bottomLayout.removeAllViews();
        if (listOrder.size() <= currentPage * 3) {
            currentPage = 0;
        }

        if (!listOrder.isEmpty()) {
            for (int i = 0; i < listOrder.size(); i++) {
                if (i % 3 == 0) {
                    View view = getLayoutInflater().inflate(R.layout.activity_kitchen_order_item_layout, null);
                    orderItemLayout = (LinearLayout) view.findViewById(R.id.kitchen_order_item_layout);
                    bottomLayout.addView(view);
                    if (currentPage == i / 3) {
                        orderItemLayout.setBackgroundResource(R.drawable.bg_order_select_layout);
                        tempLayout = orderItemLayout;
                    } else {
                        orderItemLayout.setBackgroundResource(R.drawable.bg_order_layout);
                    }
                    addOnClickListener(orderItemLayout, i / 3);
                }
                View view = getLayoutInflater().inflate(R.layout.activity_kitchen_item_container, null);
                LinearLayout itemLayout = (LinearLayout) view.findViewById(R.id.orderItemLayout);
                TextView tableName = (TextView) view.findViewById(R.id.tableName);
                TextView timePM = (TextView) view.findViewById(R.id.timePM);
                Order order = listOrder.get(i);

                tableName.setText(order.getTableName());
                String startTime = order.getOrderTime();

                timePM.setText(POSCalendarUtil.displayTimeByDate(startTime, prefUtil.getTimeFormat24(prefUtil.isFullFormat())));
                // tableName.setTextSize(prefUtil.getFontSize());
                // timePM.setTextSize(prefUtil.getFontSize());
                orderItemLayout.addView(view);
                long time = POSCalendarUtil.getMins(order.getOrderTime(), CalendarUtil.getDateTime());
                if (time >= prefUtil.getRedMinutes()) {
                    itemLayout.setBackgroundResource(R.color.after30min_red);
                } else if (time < prefUtil.getRedMinutes() && time >= prefUtil.getGreenMinutes()) {
                    itemLayout.setBackgroundResource(R.color.after15min_green);
                } else if (time < prefUtil.getGreenMinutes() && time >= prefUtil.getYellowMinutes()) {
                    itemLayout.setBackgroundResource(R.color.after0min_yellow);
                } else {
                    itemLayout.setBackgroundResource(R.color.after0min_blue);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.menu_current) {
            isHistory = false;
            menu.findItem(R.id.menu_current).setVisible(false);
            menu.findItem(R.id.menu_history).setVisible(true);
            return true;
        } else if (item.getItemId() == R.id.menu_history) {
            isHistory = true;
            menu.findItem(R.id.menu_current).setVisible(true);
            menu.findItem(R.id.menu_history).setVisible(false);
            return true;
        } else if (item.getItemId() == R.id.menu_setting) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    public Order getRemoveOrder() {
        return removeOrder;
    }

    public void setRemoveOrder(Order removeOrder) {
        this.removeOrder = removeOrder;
    }

    public boolean isHistory() {
        return isHistory;
    }

    public void setIsHistory(boolean isHistory) {
        this.isHistory = isHistory;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int position) {
        this.positionY = position;
    }

    public KitchenHelper getHelper() {
        return mHelper;
    }


    private class ComparatorOrder implements Comparator<Order> {
        @Override
        public int compare(Order o1, Order o2) {
            if (o1.getOrderTime().compareTo(o2.getOrderTime()) == 0) {
                return o1.getOrderNum().compareTo(o2.getOrderNum());
            } else {
                return o1.getOrderTime().compareTo(o2.getOrderTime());
            }
        }
    }

    private void initView() {
        setTitle(R.string.titleKitchen);
        bottomLayout = $(R.id.bottom_layout);
        fragmentLayout = $(R.id.fragment_container);
        currentLayout = $(R.id.currentLayout);
        historyLayout = $(R.id.historyLayout);
        settingLayout = $(R.id.settingLayout);
        summaryLayout = $(R.id.summaryLayout);
        titleName = $(R.id.titleName);
        tvMessage = $(R.id.message);
        menuSummary = $(R.id.menuSummary);
        listOrder = new ArrayList<>();
        allListOrder = new ArrayList<>();
        currentLayout.setOnClickListener(this);
        historyLayout.setOnClickListener(this);
        settingLayout.setOnClickListener(this);
        summaryLayout.setOnClickListener(this);
    }

    /*
      * 註冊安裝
      */
    private TaskAsyncCallBack taskInstallLicense = new TaskAsyncCallBack() {

        private String status;
        private Map<String, Object> taskResult;

        @Override
        public void setupData() {
            LicenseService licneseService = new LicenseService();
            taskResult = licneseService.install(prefUtil.getLicense());
            status = (String) taskResult.get(Constant.SERVICE_STATUS);
//            Log.i(TAG, "===taskResult:"+taskResult);
//            Log.i(TAG, "===status:"+status);
            if (Constant.STATUS_SUCCESS.equals(status)) {
                License license = (License) taskResult.get(Constant.SERVICE_DATA);
                if (license != null && license.isEnable()) {
//                    Log.i(TAG, "===saveLicense=======");
                    prefUtil.saveLicense(license);
                    //addPurchaseLicense();
                }
            }
        }

        @Override

        public void showView() {
            if (!TextUtils.isEmpty(prefUtil.getActivationKey())) {
                showFragment();
            } else {
                showLicenseInfo();
            }
        }
    };

    private void showLicenseInfo() {
  /*      int days = -1;
        try {
            String installDate = prefUtil.getInstalledDate();
            // Log.i(TAG, "installDate:" + installDate);
            // TODO this should not be problem.
            if (!TextUtils.isEmpty(installDate)) {
                days = LicenseManager.getRemindDays(installDate);
            }
            // Log.i(TAG, "days:" + days);
        } catch (LicenseException e) {
            ACRA.getErrorReporter().handleException(e);
            e.printStackTrace();
        }

        // expired
        if (days <= 0 || days > LicenseManager.TRIAL_PERIOD + LicenseManager.ONE_DAY_PERIOD) {
            showPurchaseDialog(false, getString(R.string.msgBilInApp));
        } else {
            // show reminding days
            Toast.makeText(this, String.format(getString(R.string.msgRemindDays), days), Toast.LENGTH_SHORT).show();
            showFragment();
        }*/
    }

    private void showPurchaseDialog(final boolean googlePlay, String title) {
        final AppInPurchaseDialog purchaseDialog = new AppInPurchaseDialog(this, !Config.WHITE_LABLE_VERSION);
        purchaseDialog.setCustomTitle(title);
        purchaseDialog.setCancelable(false);
        purchaseDialog.setOnPurchaseListener(new AppInPurchaseDialog.OnPurchaseListener() {
            @Override
            public void onPurchase() {
                KDSIntentUtil.purchaseWebsite(KitchenActivity.this);
               /* if (googlePlay) {
                    //No paid version
                    //IntentUtil.showAppInGooglePlay(LoginActivity.this);
                } else {
                    IntentUtil.emailPurchase(KitchenActivity.this);
                }*/
                finish();
            }
        });
        purchaseDialog.setOnRegisterListener(new AppInPurchaseDialog.OnRegisterListener() {
            @Override
            public void onRegister() {
                registerDialog();
            }
        });
        purchaseDialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    purchaseDialog.dismiss();
                    finish();
                }
                return true;
            }
        });
        purchaseDialog.show();
    }

    // 完成購買
    // Callback for when a purchase is finished
    private IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            if (result.isFailure()) {
                // complain("Error purchasing: " + result);
                return;
            }
            try {
                LicenseManager.addPurchase(KitchenActivity.this, purchase);
                recreate();
            } catch (LicenseException e) {
                ACRA.getErrorReporter().handleException(e);
                e.printStackTrace();
            }
        }
    };

    private IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        @Override
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");
            if (result.isFailure()) {
                // complain("Failed to query inventory: " + result);
                return;
            }

            try {
                Purchase purchase = inventory.getPurchase(Config.PRODUCT_FULL);
                if (purchase != null && purchase.getPurchaseState() == IabHelper.PURCHASE_STATE_PURCHASED) {
                    // Toast.makeText(PurchaseActivity.this, "===>purchase<=======" + purchase, Toast.LENGTH_LONG).show();
                    LicenseManager.addPurchase(KitchenActivity.this, purchase);
                } else {
                    LicenseManager.deletePurchase(KitchenActivity.this, Config.PRODUCT_FULL);
                }
            } catch (LicenseException e) {
                ACRA.getErrorReporter().handleException(e);
                e.printStackTrace();
            }

        }
    };

    private void registerDialog() {
        final ProductRegistrationDialog dialog = new ProductRegistrationDialog(this, prefUtil.getLicense());
        dialog.setonUnlockListener(new ProductRegistrationDialog.OnUnlockListener() {
            @Override
            public void onUnlock(License license) {
                TaskAsyncCustom dataTask = new TaskAsyncCustom(new TaskLicense(license, dialog), KitchenActivity.this);
                dataTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);
            }
        });
        dialog.setOnBackListener(new ProductRegistrationDialog.OnBackListener() {
            @Override
            public void onBack() {
                finish();
            }
        });

        dialog.show();
    }

    private class TaskLicense implements TaskAsyncCallBack {
        private Map<String, Object> taskResult;
        private License license;
        private LicenseService licneseService;
        private ProductRegistrationDialog dialog;

        public TaskLicense(License license, ProductRegistrationDialog dialog) {
            this.license = license;
            licneseService = new LicenseService();
            this.dialog = dialog;
        }

        @Override
        public void setupData() {
            taskResult = licneseService.register(license);
        }

        @Override
        public void showView() {
            String status = (String) taskResult.get(Constant.SERVICE_STATUS);
//			Log.i(TAG, "license result:"+status);
            if (Constant.STATUS_SUCCESS.equals(status)) {
                // save license to preference
                License license = (License) taskResult.get(Constant.SERVICE_DATA);
                prefUtil.saveLicense(license);
//				Log.i(TAG, "save license:"+license);
                addPurchaseLicense();
                dialog.dismiss();
                recreate();
            } else if (BaseConstant.SERVER_REGISTER_FAIL.equals(status)) {
                Toast.makeText(KitchenActivity.this, getString(R.string.errorKey), Toast.LENGTH_LONG).show();
            } else if (BaseConstant.SERVER_FAIL.equals(status)) {
                Toast.makeText(KitchenActivity.this, R.string.errorServerExcetpion, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(KitchenActivity.this, R.string.errorServer, Toast.LENGTH_LONG).show();
            }
        }

    }

    private void addPurchaseLicense() {
        Date date = new Date();
        long purchaseTime = date.getTime();
        Purchase purchase = new Purchase(IabHelper.ITEM_TYPE_INAPP, Config.ORDER_ID_FULL, Config.PRODUCT_FULL, purchaseTime);
        try {
            LicenseManager.addPurchase(KitchenActivity.this, purchase);
        } catch (LicenseException e) {
            ACRA.getErrorReporter().handleException(e);
            e.printStackTrace();
        }
    }

    /*
     * remove disable license
     */
    private TaskAsyncCallBack taskCheckLicense = new TaskAsyncCallBack() {

        @Override
        public void setupData() {
            LicenseService licneseService = new LicenseService();
            Map<String, Object> taskResult = licneseService.checkLicense(prefUtil.getLicense().getSerialNumber());
            String status = (String) taskResult.get(Constant.SERVICE_STATUS);
            if (Constant.STATUS_SUCCESS.equals(status)) {
                License license = (License) taskResult.get(Constant.SERVICE_DATA);
                if (license == null || (!TextUtils.isEmpty(license.getActivationKey()) && !license.isEnable())) {
                    prefUtil.removeActivationKey();
                }
            }
        }

        @Override
        public void showView() {
        }
    };
}
