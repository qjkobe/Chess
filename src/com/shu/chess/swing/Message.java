package com.shu.chess.swing;

import com.shu.chess.Start;
import sun.plugin.javascript.JSClassLoader;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.event.*;

/**
 * Created by Qjkobe on 2016/2/18.
 */
public class Message extends JPanel{
    public JTextField jx;
    public JTextArea jt;
    public Message(){
        setSize(251,332);
        this.setLayout(null);
        init();
    }
    public void init(){
        jt=new JTextArea();
        jt.setEditable(false);
        jt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
            }
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(jt);
        scrollPane.setBounds(0,0,240,292);
        scrollPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setLocation(0,0);
        add(scrollPane);

        jx = new JTextField();
        Button jbsend=new Button("发送",new ImageIcon(getClass().getResource("/res\\button_normal.jpg")));

        jx.setSize(140,36);
        jx.setLocation(0,292);
        add(jx);

        jbsend.setSize(95,36);
        jbsend.setLocation(140,292);
        add(jbsend);

        jx.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==e.VK_ENTER)
                    sendMsg();
            }
        });
        jbsend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMsg();
            }
        });
        jbsend.addMouseListener(new MouseAdapter() {
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

    public void sendMsg(){
        if(!"".equals(jx.getText().trim())){
            if(Start.socket!=null){
                Start.socket.sendData("M/*&"+jx.getText().trim());
            }
            Start.message.jt.setText(Start.message.jt.getText()+"我说:"+jx.getText().trim()+"\n");
            jx.setText("");
        }
    }
}
