package com.jt.service;

import com.jt.pojo.User;

/**
 * 使用dubbo框架，为单点登录所使用的用户信息业务接口
 */

public interface DubboUserService {

    /**
     * 实现用户信息的注册
     * @param user
     */
    void insertUser(User user);

    /**
     * 验证用户信息和IP地址
     * @param user
     * @param ip
     * @return
     */
    String findUserByUP(User user, String ip);

}
