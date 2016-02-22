package com.shu.JDBC;

import com.shu.login.util.XmlUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Qjkobe on 2016/2/21.
 */
public class Main {
    public static void main(String[] args) {
        Main.userToDatabase();
        Main.scoreToDatabase();
    }

    public static void userToDatabase() {
        String sql = "insert IGNORE into user values(?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DbUtil.open();
            pstmt = conn.prepareStatement(sql);

            Document document = XmlUtils.getDocument();
            List itemList = document.selectNodes("//user");
            for (Iterator iter = itemList.iterator(); iter.hasNext(); ) {
                Element element = (Element) iter.next();

                String id = element.attributeValue("id");
                String userName = element.attributeValue("userName");
                String userPwd = element.attributeValue("userPwd");
                String email = element.attributeValue("email");
                String birthday = element.attributeValue("birthday");

                pstmt.setString(1, id);
                pstmt.setString(2, userName);
                pstmt.setString(3, userPwd);
                pstmt.setString(4, email);
                pstmt.setString(5, birthday);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("user导入数据库成功");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(conn);
            DbUtil.close(pstmt);
        }
    }

    public static void scoreToDatabase() {
        String sql = "insert IGNORE into score values(?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DbUtil.open();
            pstmt = conn.prepareStatement(sql);

            Document document = XmlUtils.getDocument();
            List itemList = document.selectNodes("//score");
            for (Iterator iter = itemList.iterator(); iter.hasNext(); ) {
                Element element = (Element) iter.next();

                String userName = element.attributeValue("userName");
                int score = Integer.parseInt(element.attributeValue("score"));

                pstmt.setString(1, userName);
                pstmt.setInt(2, score);

                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("score导入数据库成功");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(conn);
            DbUtil.close(pstmt);
        }
    }
}
