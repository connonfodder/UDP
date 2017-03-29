/*******************************************************************************
 * Copyright (C) Hong Kong Android Technology Co.
 * All right reserved.
 ******************************************************************************/
package com.aadhk.kds;

import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.aadhk.billing.IabHelper;
import com.aadhk.kds.bean.Order;
import com.aadhk.kds.bean.OrderItem;
import com.aadhk.kds.util.Constant;
import com.aadhk.kds.util.PreferenceUtil;
import com.aadhk.kds.view.ConfirmDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.aadhk.kds.R.raw.msg;


public class KitchenHelper {
    private final static String TAG = "KitchenHelper";
    private KitchenActivity mActivity;
    private PreferenceUtil prefUtil;
    private IabHelper mIabHelper;
    private boolean isIA;
    Thread queueThread, serverThread;
    private MessageQueue queue;
    private ServerThread server;

    public KitchenHelper(KitchenActivity activity) {
        this.mActivity = activity;
        this.prefUtil = new PreferenceUtil(mActivity);
    }

    public void initSocket() {
        queue = MessageQueue.getInstance();   //队列处理任务线程
        queue.setActivity(mActivity);
        queueThread = new Thread(queue);
        queueThread.start();

        server = new ServerThread(queue, Constant.SOCKET_KDS_PORT);
        serverThread = new Thread(server);        //服务器接收数据线程
        serverThread.start();
    }

    public void closeSocket() {
        if (server != null) server.exit();
        if (queue != null) queue.exit();
        if (serverThread != null && serverThread.isAlive()) serverThread.interrupt(); //可能会出现异常不能关闭
        if (queueThread != null && queueThread.isAlive()) queueThread.interrupt();
    }

    /**
     * 确认上所有菜时弹出确认对话框，发送socket数据给客户端
     *
     * @param order
     */
    public void cookOrder(final Order order) {

        if (order.isHasVoidItem()) {
            if (mActivity.isHistory()) {
                Toast.makeText(mActivity, R.string.msgCancelOpen, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(mActivity, R.string.msgCancelClose, Toast.LENGTH_LONG).show();
            }
        } else {
            ConfirmDialog confirmDialog = new ConfirmDialog(mActivity);
            confirmDialog.setTitle(R.string.titleCookReady);
            if (mActivity.isHistory()) {
                confirmDialog.setTitle(R.string.titleCookCancel);
            }
            confirmDialog.setOnConfirmListener(new ConfirmDialog.OnConfirmListener() {
                @Override
                public void onConfirm() {
                    // 取得所有OrderItem的id去修改，防止另外一台厨房根据Order的id取消所有菜式
                    List<Long> orderItemIds = new ArrayList<>();
                    for (OrderItem orderItem : order.getOrderItems()) {
                        orderItemIds.add(orderItem.getId());
                    }

                    Gson gson = new Gson();
                    String data = gson.toJson(orderItemIds);
                    short op = mActivity.isHistory() ? UDPMessage.OP_UNCOOK : UDPMessage.OP_COOK;  //操作类型
                    UDPMessage msg = new UDPMessage(data, op, UDPMessage.REV_SENDING, order.getIp());
                    msg.setOrderId(order.getId());
                    queue.put(msg);
                    mActivity.removeOrder(order);
                }
            });
            confirmDialog.show();
        }
    }

    public void setIp(List<Order> list, String ip) {
        for (Order item : list) item.setIp(ip);
    }

    public List<Order> separateData(List<Order> listOrder, boolean isHistory) {
        List<Order> historyOrderList = new ArrayList<>();
        List<Order> currentOrderList = new ArrayList<>();
        for (Order order : listOrder) {
            Order historyOrder = order.clone();
            Order currentOrder = order.clone();
//			Log.i(TAG, "order number=="+order.getOrderNum());
            List<OrderItem> historyOrderItemList = new ArrayList<>();
            List<OrderItem> currentOrderItemList = new ArrayList<>();
            boolean hasVoidItem = false;
            String historyOrderTime = order.getOrderTime();
            String currentOrderTime = order.getOrderTime();
            for (OrderItem orderItem : order.getOrderItems()) {
                if (!TextUtils.isEmpty(orderItem.getCategoryName())) { //过滤掉空数据
//					Log.i(TAG, "orderItem.getStartTime()=="+orderItem.getStartTime()+", "+orderItem.getItemName());
                    // a.status="+Constant.STATUS_ORDERITEM_NEW+" or a.status="+Constant.STATUS_ORDERITEM_WAIT+"
                    // status!="+Constant.STATUS_ORDERITEM_NEW+" and a.status!="+Constant.STATUS_ORDERITEM_WAIT
                    if (orderItem.getStatus() == Constant.STATUS_ORDERITEM_NEW || orderItem.getStatus() == Constant.STATUS_ORDERITEM_WAIT) {
                        if (currentOrderTime == null) {
                            currentOrderTime = orderItem.getStartTime();
//							Log.i(TAG, "currentOrderTime=="+currentOrderTime);
                        } else if (currentOrderTime.compareTo(orderItem.getStartTime()) < 0) {
                            currentOrderTime = orderItem.getStartTime();
//							Log.i(TAG, "currentOrderTime=="+currentOrderTime);
                        }
                        currentOrderItemList.add(orderItem);
                    } else {
                        if (historyOrderTime == null) {
                            historyOrderTime = orderItem.getStartTime();
//							Log.i(TAG, "historyOrderTime=="+historyOrderTime);
                        } else if (historyOrderTime.compareTo(orderItem.getStartTime()) > 0) {
                            historyOrderTime = orderItem.getStartTime();
//							Log.i(TAG, "historyOrderTime=="+historyOrderTime);
                        }
                        historyOrderItemList.add(orderItem);
                        if (orderItem.getStatus() == Constant.STATUS_ORDERITEM_CANCELITEM) {
                            hasVoidItem = true;
                        }
                    }
                }
            }

            if (currentOrderItemList.size() > 0) {
                currentOrder.setOrderItems(currentOrderItemList);
                currentOrder.setHasVoidItem(false); // current data will not have void item
                if (currentOrderTime != null) {
                    currentOrder.setOrderTime(currentOrderTime);
                }
                currentOrderList.add(currentOrder);
            }
            if (historyOrderItemList.size() > 0) {
                historyOrder.setOrderItems(historyOrderItemList);
                historyOrder.setHasVoidItem(hasVoidItem);
                if (currentOrderTime != null) {
                    historyOrder.setOrderTime(historyOrderTime);
                }
                historyOrderList.add(historyOrder);
            }
        }
        if (isHistory) {
            return historyOrderList;
        } else {
            return currentOrderList;
        }
    }


    /**
     * 确认上某项菜时弹出确认对话框，发送socket数据给客户端
     */
    public void cookItem(final Order order, final OrderItem orderItem) {
        // Log.i(TAG, " cookOrder orderId:"+orderId);
        if (orderItem.getStatus() == Constant.STATUS_ORDERITEM_CANCELITEM) {
            if (mActivity.isHistory()) {
                Toast.makeText(mActivity, R.string.msgCancelOpen, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(mActivity, R.string.msgCancelClose, Toast.LENGTH_LONG).show();
            }
        } else {
            // confirm dialog
            ConfirmDialog confirmDialog = new ConfirmDialog(mActivity);
            String title = String.format(mActivity.getString(R.string.titleCookItemReady), orderItem.getItemName());
            if (mActivity.isHistory()) {
                title = String.format(mActivity.getString(R.string.titleCookItemCancel), orderItem.getItemName());
            }
            confirmDialog.setTitle(title);
            if (orderItem.getStatus() != Constant.STATUS_ORDERITEM_CANCELITEM && orderItem.getStatus() != Constant.STATUS_ORDERITEM_ORDERUP) {
                confirmDialog.setOnConfirmListener(new ConfirmDialog.OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        List<Long> orderItemIds = new ArrayList<>();
                        orderItemIds.add(orderItem.getId());
                        Gson gson = new Gson();
                        String data = gson.toJson(orderItemIds);  //在POS端已经设定好
                        short op = mActivity.isHistory() ? UDPMessage.OP_UNCOOK : UDPMessage.OP_COOK;  //操作类型
                        UDPMessage msg = new UDPMessage(data, op, UDPMessage.REV_SENDING, order.getIp(), KitchenActivity.localIP);
                        msg.setOrderId(order.getId());
                        Log.d("jack", "-----cookItem-----"+msg.toString());
                        queue.put(msg);
                        mActivity.removeOrderItem(order, orderItem);
                    }
                });
                confirmDialog.show();
            }
        }
    }

    private SoundPool sp;
    private HashMap<Integer, Integer> spMap;

    public void setupSound() {
        sp = new SoundPool(3, AudioManager.STREAM_MUSIC, 0); // 第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        spMap = new HashMap<>();
        spMap.put(1, sp.load(mActivity, msg, 1));
        sp.load(mActivity, msg, 1); // 把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级
    }

    public void playSound(int number) {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(mActivity.getApplicationContext(), notification);
        r.play();
    }
}
