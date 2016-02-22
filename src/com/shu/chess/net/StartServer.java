package com.shu.chess.net;

import com.shu.chess.Start;
import com.shu.chess.swing.Chess;
import com.shu.chess.swing.UserName;

import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Qjkobe on 2016/2/18.
 */
public class StartServer extends Thread {
    static ServerSocket serverSocket;

    public void run() {
        try {
            serverSocket = new ServerSocket(5002);
            Start.message.jt.setText(Start.message.jt.getText() + "建立主机成功！等待用户连入...... \n");
            Start.isServer = 1;
            UserName.Server_name = Start.userName.jt.getText().trim();
            Start.connection.setButtonEd("button_server", false);
            Start.connection.setButtonEd("button_connect", false);
            Start.connection.setButtonEd("button_flush", false);

            Socket socket = serverSocket.accept();
            Start.connection.setButtonEd("button_exit", true);
            Start.message.jt.setText(Start.message.jt.getText() + "已有用户连接连接，正建立通信...... \n");
            Chess.JBStart.setEnabled(true);
            Start.socket = new NetSocket(socket);

            Start.isConnected = 1;
        } catch (BindException e) {
            Start.message.jt.setText(Start.message.jt.getText() + "本机已建立主机，不能重复建立 (ˇ?ˇ）～\n");
        } catch (Exception e) {
            Start.message.jt.setText(Start.message.jt.getText() + "IO错误，主机建立失败(ˇ?ˇ）～ \n");
        }
    }

    public static void close() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
