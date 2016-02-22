package com.shu.JDBC;

import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Qjkobe on 2016/2/21.
 */
public class DbUtil {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    static{
        Properties prop=new Properties();
        Reader in= null;
        try {
            in = new FileReader("JDBCDemo\\src\\config.properties");
            prop.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver=prop.getProperty("driver");
        url=prop.getProperty("url");
        username=prop.getProperty("username");
        password = prop.getProperty("password");
    }
    public static Connection open(){
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void close(Connection conn){
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void close(PreparedStatement pstmt){
        if(pstmt!=null){
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
