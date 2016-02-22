package com.shu.chess.rule;

import com.shu.chess.swing.Chessboard;

/**
 * Created by Qjkobe on 2016/2/18.
 */
public class ChessInit {
    //初始化棋盘上半部分
    //参数r控制是红棋/黑棋的参数,取值0或者是7
    //初始化内存棋盘数组
    public void initTop(int r) {
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 9; i++) {
                //先赋值为0
                Chessboard.chess_have[i][j] = 0;
                switch (j) {
                    //第一排,布置车，马，象，士，将
                    case 0: {
                        if (i < 5)
                            Chessboard.chess_have[i][j] = (i + 1) + r;
                        else
                            Chessboard.chess_have[i][j] = i - ((i - 5) * 2 + 1) + r;
                    }
                    break;
                    //第三排，布置炮
                    case 2: {
                        if (i == 1 || i == 7)
                            Chessboard.chess_have[i][j] = 6 + r;
                    }
                    break;
                    //第四排，布置兵
                    case 3: {
                        if (i % 2 == 0)
                            Chessboard.chess_have[i][j] = 7 + r;
                    }
                    break;
                    default:
                        break;
                }
            }
        }
    }

    //初始化棋盘下半部分
    //参数r控制是红棋/黑棋的参数,取值0或者是7
    //初始化内存棋盘数组
    public void initUnder(int r) {
        for (int j = 5; j < 10; j++) {
            for (int i = 0; i < 9; i++) {
                //先赋值为0
                Chessboard.chess_have[i][j] = 0;
                switch (j) {
                    //第十排,布置车，马，象，士，将
                    case 9: {
                        if (i < 5)
                            Chessboard.chess_have[i][j] = (i + 1) + r;
                        else
                            Chessboard.chess_have[i][j] = i - ((i - 5) * 2 + 1) + r;
                    }
                    break;
                    //第八排，布置炮
                    case 7: {
                        if (i == 1 || i == 7)
                            Chessboard.chess_have[i][j] = 6 + r;
                    }
                    break;
                    //第七排，布置兵
                    case 6: {
                        if (i % 2 == 0)
                            Chessboard.chess_have[i][j] = 7 + r;
                    }
                    break;
                    default:
                        break;
                }
            }
        }
    }
}
