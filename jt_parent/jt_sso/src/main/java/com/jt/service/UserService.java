package com.jt.service;

/**
 * 用户信息的业务层接口
 */
public interface UserService {

    /**
     * 用户信息的校验
     * @param param
     * @param type 1、userName 2、phone 3、email
     * @return
     */
    Boolean checkUser(String param, Integer type);
}
