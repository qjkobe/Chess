package com.shu.login.dao;

import com.shu.login.domain.Score;

/**
 * Created by Qjkobe on 2016/2/20.
 */
public interface IScoreDao {
    /**
     * 根据用户名来查找用户
     * @param userName
     * @return 查到到的分数
     */
    Score find(String userName);

    /**
     * 添加用户
     * @param score
     */
    void add(Score score);

    /**
     * 更新用户
     * @param score
     * @param isWin
     */
    void update(Score score,boolean isWin);
}
