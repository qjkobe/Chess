package com.shu.chess.swing;

import com.shu.chess.History;
import com.shu.chess.Start;
import com.shu.chess.rule.ChessInit;
import com.shu.chess.sounds.BasicPlayer;
import com.shu.login.service.impl.UserServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Qjkobe on 2016/2/18.
 */
public class Chessboard extends JPanel {
    private Image img;
    final static int BORDER_LENGTH = 40;//棋盘顶上距离
    final static int GRID_LENGTH = 58;//棋盘格子长与高
    final static int CHESSMAN_LENGTH = 52;//棋子长与高

    private int X;
    private int Y;
    //0 表示该位置没有下棋，1-7表示红棋车，马，象，士，将，炮，兵，8-14 表示黑棋车，马，象，士，将，炮，兵
    //内存棋盘数组
    public static int[][] chess_have = new int[9][10];
    public static int isSelected = 0;//1表示选中
    public static Point hisPoint;//选中棋子位置
    public static Point nowPoint;//要走的位置
    int chess_count = 0;
    public static Point king;//王位置
    public static int kill = 0;//1表示将军
    ArrayList danger = new ArrayList();//自己将军位置;
    HashMap history = new HashMap();

    UserServiceImpl service=new UserServiceImpl();

    public Chessboard(Image img) {
        this.img = img;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        this.setSize(size);
        this.setLayout(null);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                X = (e.getX() - BORDER_LENGTH + GRID_LENGTH / 2) / GRID_LENGTH;
                Y = (e.getY() - BORDER_LENGTH + GRID_LENGTH / 2) / GRID_LENGTH;
                if ((X >= 0 && X <= 8) && (Y >= 0 && Y <= 9)) {
                    nowPoint = new Point(X, Y);
                    if (isSelected == 1 && Start.rule.checkRule(Chessboard.hisPoint, Chessboard.nowPoint))
                        playChess();
                }
            }
        });
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    public void playChess() {

        //下棋步数加一
        chess_count++;
        history.put(String.valueOf(chess_count), new History(chess_have[hisPoint.x][hisPoint.y],
                chess_have[nowPoint.x][nowPoint.y], hisPoint, nowPoint));
        //停止闪烁
        if (Start.select != null) {
            Start.select.setFlag(false);
            Start.select.setRemove(true);
        }
        //前台绘图
        if (chess_have[nowPoint.x][nowPoint.y] > 0) {
            //吃棋
            this.remove(getComponentAt(getLocation(nowPoint)));
        }
        this.remove(getComponentAt(getLocation(hisPoint)));
        add(getChessman(chess_have[hisPoint.x][hisPoint.y], nowPoint));
        //记录对方老王位置
        if (chess_have[hisPoint.x][hisPoint.y] % 7 == 5 && hisPoint.y < 5)
            king = nowPoint;
        String msg = checkWin();
        //加载声音
        if (chess_have[nowPoint.x][nowPoint.y] > 0)
            new BasicPlayer("/sound\\eat.wav").start();
        else
            new BasicPlayer("/sound\\go.wav").start();
        //内存数组变换
        chess_have[nowPoint.x][nowPoint.y] = chess_have[hisPoint.x][hisPoint.y];
        chess_have[hisPoint.x][hisPoint.y] = 0;

        isSelected = 0;

        if (Start.chessState == 1) {
            Chess.JBRepent.setEnabled(true);
            int killking = checkKill();
            if (killking == 1) {
                new BasicPlayer("/sound\\danger.wav").start();
                Start.message.jt.setText(Start.message.jt.getText() + "我方将军！\n");
            }
            if (Start.socket != null) {
                Start.socket.sendData("P/*&" + hisPoint.x + ":" + hisPoint.y + ":" + nowPoint.x + ":" + nowPoint.y + ":" + killking);
            }
        } else
            Chess.JBRepent.setEnabled(false);

        changeState();

        repaint();
        if (msg != null) {
            if (msg.equals("你赢啦，恭喜恭喜！")) {
                new BasicPlayer("/sound\\gamewin.wav").start();
                Chess.JBFail.setEnabled(false);
                Chess.JBPeace.setEnabled(false);
                Chess.JBRepent.setEnabled(false);
            }
            else {
                new BasicPlayer("/sound\\gameover.wav").start();
                Chess.JBFail.setEnabled(false);
                Chess.JBPeace.setEnabled(false);
                Chess.JBRepent.setEnabled(false);
            }
            JOptionPane.showMessageDialog(null, msg, "提示信息", JOptionPane.ERROR_MESSAGE);
            //init();
            Start.chessState = 0;
            Start.chessConfirm = 0;
            Chess.JBStart.setEnabled(true);
        }
    }

    //悔棋,悔最后一步
    public void replay() {

        if (chess_count > 0) {
            //remove(chess_count-1);
            History his = (History) history.get(String.valueOf(chess_count));
            this.remove(getComponentAt(getLocation(his.getNowPoint())));
            //还原被吃的棋子
            if (his.getEatchess() != 0) {
                add(getChessman(his.getEatchess(), his.getNowPoint()));
            }
            //还原棋子
            add(getChessman(his.getChess(), his.getHisPoint()));
            repaint();

            //内存棋盘数组同步更新
            chess_have[his.getHisPoint().x][his.getHisPoint().y] = his.getChess();
            chess_have[his.getNowPoint().x][his.getNowPoint().y] = his.getEatchess();

            history.remove(String.valueOf(chess_count));
            chess_count--;

            if (Start.select != null)
                Start.select.setFlag(false);

            changeState();
        }
    }

    public String checkWin() {
        String Msg = null;
        if (chess_have[nowPoint.x][nowPoint.y] % 7 == 5) {
            if (nowPoint.y < 5) {
                Msg = "你赢啦，恭喜恭喜！";
                String name=Start.getNowname();
                service.updateScore(service.queryScore(name) ,true);
            } else {
                Msg = "你输啦，真是遗憾！";
                String name = Start.getNowname();
                service.updateScore(service.queryScore(name), false);
            }
        }
        return Msg;
    }

    public void init() {
        this.removeAll();//初始化
        ChessInit chessInit = new ChessInit();

        //单局客户端先下,所以客户端是红棋
        if (Start.chessCount % 2 == 1) {
            if (Start.isServer == 0) {
                chessInit.initTop(7);
                chessInit.initUnder(0);
            } else if (Start.isServer == 1) {
                chessInit.initTop(0);
                chessInit.initUnder(7);
            }
        } else {
            if (Start.isServer == 0) {
                chessInit.initTop(0);
                chessInit.initUnder(7);
            } else if (Start.isServer == 1) {
                chessInit.initTop(7);
                chessInit.initUnder(0);
            }
        }
        //画出棋盘
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 10; j++) {
                if (chess_have[i][j] > 0) {
                    add(getChessman(chess_have[i][j], new Point(i, j)));
                }
            }
        }
        repaint();

        history.clear();
        isSelected = 0;
        chess_count = 0;
        king = new Point(4, 0);
        kill = 0;
        danger.clear();
    }

    public Chessman getChessman(int chessCode, Point p) {
        String imageFile = "/res/" + chessCode + ".jpg";
        Chessman man = new Chessman(new ImageIcon(getdir(imageFile)));
        man.setLocation(getLocation(p));
        return man;
    }

    public Point getLocation(Point p) {
        int x_location = p.x * GRID_LENGTH + BORDER_LENGTH - CHESSMAN_LENGTH / 2;
        int y_location = p.y * GRID_LENGTH + BORDER_LENGTH - CHESSMAN_LENGTH / 2;
        return new Point(x_location, y_location);
    }

    public URL getdir(String res) {
        return getClass().getResource(res);
    }

    //下棋状态切换
    public void changeState() {
        switch (Start.chessState) {
            case 1:
                Start.chessState = 2;
                break;
            case 2:
                Start.chessState = 1;
                break;
            default:
                break;
        }
    }

    //检查是否将军
    public int checkKill() {
        for (int i = 0; i < danger.size(); i++) {
            Point p = (Point) danger.get(i);
            if ((chess_have[p.x][p.y] / 8 == chess_have[king.x][king.y] / 8) || chess_have[p.x][p.y] == 0)
                danger.remove(i);
        }
        switch (chess_have[nowPoint.x][nowPoint.y] % 7) {
            case 1:
                danger.add(0, nowPoint);
                break;
            case 6:
                danger.add(0, nowPoint);
                break;
            case 2: {
                if (nowPoint.y < 5 && (nowPoint.x > 0 && nowPoint.x < 8))
                    danger.add(0, nowPoint);
            }
            break;
            case 0: {
                if (nowPoint.y < 4 && (nowPoint.x > 1 && nowPoint.x < 7))
                    danger.add(0, nowPoint);
            }
            break;
            default:
                break;
        }

        for (int i = 0; i < danger.size(); i++) {
            Point p = (Point) danger.get(i);
            if (Start.rule.checkRule(p, king))
                return 1;
        }
        return 0;
    }
}
