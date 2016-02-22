package com.shu.chess.net;

import com.shu.chess.Start;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * Created by Qjkobe on 2016/2/18.
 */
public class UdpServer extends Thread{
    public static String getLocalIP(){
        String IP=null;
        String stt[]= new String[0];
        try {
            stt = InetAddress.getLocalHost().toString().split("/");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        IP=stt[1];
        return IP;
    }

    //发送数据报(包含自身IP),寻求服务器的IP.
    public static void UdpSendMsg(String Msg){
        try {
            MulticastSocket ms=new MulticastSocket(5355);
            InetAddress group=InetAddress.getByName("239.110.110.110");
            byte[] buffer=Msg.getBytes();
            DatagramPacket dgp=new DatagramPacket(buffer,0,buffer.length,group,5001);

            ms.send(dgp);
            ms.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //接收消息
    public void UdpRecMsg(){
        try {
            MulticastSocket ms=new MulticastSocket(5001);
            InetAddress group=InetAddress.getByName("239.110.110.110");
            ms.joinGroup(group);
            while(true){
                byte[] buffer=new byte[256];
                DatagramPacket dgp=new DatagramPacket(buffer,buffer.length);
                ms.receive(dgp);
                Start.handlingMsg.splitMsg(new String(dgp.getData()).trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  void run(){
        UdpRecMsg();
    }
}
