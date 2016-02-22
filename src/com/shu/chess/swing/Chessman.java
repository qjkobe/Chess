package com.shu.chess.swing;

import com.shu.chess.Start;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Qjkobe on 2016/2/19.
 */
public class Chessman extends JLabel {
    public Chessman(ImageIcon img) {
        this.setSize(img.getImage().getWidth(null), img.getImage().getHeight(null));
        this.setIcon(img);
        this.setIconTextGap(0);
        this.setBorder(null);
        this.setText(null);
        this.setOpaque(false);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int X = (Chessman.this.getLocation().x - Chessboard.BORDER_LENGTH + Chessboard.GRID_LENGTH / 2) / Chessboard.GRID_LENGTH;
                int Y = (Chessman.this.getLocation().y - Chessboard.BORDER_LENGTH + Chessboard.GRID_LENGTH / 2) / Chessboard.GRID_LENGTH;

                int r = Chessboard.chess_have[X][Y] - Start.judgRB();

                if (Start.chessState == 1) {
                    //选中棋子，欲下
                    if (r > 0 && r < 8) {
                        Chessboard.isSelected = 1;
                        Chessboard.hisPoint = new Point(X, Y);
                        if (Start.select != null) {
                            Start.select.setFlag(false);
                        }
                        Start.select = new Select(Chessman.this);
                        Start.select.start();
                    }
                }

                //吃棋
                if ((r < 1 || r > 7) && Chessboard.isSelected == 1) {
                    Chessboard.nowPoint = new Point(X, Y);
                    if (Start.rule.checkRule(Chessboard.hisPoint, Chessboard.nowPoint)) {
                        Start.chessboard.playChess();
                    }
                }

            }
        });
    }
}
