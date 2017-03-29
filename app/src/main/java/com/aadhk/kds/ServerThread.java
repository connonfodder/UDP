/*******************************************************************************
 * Copyright (C) Hong Kong Android Technology Co.
 * All right reserved.
 ******************************************************************************/
package com.aadhk.kds;


import android.text.TextUtils;
import android.util.Log;

import com.aadhk.kds.util.Constant;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * 只负责接收不负责其他事情
 */
public class ServerThread implements Runnable {

    private int port;
    private static final int ECHOMAX = 61440; //发送或接收的信息最大字节数    60KB
    DatagramSocket socket = null;  //服务器
    private MessageQueue queue;
    private boolean isRunning;
    private static ServerThread instance;

    public ServerThread(MessageQueue queue, int port) {
        this.queue = queue;
        this.port = port;
        isRunning = true;
        instance = this;
    }

    public static ServerThread getInstance() {
        return instance;
    }

    public void exit() {
        isRunning = false;
        if (socket != null && !socket.isClosed()) socket.close();
        instance = null;
    }

    @Override
    public void run() {
        Log.d("jack", "------ServerThread----run-----");
        try {
            socket = new DatagramSocket(port);
            DatagramPacket packet = new DatagramPacket(new byte[ECHOMAX], ECHOMAX);
            while (isRunning) {     //TODO: 不断接收 改进: 非阻塞的Socket
                try {
                    socket.receive(packet);  // Receive packet from client 阻塞的

                    if (packet.getAddress().equals(KitchenActivity.localIP)) {      // Check source
                        Log.d("jack", "Received packet from an unknown source");
                        throw new IOException("Received packet from an unknown source");
                    }
                    Log.d("server", "------------server-----------------Handling client at " + packet.getAddress().getHostAddress() + " on port " + packet.getPort());
                    Log.d("jack", "  getOffset=" + packet.getOffset());
                    Log.d("jack", "  getLength=" + packet.getLength());
                    Log.d("jack", "  getData.getLength=" + packet.getData().length);
                    String content = new String(packet.getData(), 0, packet.getLength(), "utf-8");
                    Log.d("server", "Receive:" + content);

                    if (content.startsWith("search")) {   //为了搜索能起效果
                        packet.setData("Roger".getBytes());
                        socket.send(packet);
                        continue;
                    }
                    if (!TextUtils.isEmpty(content)) {
                        UDPMessage msg = null;
                        Gson gson = new Gson();
                        JsonReader reader = new JsonReader(new StringReader(content));
                        reader.setLenient(true);
                        try {
//                            Userinfo userinfo1 = gson.fromJson(reader, Userinfo.class);
                            msg = gson.fromJson(reader, UDPMessage.class);
                        } catch (JsonSyntaxException e) {
                            Log.d("jack", "---------JsonSyntaxException-------" + e.getMessage());
                        }
                        if (msg == null) continue;
                        Log.d("jack", "---------from client-------" + msg.toString());
                        if (msg.getRev() == UDPMessage.REV_SENDING) {    //接收到其他客户端的信息
                            UDPMessage back = msg.clone();
                            back.setRev(UDPMessage.REV_SENDED);
                            msg.setRev(UDPMessage.REV_SENDED);
                            back.setData("");    //减少传输流量
                            back.setOrderList(null);
                            Log.d("jack", "------quick response----");
                            send(packet.getAddress().getHostAddress(), back); // 将客户端发送来的信息返回给客户端
                            queue.put(msg);
                        } else if (msg.getRev() == UDPMessage.REV_SENDED) {
                            queue.releaseLock();   //释放锁 开始下一个任务
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("jack", "fail to receive");
                } finally {
                    packet.setData(new byte[ECHOMAX]);
                    packet.setLength(ECHOMAX);
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            if (socket != null && !socket.isClosed()) {
                socket.close();
                run();
            }
        }
    }


    private void send(String ip, UDPMessage msg) {
        DatagramSocket socket = null;
        try {
            InetAddress serverAddress = InetAddress.getByName(ip); // 服务器地址
            Gson gson = new Gson();
            String data = gson.toJson(msg);
            byte[] bytesToSend = data.getBytes(); // 发送的信息
            socket = new DatagramSocket();
            socket.setSoTimeout(3000); //设置阻塞时间
            // 相当于将发送的信息打包
            DatagramPacket sendPacket = new DatagramPacket(bytesToSend, bytesToSend.length, serverAddress, Constant.SOCKET_KDS_PORT);
            socket.send(sendPacket);
            //发送完了 如果没有释放锁那么需要等多久才释放锁进入下一步操作呢  那我怎么知道外面没有接收到正确的
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed())
                socket.close();
        }
    }

}
