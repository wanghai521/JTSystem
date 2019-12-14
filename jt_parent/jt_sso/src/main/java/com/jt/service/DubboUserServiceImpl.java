package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.JedisCluster;

import java.util.Date;
import java.util.UUID;

/**
 * dubbo狂啊记得业务层实现类
 *
 * @Auther WangHai
 * @Date 2019/12/8
 * @Describle what
 */
@Service // 使用dubbo框架的注解
public class DubboUserServiceImpl implements DubboUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 用户信息的注册
     * 1、避免后台数据库报错，暂停使用手机代替
     * 2、使用MD5加密算法，
     * 3、设置操作时间
     * <p>
     * 注意：1、注册时使用加密算法，
     * 2、用户登录时，使用相同的
     *
     * @param user
     */
    @Override
    public void insertUser(User user) {

        // 将密码进行md5的加密
        String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setEmail(user.getPhone())
                .setPassword(md5Password)
                .setCreated(new Date())
                .setUpdated(user.getCreated());
        userMapper.insert(user);
    }

    /**
     * 验证用户信息和ip地址
     * 1、返回值数据，返回加密后的秘钥
     * 2、校验用户信息是否有效，如果无效就返回null
     * 3、如果用户的密码正确，那就把用户信息存进redis中
     *
     * @param user
     * @param ip
     * @return
     */
    @Override
    public String findUserByUP(User user, String ip) {

        // 1、将用户的密码相同的方式进行加密
        String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Password);
        // 2、根据用户的信息进行数据库的查询
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        User userDB = userMapper.selectOne(queryWrapper);
        // 判断查询的信息是否为空
        if (userDB == null) {
            return null;
        }

        // 运行至此，说明用户信息核对正确
        // 3、生成动态的ticket
        String uuid = UUID.randomUUID().toString();
        String ticket = DigestUtils.md5DigestAsHex(uuid.getBytes());

        // 4、为了使得数据安全性，一般将重要的数据进行脱敏处理
        // 将user对象转换成json
        userDB.setPassword(md5Password);
        String userJSON = ObjectMapperUtil.objectToJson(userDB);

        // 6、防止用户重复登录,将之前的用户信息删除
        if (jedisCluster.exists("JT_USERNAME"+userDB.getUsername())){
            String oldTicket = jedisCluster.get("JT_USERNAME" + userDB.getUsername());
            // 删除
            jedisCluster.del(oldTicket);
        }

        // 5、将数据保存在redis中
        jedisCluster.hset(ticket, "JT_USER", userJSON);
        jedisCluster.hset(ticket, "JT_USER_IP", ip);
        jedisCluster.expire(ticket, 3600);// 设置超时时间

        // 将用户信息和ticket绑定
        jedisCluster.setex("JT_USERNAME"+userDB.getUsername(), 24*3600, ticket);

        return ticket;
    }
}
