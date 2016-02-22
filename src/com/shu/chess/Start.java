package com.shu.chess;

import com.shu.chess.swing.*;
import com.shu.chess.rule.*;
import com.shu.chess.net.*;

import java.awt.Dimension;
import java.awt.Graphics;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Created by Qjkobe on 2016/2/18.
 */
public class Start {
    public static Chessboard chessboard;
    public static UserName userName;
    public static Msg msg;
    public static Connection connection;
    public static Message message;
    public static UdpServer udpServer;
    public static HandingMsg handlingMsg;
    public static NetSocket socket;
    public static Chess chess;
    public static Start start;
    public static JFrame frame;
    public static Select select;
    public static Rule rule;
    //表示是否已连接，0表示未连接，1表示已连接
    public static int isConnected = 0;
    //表示是主机还是客户端，0表示客户端，1表示主机
    public static int isServer = 0;
    //下棋状态，0表示未开始，1表示我下，2表示对方下
    public static int chessState = 0;
    //下棋局数
    public static int chessCount = 0;
    //确认状态，0表示双方未确认开始，1表示一方已取认开始，2表示双方已确认开始，可以下棋了
    public static int chessConfirm = 0;
    static String nowname;

    public static String getNowname() {
        return nowname;
    }

    public static void setNowname(String nowname) {
        Start.nowname = nowname;
    }

    public class innerPane extends JPanel {
        public innerPane() {
            setLayout(null);
            Dimension size = new Dimension(235, 32);
            this.setSize(size);
            this.setPreferredSize(size);
            this.setMaximumSize(size);
            this.setMaximumSize(size);
        }

        public void paintComponent(Graphics g) {
            g.drawImage(new ImageIcon(getdir("/res\\back.jpg")).getImage(), 0, 0, null);
        }
    }

    public Start() {
        frame = new JFrame("中国象棋");
        frame.setIconImage(new ImageIcon(getdir("/res\\5.jpg")).getImage());
        chessboard = new Chessboard(new ImageIcon(getdir("/res\\main.jpg")).getImage());
        frame.getContentPane().setLayout(null);
        chessboard.setLocation(0, 0);
        frame.getContentPane().add(chessboard);

        userName = new UserName(new ImageIcon(getdir("/res\\back.jpg")).getImage());
        userName.setLocation(554, 0);
        frame.getContentPane().add(userName);

        msg = new Msg(new ImageIcon(getdir("/res\\back.jpg")).getImage());
        msg.setLocation(554, 292);
        frame.getContentPane().add(msg);

        connection = new Connection();
        connection.setLocation(554, 34);
        frame.getContentPane().add(connection);

        JPanel jPanel = new innerPane();
        jPanel.setLocation(554, 290);
        frame.getContentPane().add(jPanel);

        message = new Message();
        message.setLocation(554, 324);
        frame.getContentPane().add(message);

        chess = new Chess();
        chess.setLocation(0, 616);
        frame.getContentPane().add(chess);

        handlingMsg = new HandingMsg();
        rule = new Rule();
        udpServer = new UdpServer();
        udpServer.start();

        frame.setSize(794, 678);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

//    public static void main(String[] args) {
//        start = new Start();
//    }

    //判定先后手,单局由客户端先下,双局由服务器先下
    public static void judgeOffensive() {
        if (Start.chessCount % 2 != 0) {
            switch (Start.isServer) {
                case 0:
                    Start.chessState = 1;
                    break;
                case 1:
                    Start.chessState = 2;
                    break;
                default:
                    break;
            }
        } else {
            switch (Start.isServer) {
                case 0:
                    Start.chessState = 2;
                    break;
                case 1:
                    Start.chessState = 1;
                    break;
                default:
                    break;
            }
        }
    }

    //返回判断是红棋还是黑棋的参数
    public static int judgRB() {
        int r = 0;
//		单局由客户端先下，即客户端是红棋
        if (Start.chessCount % 2 == 1) {
            if (Start.isServer == 0) {
                r = 0;
            } else if (Start.isServer == 1) {
                r = 7;
            }
        }
        //双局由客户端后下，即服务器端是红棋
        else {
            if (Start.isServer == 0) {
                r = 7;
            } else if (Start.isServer == 1) {
                r = 0;
            }
        }
        return r;
    }

    public URL getdir(String res) {
        return getClass().getResource(res);
    }
}
