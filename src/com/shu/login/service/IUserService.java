package com.shu.login.service;

import com.shu.login.domain.Score;
import com.shu.login.domain.User;
import com.shu.login.exception.UserExistException;

/**
 * Created by Qjkobe on 2016/2/19.
 */
public interface IUserService {
    /**
     * 提供注册服务
     * @param user
     * @throws UserExistException
     */
    void registerUser(User user) throws UserExistException;

    /**
     * 提供登录服务
     * @param userName
     * @param userPwd
     * @return
     */
    User loginUser(String userName, String userPwd);

    /**
     * 提供添加分数服务
     * @param score
     */
    void registerScore(Score score);

    /**
     * 提供分数查询服务
     * @param userName
     * @return
     */
    Score queryScore(String userName);

    /**
     * 提供分数加减服务
     * @param userName
     * @return
     */
    void updateScore(Score score,boolean isWin);
}
