package com.shu.login.swing;

import com.shu.chess.Start;
import com.shu.login.domain.User;
import com.shu.login.service.IUserService;
import com.shu.login.service.impl.UserServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.List;

/**
 * Created by Qjkobe on 2016/2/19.
 */
public class login extends JFrame{
    JTextField jTextField ;//定义文本框组件
    JPasswordField jPasswordField;//定义密码框组件
    JLabel jLabel1,jLabel2;
    JPanel jp4;
    JButton jb1,jb2; //创建按钮
//    private boolean flag=false;
//    public static List<String> listusername = new ArrayList<String>();//保存登陆过的用户


    public login(){
        jTextField = new JTextField(12);
        jPasswordField = new JPasswordField(12);
        jTextField.setMaximumSize(jTextField.getPreferredSize());
        jPasswordField.setMaximumSize(jPasswordField.getPreferredSize());
        jLabel1 = new JLabel("用户名");
        jLabel2 = new JLabel("密码");
        jb1 = new JButton("确认");
        jb2 = new JButton("取消");
        final JLabel register=new JLabel("注册");
        jp4=new JPanel();

        register.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                URI uri;
                Desktop desktop= null;
                try {
                    uri=new URI("http://localhost:8080/qjkobe/index.jsp");
                    desktop = Desktop.getDesktop();

                    if(Desktop.isDesktopSupported()&&desktop.isSupported(Desktop.Action.BROWSE))
                        desktop.browse(uri);
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                register.setForeground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                register.setForeground(Color.blue);
            }
        });

        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username=jTextField.getText();
                String password=new String(jPasswordField.getPassword());
                //System.out.println(username+":"+password);

                if(username.equals("")){
                    JOptionPane.showConfirmDialog(null, "用户名不能为空", "错误", JOptionPane.CLOSED_OPTION);
                    return;
                }
                if (password.equals("")) {
                    JOptionPane.showConfirmDialog(null, "密码不能为空", "错误", JOptionPane.CLOSED_OPTION);
                    return;
                }

                //登录
                IUserService service = new UserServiceImpl();
                User user = service.loginUser(username, password);
                if (user == null) {
                    JOptionPane.showConfirmDialog(null, "用户名或密码错误", "错误", JOptionPane.CLOSED_OPTION);
                    jTextField.setText("");
                    jPasswordField.setText("");
                    return;
                }

                /**
                 * 用户是否已登录...未实现..得改数据库.唉
                 */
//                for(String str:listusername){
//                    System.out.println(str+","+username);
//                    if(str==username){
//                        flag=true;
//                    }
//                }
//                if(flag){
//                    JOptionPane.showConfirmDialog(null, "该用户已经登录", "错误", JOptionPane.CLOSED_OPTION);
//                    jTextField.setText("");
//                    jPasswordField.setText("");
//                    return;
//                }

                Start start=new Start();
                start.setNowname(username);
//                listusername.add(username);
                setVisible(false);

            }
        });
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextField.setText("");
                jPasswordField.setText("");
            }
        });

        //设置布局
        //this.setLayout(new GridLayout(2,1));
        Box htemp1 = Box.createHorizontalBox();
        Box htemp2=Box.createHorizontalBox();
        Box htemp3=Box.createHorizontalBox();
        //jTextField.setMaximumSize(jTextField.getPreferredSize());

        htemp1.add(jLabel1);
        htemp1.add(Box.createHorizontalStrut(10));
        htemp1.add(jTextField);

        htemp2.add(jLabel2);
        htemp2.add(Box.createHorizontalStrut(22));
        htemp2.add(jPasswordField);

        htemp3.add(jb1);
        htemp3.add(Box.createHorizontalStrut(10));
        htemp3.add(jb2);
        htemp3.add(Box.createHorizontalStrut(10));
        htemp3.add(register);

        Box vtemp = Box.createVerticalBox();

        vtemp.add(htemp1);
        vtemp.add(Box.createVerticalStrut(40));
        vtemp.add(htemp2);
        vtemp.add(Box.createVerticalStrut(40));
        vtemp.add(htemp3);

        jp4.add(vtemp);
        this.add(jp4);

        //设置显示
        this.setSize(300, 200);
        this.setPreferredSize(new Dimension(300,200));
        this.setMaximumSize(new Dimension(300,200));
        this.setMaximumSize(new Dimension(300,200));
        //Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        //this.setLocation((int)(screenSize.getWidth()-300)/2,(int)(screenSize.getHeight()-200)/2);
        this.setVisible(true);
        this.setTitle("登陆");
        setResizable(false);

    }
    public static void main(String[] args){
        login l=new login();
    }
}
