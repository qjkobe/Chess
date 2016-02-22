package com.shu.chess.swing;

import com.shu.chess.Start;
import com.shu.chess.net.NetSocket;
import com.shu.chess.net.StartServer;
import com.shu.chess.net.UdpServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by Qjkobe on 2016/2/18.
 */
public class Connection extends JPanel {
    public DefaultListModel model;
    public JList server;
    public static long validate;
    public HashMap serverInfo = new HashMap();
    private final Button button_server;
    private final Button button_connect;
    private final Button button_flush;
    private final Button button_exit;

    public Connection() {
        setSize(235, 258);
        this.setPreferredSize(new Dimension(235, 258));
        this.setMaximumSize(new Dimension(235, 258));
        this.setMaximumSize(new Dimension(235, 258));
        setBackground(new Color(210, 165, 97));
        model = new DefaultListModel();
        server = new JList(model);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(server);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.setLayout(new GridLayout(1, 2));
        add(scrollPane);

        button_server = new Button("建立主机", new ImageIcon(Start.chessboard.getdir("/res\\button_normal.jpg")));
        button_connect = new Button("连接主机", new ImageIcon(Start.chessboard.getdir("/res\\button_normal.jpg")));
        button_flush = new Button("刷新主机", new ImageIcon(Start.chessboard.getdir("/res\\button_normal.jpg")));
        button_exit = new Button("退出对战", new ImageIcon(Start.chessboard.getdir("/res\\button_normal.jpg")));

        Box box = Box.createVerticalBox();
        box.add(button_server);
        box.add(Box.createVerticalStrut(40));
        box.add(button_connect);
        box.add(Box.createVerticalStrut(40));
        box.add(button_flush);
        box.add(Box.createVerticalStrut(40));
        box.add(button_exit);

        add(box);

        //添加鼠标事件
        button_server.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ("".equals(Start.userName.jt.getText().trim()))
                    Start.message.jt.setText(Start.message.jt.getText() + "用户名为空，主机建立失败(ˇ?ˇ）～请填写用户名\n");
                else {
                    new StartServer().start();
                }
            }
        });
        //建立连接
        button_connect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int index = server.getSelectedIndex();
                String serverIp = (String) serverInfo.get(String.valueOf(index));
                if (index == -1) {
                    Start.message.jt.setText(Start.message.jt.getText() + "未选择主机，连接失败(ˇ?ˇ）～请选择主机！\n");
                } else {
                    try {
                        Start.message.jt.setText(Start.message.jt.getText() + "正建立通信...... \n");
                        Start.socket = new NetSocket(new Socket(InetAddress.getByName(serverIp), 5002));
                        Start.connection.setButtonEd("button_server", false);
                        Start.connection.setButtonEd("button_connect", false);
                        Start.connection.setButtonEd("button_flush", false);
                        Chess.JBStart.setEnabled(true);
                    } catch (IOException ex) {
                        Start.message.jt.setText(Start.message.jt.getText() + "IO错误，连接失败(ˇ?ˇ）～ \n");
                    }
                }
            }
        });
        button_flush.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.clear();
                serverInfo.clear();
                validate = System.currentTimeMillis();
                UdpServer.UdpSendMsg("F/*&" + validate);
            }
        });
        button_exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public void clear(){
        model.clear();
    }

    public void add(String s){
        model.add(model.getSize(),s);
    }

    public void setButtonEd(String buttonName,boolean isEdit){
        if(buttonName.equals("button_server"))
            button_server.setEnabled(isEdit);
        else if (buttonName.equals("button_connect"))
            button_connect.setEnabled(isEdit);
        else if (buttonName.equals("button_flush"))
            button_flush.setEnabled(isEdit);
        else if (buttonName.equals("button_exit"))
            button_exit.setEnabled(isEdit);
    }
}
