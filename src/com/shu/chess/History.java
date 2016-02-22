package com.shu.chess;

import java.awt.*;

/**
 * Created by Qjkobe on 2016/2/19.
 */
public class History {
    //此次走的棋子代码
    private int chess;
    //被吃的棋子代码
    private int eatchess;
    //棋子历史位置
    private Point hisPoint;
    //棋子现在位置
    private Point nowPoint;

    public History(int chess, int eatchess, Point hisPoint, Point nowPoint) {
        this.chess = chess;
        this.eatchess = eatchess;
        this.hisPoint = hisPoint;
        this.nowPoint = nowPoint;
    }
    public int getChess() {
        return chess;
    }
    public void setChess(int chess) {
        this.chess = chess;
    }
    public int getEatchess() {
        return eatchess;
    }
    public void setEatchess(int eatchess) {
        this.eatchess = eatchess;
    }
    public Point getHisPoint() {
        return hisPoint;
    }
    public void setHisPoint(Point hisPoint) {
        this.hisPoint = hisPoint;
    }
    public Point getNowPoint() {
        return nowPoint;
    }
    public void setNowPoint(Point nowPoint) {
        this.nowPoint = nowPoint;
    }
}
