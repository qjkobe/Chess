package com.shu.login.service.impl;

import com.shu.login.dao.IScoreDao;
import com.shu.login.dao.impl.ScoreDaoImpl;
import com.shu.login.domain.Score;
import com.shu.login.service.IUserService;
import com.shu.login.dao.IUserDao;
import com.shu.login.dao.impl.UserDaoImpl;
import com.shu.login.domain.User;
import com.shu.login.exception.UserExistException;

/**
 * Created by Qjkobe on 2016/2/19.
 */
public class UserServiceImpl implements IUserService {
    private IUserDao userDao = new UserDaoImpl();
    private IScoreDao scoreDao=new ScoreDaoImpl();

    @Override
    public void registerUser(User user) throws UserExistException {
        if (userDao.find(user.getUserName()) != null) {
            //checked exception
            //unchecked exception
            //上一层程序处理这个异常，以给用户一个友好提示
            throw new UserExistException("注册的用户名已存在！！！");
        }
        userDao.add(user);
    }

    @Override
    public User loginUser(String userName, String userPwd) {
        return userDao.find(userName, userPwd);
    }

    @Override
    public void registerScore(Score score) {
        scoreDao.add(score);
    }

    @Override
    public Score queryScore(String userName) {
        return scoreDao.find(userName);
    }

    @Override
    public void updateScore(Score score, boolean isWin) {
        scoreDao.update(score, isWin);
    }
}
