package com.shu.login.dao.impl;

import com.shu.chess.HandingMsg;
import com.shu.login.dao.IScoreDao;
import com.shu.login.domain.Score;
import com.shu.login.util.XmlUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.io.IOException;

/**
 * Created by Qjkobe on 2016/2/20.
 */
public class ScoreDaoImpl implements IScoreDao {
    @Override
    public Score find(String userName) {
        try {
            Document document = XmlUtils.getDocument();
            Element e = (Element) document.selectSingleNode("//score[@userName='" + userName + "']");
            if (e == null) {
                return null;
            }
            Score score = new Score();
            score.setUserName(e.attributeValue("userName"));
            score.setScore(e.attributeValue("score"));

            return score;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void update(Score score, boolean isWin) {
        try {
            Document document = XmlUtils.getDocument();
            String userName = score.getUserName();
            int s = Integer.parseInt(score.getScore());
            String splus = String.valueOf(s + 1);
            String sminus = String.valueOf(s - 1);
            Element e = (Element) document.selectSingleNode("//score[@userName='" + userName + "']");

            if (isWin == true) {
                e.setAttributeValue("score", splus);
            } else {
                e.setAttributeValue("score", sminus);
            }

            XmlUtils.write2Xml(document);


        } catch (DocumentException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    //@SuppressWarnings("deprecation")
    @Override
    public void add(Score score) {
        try {
            Document document = XmlUtils.getDocument();
            Element root = document.getRootElement();
            Element score_node = root.addElement("score");  //创建score结点，并挂到root
            score_node.addAttribute("userName", score.getUserName());
            score_node.addAttribute("score", score.getScore());

            XmlUtils.write2Xml(document);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
