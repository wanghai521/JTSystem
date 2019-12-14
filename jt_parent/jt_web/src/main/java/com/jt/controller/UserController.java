package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.util.CookieUtil;
import com.jt.util.IPUtil;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther WangHai
 * @Date 2019/12/7
 * @Describle what
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Reference(check = false) // 使用dubbo框架的注解
    private DubboUserService userService;
    @Autowired
    private JedisCluster jedisCluster;


    @RequestMapping("/{moduleName}")
    public String moduleName(@PathVariable String moduleName) {

        return moduleName;
    }

    /**
     * 实现信息注册
     *
     * @return
     */
    @RequestMapping("/doRegister")
    @ResponseBody
    public SysResult doRegister(User user) {

        userService.insertUser(user);
        return SysResult.success();
    }

    /**
     * 实现用户单点登录操作
     * 1、动态获取IP地址
     * 2、将ticket信息发送到cookie中
     * cookie的用法
     * 关于path的说明：
     * 1、url:www.jd.com/login.html
     * ticketCookie.setPath("/");
     * ticketCookie.setPath("/abc"); url无法访问该cookie
     *
     * @return
     */
    @RequestMapping("/doLogin")
    @ResponseBody
    public SysResult doLogin(User user, HttpServletRequest request,
                             HttpServletResponse response) {

        // 1、动态的获取用户的ip地址
        String ip = IPUtil.getIpAddr(request);
        // 2、获取校验结果
        String ticket = userService.findUserByUP(user, ip);
        if (StringUtils.isEmpty(ticket)) {
            // 表示用户名和密码错误
            return SysResult.fail();
        }

        // 运行到这，说明信息验证正确
        Cookie ticketCookie = new Cookie("JT_TICKET", ticket);
        ticketCookie.setMaxAge(7 * 24 * 3600);
        ticketCookie.setPath("/"); // 根目录有效
        // 由于是单点登录，需要讲cookie信息设置为共享数据
        ticketCookie.setDomain("jt.com");
        response.addCookie(ticketCookie);

        return SysResult.success();
    }

    /**
     * 用登录的退出
     * 1、删除redis中的数据 ticket
     * 2、删除cookie  “JT_TICKET”
     *
     * 实现思路
     * 1、现货区“JT_TICKET”名称的cookie值
     * 2、删除redis数据
     * 3、删除cookie纪录
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){

        // 1、获取cookie信息
        Cookie cookie = CookieUtil.getCookie(request, "JT_TICKET");

        // 判断获取的cookie是否为空
        if (cookie == null){
            // 重定向到系统首页
            return "redirect:/";
        }

        // 2、删除redsi数据
        String ticket = cookie.getValue();
        jedisCluster.del(ticket);

        /*3、规则：
        * 定义一个和之前一样的cookie，名称和配置都一直
        * 之后进行删除
        * */
        CookieUtil.deleteCookie(response, "JT_TICKET", 0, "jt.com", "/");

        return "redirect:/";
    }


}
