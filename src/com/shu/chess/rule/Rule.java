package com.shu.chess.rule;

import com.shu.chess.swing.Chessboard;

import java.awt.*;

/**
 * Created by Qjkobe on 2016/2/18.
 */
public class Rule {
    int hisX;
    int hisY;
    int nowX;
    int nowY;

    public boolean checkRule(Point hisPoint, Point nowPoint) {
        int chess = Chessboard.chess_have[hisPoint.x][hisPoint.y];
        hisX = hisPoint.x;
        hisY = hisPoint.y;
        nowX = nowPoint.x;
        nowY = nowPoint.y;
        boolean return_flag = false;

        if (hisX != nowX || hisY != nowY) {
            if (checkNowLocation())
                return false;
            switch (chess % 7) {
                case 1:
                    return_flag = checkVehicle();
                    break;
                case 2:
                    return_flag = checkHorse();
                    break;
                case 3:
                    return_flag = checkElephant();
                    break;
                case 4:
                    return_flag = checkScholar();
                    break;
                case 5:
                    return_flag = checkKing();
                    break;
                case 6:
                    return_flag = checkCannon();
                    break;
                case 0:
                    return_flag = checkSoldier();
                    break;
                default:
                    break;
            }
        }
        return return_flag;
    }

    //判断当前要走的那步棋是否有自己的棋存在，有返回true，没有返回false
    public boolean checkNowLocation() {
        if (Chessboard.chess_have[nowX][nowY] > 0 && (Chessboard.chess_have[nowX][nowY] / 8 == Chessboard.chess_have[hisX][hisY] / 8)) {
            return true;
        }
        return false;
    }

    /*
    一步之间棋子的数目
     */
    public int betweenCount() {
        int count = 0;
        if (nowX == hisX) {
            for (int i = Math.min(nowY, hisY) + 1; i < Math.max(nowY, hisY); i++) {
                if (Chessboard.chess_have[nowX][i] > 0)
                    count++;
            }
        } else if (nowY == hisY) {
            for (int i = Math.min(nowX, hisX) + 1; i < Math.max(nowX, hisX); i++) {
                if (Chessboard.chess_have[i][nowY] > 0)
                    count++;
            }
        }
        return count;
    }

    //返回红棋黑棋位置变换参数
    public int getr() {
        //位置在棋盘上面
        if (hisY < 5)
            return 0;
            //位置在棋盘下面
        else
            return 7;

    }

    //兵的规则
    public boolean checkSoldier() {
        //兵只能向前走，因为只在走棋的时候检查，且双方兵都是从下方出发，所以兵的Y值减少代表向前
        //向前走
        if ((nowY - hisY) == -1 && nowX == hisX) {
            return true;
        }
        //过河兵规则
        if (hisY < 5) {
            if (Math.abs(nowX - hisX) == 1)
                return true;
        }
        return false;
    }

    //车的规则
    public boolean checkVehicle() {
        if (nowX == hisX || nowY == hisY) {
            if (betweenCount() == 0)
                return true;
        }
        return false;
    }

    //马的规则
    public boolean checkHorse() {
        //分别检查马的四个方向
        if ((nowY - hisY) == 2 && Math.abs(nowX - hisX) == 1) {
            //检查蹩脚马
            if (Chessboard.chess_have[hisX][hisY + 1] > 0)
                return false;
            return true;
        } else if ((nowY - hisY) == -2 && Math.abs(nowX - hisX) == 1) {
            if (Chessboard.chess_have[hisX][hisY - 1] > 0)
                return false;
            return true;
        } else if ((nowX - hisX) == 2 && Math.abs(nowY - hisY) == 1) {
            if (Chessboard.chess_have[hisX + 1][hisY] > 0)
                return false;
            return true;
        } else if ((nowX - hisX) == -2 && Math.abs(nowY - hisY) == 1) {
            if (Chessboard.chess_have[hisX - 1][hisY] > 0)
                return false;
            return true;
        }
        return false;
    }

    //炮的规则
    public boolean checkCannon() {
        int count = betweenCount();

        if (nowX == hisX || nowY == hisY) {
            if (count == 0) {
                if (Chessboard.chess_have[nowX][nowY] > 0)
                    return false;
                return true;
            } else if (count == 1 && Chessboard.chess_have[nowX][nowY] > 0) {
                if ((Chessboard.chess_have[nowX][nowY] / 8 == Chessboard.chess_have[hisX][hisY] / 8))
                    return false;
                return true;
            }
        }
        return false;
    }

    //相的规则
    public boolean checkElephant() {
        //在同一边
        if (nowY / 5 == hisY / 5) {
            //分别检查相的四个方向
            if ((nowY - hisY) == 2 && (nowX - hisX) == 2) {
                //检查相心是否被填
                if (Chessboard.chess_have[hisX + 1][hisY + 1] > 0)
                    return false;
                return true;
            } else if ((nowY - hisY) == 2 && (nowX - hisX) == -2) {
                if (Chessboard.chess_have[hisX - 1][hisY + 1] > 0)
                    return false;
                return true;
            } else if ((nowY - hisY) == -2 && (nowX - hisX) == 2) {
                if (Chessboard.chess_have[hisX + 1][hisY - 1] > 0)
                    return false;
                return true;
            } else if ((nowY - hisY) == -2 && (nowX - hisX) == -2) {
                if (Chessboard.chess_have[hisX - 1][hisY - 1] > 0)
                    return false;
                return true;
            }
        }
        return false;
    }

    //士的规则
    public boolean checkScholar() {
        //士必须在一个框内活动
        if ((nowX >= 3 && nowX <= 5) && (nowY >= (0 + getr()) && nowY <= (2 + getr()))) {
            //士必须斜则走
            if (Math.abs(nowX - hisX) == 1 && Math.abs(nowY - hisY) == 1)
                return true;
        }
        return false;
    }

    //将的规则
    public boolean checkKing() {
        //将可以杀对方老王
        if (nowX == hisX && Chessboard.chess_have[nowX][nowY] % 7 == 5) {
            if (betweenCount() == 0)
                return true;
        }
        //将必须在一个框内活动
        if ((nowX >= 3 && nowX <= 5) && (nowY >= (0 + getr()) && nowY <= (2 + getr()))) {
            //将必须横竖走
            if ((Math.abs(nowX - hisX) == 1 && Math.abs(nowY - hisY) == 0) || (Math.abs(nowX - hisX) == 0 && Math.abs(nowY - hisY) == 1))
                return true;
        }
        return false;
    }
}
