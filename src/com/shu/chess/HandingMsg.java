package com.shu.chess;

import com.shu.chess.net.UdpServer;
import com.shu.chess.sounds.BasicPlayer;
import com.shu.chess.swing.Chess;
import com.shu.chess.swing.Chessboard;
import com.shu.chess.swing.Connection;
import com.shu.chess.swing.UserName;
import com.shu.login.domain.Score;
import com.shu.login.service.impl.UserServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.util.StringTokenizer;

/**
 * Created by Qjkobe on 2016/2/19.
 */
public class HandingMsg {
    final static String SPLIT_CODE = "/*&";
    final static String SPLIT_INFO = ":";
    UserServiceImpl service=new UserServiceImpl();
    
    public void splitMsg(String Msg) {
        StringTokenizer st = new StringTokenizer(Msg, SPLIT_CODE);
        String mark = st.nextToken();
        String info = st.nextToken();

        //寻找主机信息
        if ("F".equals(mark)) {
            //回复寻找主机
            if (Start.isServer == 1 && Start.isConnected == 0) {
                String reString = "RF/*&" + info + SPLIT_INFO + UserName.Server_name + SPLIT_INFO + UdpServer.getLocalIP();
                UdpServer.UdpSendMsg(reString);
            }
        } else if ("RF".equals(mark))
            ServerInfo(info);
            //开始信息
        else if ("S".equals(mark))
            confirmStart();
            //下棋信息
        else if ("P".equals(mark))
            play(info);
            //处理悔棋请求
        else if ("H".equals(mark))
            repentReq(info);
            //处理回复的悔棋请求
        else if ("RH".equals(mark))
            repent(info);
            //处理认输请求
        else if ("R".equals(mark))
            fail();
            //处理和棋请求
        else if ("E".equals(mark))
            peaceReq();
            //处理回复的和棋请求
        else if ("RE".equals(mark))
            peace(info);
            //处理聊天信息
        else if ("M".equals(mark)) {
            Start.message.jt.setText(Start.message.jt.getText() + "对方说: " + info + "\n");
        }
    }

    public void ServerInfo(String s) {
        StringTokenizer st = new StringTokenizer(s, SPLIT_INFO);
        long mark = Long.parseLong(st.nextToken());

        if (Connection.validate == mark) {
            String ServerName = st.nextToken();
            String ServerIp = st.nextToken();
            //储存主机信息
            Start.connection.serverInfo.put(String.valueOf(Start.connection.model.getSize()), ServerIp);
            //添加主机信息到前台
            Start.connection.model.addElement(ServerName);
        }
    }

    public void confirmStart() {
        Start.chessConfirm++;
        if (Start.chessConfirm == 1)
            Start.message.jt.setText(Start.message.jt.getText() + "对方已确认开始\n");
        else if (Start.chessConfirm == 2) {
            Start.message.jt.setText(Start.message.jt.getText() + "双方已确认开始,开始对战!\n");
            Chess.JBFail.setEnabled(true);
            Chess.JBPeace.setEnabled(true);

            Start.chessCount++;

            Start.judgeOffensive();
            Start.chessboard.init();
        }
    }

    public void play(String s) {
        StringTokenizer st = new StringTokenizer(s, SPLIT_INFO);
        Point hisPoint = pointReverse(new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        Point nowPoint = pointReverse(new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        int killking = Integer.parseInt(st.nextToken());
        Chessboard.hisPoint = hisPoint;
        Chessboard.nowPoint = nowPoint;
        Start.chessboard.playChess();
        if (killking == 1)
        {
            Start.message.jt.setText(Start.message.jt.getText() + "对方将军！\n");
            new BasicPlayer("/chess/Sounds/sound/danger.wav").start();
        }
    }

    //处理悔棋请求
    public void repentReq(String s) {
        String msg = "对方想悔棋，是否同意？";
        int yesorNo = JOptionPane.showConfirmDialog(null, msg, "悔棋信息", JOptionPane.YES_NO_OPTION);
        //悔棋
        String returnMsg;
        if (yesorNo == JOptionPane.YES_OPTION) {
            Start.chessboard.replay();
            returnMsg = "RH/*&" + "1";
        } else {
            returnMsg = "RH/*&" + "0";
        }
        if (Start.socket != null) {
            Start.socket.sendData(returnMsg);
        }
    }

    public void repent(String s) {
        int yesorno = Integer.parseInt(s);
        switch (yesorno) {
            //不同意
            case 0: {
                JOptionPane.showMessageDialog(null, "对方不同意悔棋！", "悔棋回复", JOptionPane.ERROR_MESSAGE);
            }
            break;
            //同意悔棋
            case 1: {
                Start.chessboard.replay();
            }
            break;
            default:
                break;
        }
    }

    public void fail() {
        new BasicPlayer("/sound/gamewin.wav").start();
        JOptionPane.showMessageDialog(null, "对方认输，你赢了！", "提示信息", JOptionPane.ERROR_MESSAGE);
        Start.chessState = 0;
        Start.chessConfirm = 0;
        Chess.JBStart.setEnabled(true);
        Chess.JBFail.setEnabled(false);
        Chess.JBPeace.setEnabled(false);
        Chess.JBRepent.setEnabled(false);
        String name=Start.getNowname();
        service.updateScore(service.queryScore(name) ,true);

    }

    public void peaceReq() {
        String msg = "对方想和棋，是否同意？";
        int yesorNo = JOptionPane.showConfirmDialog(null, msg, "和棋信息", JOptionPane.YES_NO_OPTION);
        //悔棋
        String returnMsg;
        if (yesorNo == JOptionPane.YES_OPTION) {
            Start.chessState = 0;
            Start.chessConfirm = 0;
            Chess.JBStart.setEnabled(true);
            returnMsg = "RE/*&" + "1";
        } else {
            returnMsg = "RE/*&" + "0";
        }
        if (Start.socket != null) {
            Start.socket.sendData(returnMsg);
        }
    }

    public void peace(String s) {
        int yesorno = Integer.parseInt(s);
        switch (yesorno) {
            //不同意
            case 0: {
                JOptionPane.showMessageDialog(null, "对方不同意和棋！", "和棋回复", JOptionPane.ERROR_MESSAGE);
            }
            break;
            //同意和棋
            case 1: {
                JOptionPane.showMessageDialog(null, "对方已同意和棋，可以重新开始了！", "和棋回复", JOptionPane.ERROR_MESSAGE);
                Start.chessState = 0;
                Start.chessConfirm = 0;
                Chess.JBStart.setEnabled(true);
            }
            break;
            default:
                break;
        }
    }

    //坐标对称反转
    public Point pointReverse(Point p) {

        return new Point(p.x, 5 + (4 - p.y));

    }
}
