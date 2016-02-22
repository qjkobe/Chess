package com.shu.chess.net;

import com.shu.chess.Start;
import com.shu.chess.swing.Chess;

import java.io.*;
import java.net.Socket;

/**
 * Created by Qjkobe on 2016/2/18.
 */
public class NetSocket extends Thread {
    BufferedOutputStream bos;
    BufferedInputStream bis;
    Socket socket;

    public NetSocket(Socket socket) {
        try {
            this.socket = socket;
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            bos = new BufferedOutputStream(os, 1024);
            bis = new BufferedInputStream(is, 1024);
            Start.message.jt.setText(Start.message.jt.getText()+"通信建立成功!\n");
            start();
        } catch (IOException e) {
            Start.message.jt.setText(Start.message.jt.getText()+"网络或其他原因，通信建立失败！ \n");
        }
    }

    public void run() {
        waitMsg();
    }

    public void waitMsg() {
        boolean flag = true;
        while (flag) {
            byte[] bytes = new byte[1024];
            try {
                bis.read(bytes);
                Start.handlingMsg.splitMsg(new String(bytes).trim());
            } catch (IOException e) {
                Start.message.jt.setText(Start.message.jt.getText() + "对方已退出！ \n");
                Start.chessConfirm = 0;
                Start.chessCount = 0;
                Start.chessState = 0;
                Start.isConnected = 0;
                Start.isServer = 0;
                Start.chessboard.init();
                Start.connection.setButtonEd("button_server", true);
                Start.connection.setButtonEd("button_connect", true);
                Start.connection.setButtonEd("button_flush", true);
                Start.connection.setButtonEd("button_exit", true);
                Chess.JBStart.setEnabled(false);
                Chess.JBRepent.setEnabled(false);
                Chess.JBFail.setEnabled(false);
                Chess.JBPeace.setEnabled(false);
                StartServer.close();
                flag = false;
            }
        }
    }

    //发送消息
    public void sendData(String Msg) {
        try {
            bos.write(Msg.getBytes());
            bos.flush();
        } catch (IOException e) {
            Start.message.jt.setText(Start.message.jt.getText() + "IO错误，发送消息失败！ \n");
        }
    }

    public void close() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
