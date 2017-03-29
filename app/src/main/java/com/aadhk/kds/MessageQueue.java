package com.aadhk.kds;

import android.util.Log;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 只负责处理队列任务不做其他事
 */
public class MessageQueue implements Runnable {

    private KitchenActivity mActivity;
    private static MessageQueue instance;
    private static ConcurrentLinkedQueue<UDPMessage> queue;        //优先执行队列
    private static ConcurrentLinkedQueue<UDPMessage> delayQueue;    //延迟执行队列
    private static Set<String> blockIpSet;   //IP名单
    private int mQueueSize = 0, mDelayQueueSize = 0;   //统计队列的长度
    private static CountDownLatch latch;  // 锁
    private static boolean isSuccess, isRunning, isDelay;
    private static HandleDataThread task;

    private MessageQueue() {
        instance = this;
        queue = new ConcurrentLinkedQueue<>();  //可以合理的扩容
        delayQueue = new ConcurrentLinkedQueue<>();
        blockIpSet = new HashSet<>();
        isRunning = true;
        isDelay = false;
    }

    /**
     * 一定要避免内存泄漏
     */
    public static void exit() {
        if (instance != null && isRunning) {
            isRunning = false;
            isSuccess = true;
            if (task != null && task.isAlive()) {
                task.interrupt();
            }
            if (latch != null) latch.countDown();
            latch = null;
            queue = null;
            delayQueue = null;
            blockIpSet = null;
            instance = null;
        }
    }

    @Override
    public synchronized void run() {
        Log.d("jack", "------MessageQueue----run-----");
        while (isRunning) {
            if (mQueueSize == 0 && mDelayQueueSize == 0) continue;
            UDPMessage msg = next();   //拿而不是出队列
            Log.d("jack", "  拿到任务开始执行  ");
            if (msg != null) {
                try {
                    latch = getLock();
                    task = new HandleDataThread(this, msg);  //如果不成功那么该如何通知线程
                    task.start();
                    isSuccess = false;
                    if (msg.getRev() == UDPMessage.REV_SENDED) {
                        latch.await();   //线程等待
                    } else if (msg.getRev() == UDPMessage.REV_SENDING) {
                        latch.await(5, TimeUnit.SECONDS);   //最多超时5s
                        if (task.isAlive()) {
                            Log.d("jack", "  5s 超时，自动杀死任务 ");
                            task.interrupt();
                        }
                        if (!isSuccess) continueQueue();
                    }
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private CountDownLatch getLock() {
        if (latch == null) {
            latch = new CountDownLatch(1);
        }
        if (latch.getCount() == 0) {
            latch = new CountDownLatch(1);
        }
        return latch;
    }

    /**
     * 释放锁 出队列 执行下一个任务
     */
    public void releaseLock() {
        if (latch != null) {
            try {
                UDPMessage msg = null;
                if(isDelay && mDelayQueueSize > 0){
                    msg = delayQueue.remove();
                    if (msg != null) mDelayQueueSize--;
                }else if( mQueueSize > 0){
                    msg = queue.remove();
                    if (msg != null) mQueueSize--;
                }
                if (msg != null && blockIpSet.contains(msg.getToIp())) blockIpSet.remove(msg.getToIp());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (queue.isEmpty()) mQueueSize = 0;
            if (delayQueue.isEmpty()) mDelayQueueSize = 0;
            latch.countDown();
            isSuccess = true;
        }
        Log.d("jack", "--- 释放锁 出队列 执行下一个任务 ---优先队列=" + mQueueSize + ", 延迟队列=" + mDelayQueueSize);
    }

    /**
     * 释放锁 因为任务执行失败 将当前任务的IP放到名单上 将任务放到延迟队列中去 去执行下一个任务
     */
    public void continueQueue() {
        //将队列放到延迟队列，将其IP添加到名单上
        if (latch != null) {
            latch.countDown();
            latch = null;
        }
        if (mQueueSize > 0)   //如果优先队列没有则表示现在执行的是延迟队列任务失败
            putToDelay();
        Log.d("jack", " ----释放锁 将任务放到延迟队列 执行下一个任务 -- 优先队列=" + mQueueSize + ", 延迟队列=" + mDelayQueueSize);
    }

    public static MessageQueue getInstance() {
        if (instance == null) {
            instance = new MessageQueue();
        }
        return instance;

    }

    public void setActivity(KitchenActivity mActivity) {
        this.mActivity = mActivity;
    }

    /**
     * 优先队列入队  不阻塞线程  统计任务数量  因为调用size()方法非常耗性能
     */
    public void put(UDPMessage m) {
        String ip = m.getFromIp();
        m.setFromIp(KitchenActivity.localIP);
        m.setToIp(ip);
        if (queue.offer(m)) {
            mQueueSize++;
        }
        Log.d("jack", "------MessageQueue----put----------优先队列=" + mQueueSize + ", 延迟队列=" + mDelayQueueSize);
    }

    /**
     * 延迟队列入队
     */
    public void putToDelay() {
        UDPMessage msg = queue.remove();
        if (msg != null) {
            mQueueSize--;
        }
        if (queue.isEmpty()) mQueueSize = 0;
        if (delayQueue.offer(msg)) {
            mDelayQueueSize++;
            if (!blockIpSet.contains(msg.getFromIp())) blockIpSet.add(msg.getFromIp());
        }
        Log.d("jack", "--------putToDelay----------优先队列=" + mQueueSize + ", 延迟队列=" + mDelayQueueSize);
    }

    /**
     * 获取但不删除元素
     * 优先获取主队列元素, 如果没有则获取延迟队列的元素
     */
    public UDPMessage next() {
        Log.d("jack", "----------next----------优先队列=" + mQueueSize + ", 延迟队列=" + mDelayQueueSize);
        //检测到当前的IP在名单上 给延迟队列 取下一个任务
        synchronized (this) {
            if (mQueueSize > 0) {   //优先队列不为空则优先获取
                UDPMessage msg = queue.element();
                if (msg != null) {
                    if (blockIpSet.contains(msg.getFromIp())) {   //表明该放到延迟队列
                        putToDelay();
                        return next();   //递归再获取值
                    } else {
                        isDelay = false;
                        return msg;     //优先队列的元素
                    }
                }
            } else {                //去获取延迟队列
                if (mDelayQueueSize > 0) {   //确保有数据
                    UDPMessage msg = delayQueue.element();
                    if (msg != null) {
                        isDelay = true;
                        return msg;     //延迟队列的元素
                    }
                }
            }
            return null;
        }
    }
}
