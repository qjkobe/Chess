package com.shu.chess.swing;

import com.shu.chess.Start;
import com.shu.chess.sounds.BasicPlayer;
import com.shu.login.service.impl.UserServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Qjkobe on 2016/2/18.
 */
public class Chess extends JPanel {
    public static Button JBStart;
    public static Button JBRepent;
    public static Button JBFail;
    public static Button JBPeace;
    UserServiceImpl service=new UserServiceImpl();

    public Chess() {
        setSize(554, 36);
        setBackground(new Color(210, 165, 97));
        this.setLayout(null);
        init();
    }

    public void init() {
        JBStart = new Button("开始", new ImageIcon(Start.chessboard.getdir("/res\\button_normal.jpg")));
        JBRepent = new Button("悔棋", new ImageIcon(Start.chessboard.getdir("/res\\button_normal.jpg")));
        JBFail = new Button("认输", new ImageIcon(Start.chessboard.getdir("/res\\button_normal.jpg")));
        JBPeace = new Button("和棋", new ImageIcon(Start.chessboard.getdir("/res\\button_normal.jpg")));

        JBStart.setSize(95, 36);
        JBRepent.setSize(95, 36);
        JBFail.setSize(95, 36);
        JBPeace.setSize(95, 36);

        JBStart.setLocation(0, 0);
        JBRepent.setLocation(153, 0);
        JBFail.setLocation(306, 0);
        JBPeace.setLocation(459, 0);

        add(JBStart);
        add(JBRepent);
        add(JBFail);
        add(JBPeace);

        JBStart.setEnabled(false);
        JBRepent.setEnabled(false);
        JBFail.setEnabled(false);
        JBPeace.setEnabled(false);

        JBStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Start.socket != null) {
                    Start.socket.sendData("S/*&1");
                    //System.out.println("S/*&");
                    JBStart.setEnabled(false);
                    Start.chessConfirm++;
                    switch (Start.chessConfirm) {

                        case 1:
                            Start.message.jt.setText(Start.message.jt.getText() + "你已确认开始，等待对方确认...... \n");
                            break;
                        case 2: {
                            Start.message.jt.setText(Start.message.jt.getText() + "双方已确认开始，可以下棋了！ \n");
                            Start.chessCount++;
                            JBFail.setEnabled(true);
                            JBPeace.setEnabled(true);
                            Start.chessboard.init();
                            Start.judgeOffensive();
                        }
                        break;
                        default:
                            break;
                    }
                }
            }
        });

        JBRepent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Start.socket != null) {
                    Start.socket.sendData("H/*&1");
                }
                JBRepent.setEnabled(false);
            }
        });

        JBFail.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 int yesorNo = JOptionPane.showConfirmDialog(null, "您确实要认输吗？", "提示信息", JOptionPane.YES_NO_OPTION);
                 if (yesorNo == JOptionPane.YES_OPTION) {
                     new BasicPlayer("/sound/gameover.wav").start();
                     String name=Start.getNowname();
                     service.updateScore(service.queryScore(name) ,false);
                     JOptionPane.showMessageDialog(null, "你输了！", "提示信息", JOptionPane.ERROR_MESSAGE);
                     Start.chessState = 0;
                     Start.chessConfirm = 0;
                     Chess.JBStart.setEnabled(true);
                     JBRepent.setEnabled(false);
                     JBFail.setEnabled(false);
                     JBPeace.setEnabled(false);
                     if (Start.socket != null) {
                         Start.socket.sendData("R/*&1");
                     }
                 }
             }
         }
        );
        JBPeace.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                if (Start.socket!=null)
                {
                    Start.socket.sendData("E/*&1");
                }
            }
        });
    }
}
