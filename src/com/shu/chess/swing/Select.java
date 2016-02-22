package com.shu.chess.swing;

import com.shu.chess.Start;
import com.shu.chess.sounds.BasicPlayer;

import java.awt.*;

/**
 * Created by Qjkobe on 2016/2/19.
 */
public class Select extends Thread {
    Chessman chessman;
    Point p;
    private boolean flag = true;
    private boolean isRemove = false;
    int i = 1;

    public Select(Chessman chessman) {
        this.chessman = chessman;
        p = chessman.getLocation();
        new BasicPlayer("/sound\\select.wav").start();
    }

    public void run() {
        while (flag) {
            switch (i % 2) {
                case 0:
                    Start.chessboard.add(chessman);
                    break;
                case 1:
                    Start.chessboard.remove(chessman);
                    break;
                default:
                    break;
            }
            Start.chessboard.repaint();
            i++;
            try {
                Thread.sleep(250);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!isRemove) {
            if (i % 2 == 0)
                Start.chessboard.add(chessman);
            Start.chessboard.repaint();
        }
    }
    public boolean isFlag() {
        return flag;
    }
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    public boolean isRemove() {
        return isRemove;
    }
    public void setRemove(boolean isRemove) {
        this.isRemove = isRemove;
    }
}
