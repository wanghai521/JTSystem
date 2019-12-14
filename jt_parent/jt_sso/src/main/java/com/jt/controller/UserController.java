package com.jt.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.service.UserService;
import com.jt.util.CookieUtil;
import com.jt.util.IPUtil;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 单点登录的控制层
 *
 * @Auther WangHai
 * @Date 2019/12/7
 * @Describle what
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 对用户信息的校验
     * 其中第一个参数是检验信息的传参
     * 第二个参数是校验类型的区别，1、用户名 2、手机号 3、邮箱
     *
     * @return
     */
    @RequestMapping("/check/{param}/{type}")
    public JSONPObject checkUser(@PathVariable String param, @PathVariable Integer type, String callback) {
        // 校验用户信息
        Boolean result = userService.checkUser(param, type);

        SysResult sysResult = SysResult.success(result);

        return new JSONPObject(callback, sysResult);
    }

    /**
     * 用户信息的回显
     *
     * @return
     */
    @RequestMapping("/query/{ticket}")
    @ResponseBody
    public JSONPObject findByTicket(@PathVariable String ticket,
                                    String callback,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        JSONPObject jsonpObject;
        // 先判断传过来的ticket是否为空
        if (StringUtils.isEmpty(ticket)) {
            // ticket为空，直接返回错误
            jsonpObject = new JSONPObject(callback, SysResult.fail());
            return jsonpObject;
        }

        // 运行至此，表示ticket没有问题
        // 2、判断当前的key是否存在
        if (!jedisCluster.exists(ticket)) {
            // 缓存中不存在这个key，可能是盗用其他人的cookie
            // 如果ticket不为null的时候，但是redis中的数据不正确，说明cookie有错误，应该删除cookie
            CookieUtil.deleteCookie(response, "JT_TICKET", 0, "jt.com", "/");
            jsonpObject = new JSONPObject(callback, SysResult.fail());
            return jsonpObject;
        }

        // 运行至此，说明缓存中存在这个key
        // 3、判断ip地址是否匹配
        String nowIP = IPUtil.getIpAddr(request);
        String realIP = jedisCluster.hget(ticket, "JT_USER_IP");
        if (!nowIP.equals(realIP)) {
            // IP地址不匹配，返回错误
            jsonpObject = new JSONPObject(callback, SysResult.fail());
            return jsonpObject;
        }

        // 运行至此，说明IP地址也匹配
        // 4、返回用户信息数据
        String userJSON = jedisCluster.hget(ticket, "JT_USER");
        jsonpObject = new JSONPObject(callback, SysResult.success(userJSON));
        return jsonpObject;
    }
}
