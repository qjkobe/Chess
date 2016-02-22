package com.shu.login.dao;

import com.shu.login.domain.User;

/**
 * Created by Qjkobe on 2016/2/19.
 */
public interface IUserDao {
    /**
     * 根据用户名和密码来查找用户
     * @param userName
     * @param userPwd
     * @return 查到到的用户
     */
    User find(String userName, String userPwd);

    /**
     * 添加用户
     * @param user
     */
    void add(User user);

    /**根据用户名来查找用户
     * @param userName
     * @return 查到到的用户
     */
    User find(String userName);
}
