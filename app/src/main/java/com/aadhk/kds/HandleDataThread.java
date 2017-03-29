package com.aadhk.kds;


import android.util.Log;

import com.aadhk.kds.util.Constant;
import com.google.gson.Gson;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


/**
 * 这里做一些实际的业务，用来处理各种不同的情况
 */
public class HandleDataThread extends Thread {
    private UDPMessage msg;
    private MessageQueue queue;

    public HandleDataThread(MessageQueue queue, UDPMessage msg) {
        this.queue = queue;
        this.msg = msg;
    }

    @Override
    public void run() {
        switch (msg.getOperation()) {
            case UDPMessage.OP_INIT_KDS:
                System.out.println("-------HandleDataThread------init kds----------------");
                //TODO: 获取数据交给UI去处理
                POSApp.getInstance().sendMsg(msg);   //这样做怕内存泄漏
                break;
            case UDPMessage.OP_SEND:
                System.out.println("-------HandleDataThread------send order----------------");
                //TODO: 获取数据交给UI去处理
                POSApp.getInstance().sendMsg(msg);
                break;
            case UDPMessage.OP_PAY:
                System.out.println("-------HandleDataThread------payment order----------------");
                //TODO: 获取数据交给UI去处理
                POSApp.getInstance().sendMsg(msg);
                break;
            case UDPMessage.OP_COOK:
                System.out.println("-------HandleDataThread------cook food----------------");
                //TODO: 发送数据
                break;
            case UDPMessage.OP_UNCOOK:
                System.out.println("-------HandleDataThread------uncook food----------------");
                //TODO: 发送数据
                break;
            default:
                System.out.println("-------HandleDataThread------unknow operation----------------");
                break;
        }
        String ip = msg.getToIp();
        Log.d("jack", "这是 " + (msg.getRev() == UDPMessage.REV_SENDED ? "单线程处理" : "多线程发送") + "操作" + msg.toString());
        if (msg.getRev() == UDPMessage.REV_SENDING) {   //主动发送
//            msg.setFromIp(KitchenActivity.localIP);
//            msg.setToIp(ip);
            if(msg.getVersion() == -1){
                msg.setVersion(POSApp.getInstance().getSendVersion(ip));
            }
            send(ip, msg);
        } else {                                        //被动接收
            queue.releaseLock();
        }
    }

    // 原则:  接收到的数据包其IP永远是发送方
    private synchronized void send(String ip, UDPMessage msg) {
        DatagramSocket socket = null;
        try {
            InetAddress serverAddress = InetAddress.getByName(ip); // 服务器地址
            Gson gson = new Gson();
            String data = gson.toJson(msg);
            Log.d("jack", "单线程发送的消息体" + data);
            byte[] bytesToSend = data.getBytes(); // 发送的信息
            socket = new DatagramSocket();
            socket.setSoTimeout(3000); //设置阻塞时间
            // 相当于将发送的信息打包
            DatagramPacket sendPacket = new DatagramPacket(bytesToSend, bytesToSend.length, serverAddress, Constant.SOCKET_KDS_PORT);
            socket.send(sendPacket);
            //发送完了 如果没有释放锁那么需要等多久才释放锁进入下一步操作呢  那我怎么知道外面没有接收到正确的
            Log.d("jack", "单线程发送完毕，等待回传信号");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed())
                socket.close();
        }
    }
}
