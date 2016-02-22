package com.shu.chess.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Qjkobe on 2016/2/18.
 */
public class Button extends JButton{
    private String button_caption;
    private ImageIcon icon;

    public Button(String button_caption,ImageIcon icon){
        super(button_caption);
        this.button_caption=button_caption;
        this.icon=icon;
        this.setSize(100,34);
        this.setIcon(icon);
        this.setHorizontalTextPosition(SwingConstants.CENTER);
        this.setMargin(new Insets(0,0,0,0));
        this.setIconTextGap(0);
        this.setBorderPainted(false);
        this.setBorder(null);
        this.setOpaque(false);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                ((JButton)e.getSource()).setIcon(new ImageIcon(getClass().getResource("/res\\button_press.jpg")));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                ((JButton)e.getSource()).setIcon(new ImageIcon(getClass().getResource("/res\\button_normal.jpg")));
            }
        });
    }
    public void changeIcon(ImageIcon icon){
        this.icon=icon;
        this.setIcon(icon);
    }
}
