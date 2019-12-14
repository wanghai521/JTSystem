package com.jt.util;

import com.jt.pojo.User;

/**
 * 这是一个工具API，用于从一个线程中获取当前的用户信息
 * @Auther WangHai
 * @Date 2019/12/9
 * @Describle what
 */
public class UserThreadLocal {

    // 如果存储多个数据，则使用Map集合
    private static ThreadLocal<User> thread = new ThreadLocal<>();

    public static void setUser(User user){
        thread.set(user);
    }

    public static User getUser(){
        return thread.get();
    }

    // 防止内存溢出，必须添加移除方法
    public static void remove(){
        thread.remove();
    }

}
