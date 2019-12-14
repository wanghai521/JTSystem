package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户信息的业务处理层
 * @Auther WangHai
 * @Date 2019/12/7
 * @Describle what
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 用户信息的校验
     * @param param
     * @param type 1、userName 2、phone 3、email
     * @return
     */
    @Override
    public Boolean checkUser(String param, Integer type) {
        // 使用集合存储类型的参数和类型对应，在判断时会节省很多的判断时间
        Map<Integer,String> columMap = new HashMap<>();
        columMap.put(1, "username");
        columMap.put(2, "phone");
        columMap.put(3, "email");
        // 根据传参得到类型的字段名
        String colum = columMap.get(type);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 使用传参的数据和类型进行查询
        queryWrapper.eq(colum, param);
        User user = userMapper.selectOne(queryWrapper);

        return user==null?false:true;
    }
}
