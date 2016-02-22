package com.shu.chess.swing;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Qjkobe on 2016/2/18.
 */
public class Msg extends JPanel {
    private Image img;
    public JLabel jl;
    public static String Server_name;
    public Msg(Image img){
        Dimension size=new Dimension(img.getWidth(null),img.getHeight(null));
        this.setSize(size);
        this.setPreferredSize(size);
        this.setMaximumSize(size);
        this.setMaximumSize(size);
        this.img=img;
        this.setLayout(new GridLayout(1,2));
        init();
    }
    public void paintComponent(Graphics g){
        g.drawImage(img,0,0,null);
    }
    public void init(){
        jl=new JLabel("消息");
        add(jl);
    }
}
