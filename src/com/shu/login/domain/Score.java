package com.shu.login.domain;

import java.io.Serializable;

/**
 * Created by Qjkobe on 2016/2/20.
 */
public class Score implements Serializable{
    // 用户名
    private String userName;
    //分数
    private String score;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }


}
