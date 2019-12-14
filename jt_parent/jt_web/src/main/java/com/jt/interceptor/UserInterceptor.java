package com.jt.interceptor;

import com.jt.pojo.User;
import com.jt.util.CookieUtil;
import com.jt.util.IPUtil;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 *
 * @Auther WangHai
 * @Date 2019/12/9
 * @Describle what
 */
@Component //交给spring管理
public class UserInterceptor implements HandlerInterceptor {
    @Autowired
    private JedisCluster jedisCluster;

    /**
     * boolean：true表示放行 false表示拦截
     * <p>
     * yewusilu：
     * 1、动态的获取cookie、中的JT_TICKET的值
     * 2、获取用户的IP地址，检验数据
     * 3、查询redis中是否已经有数据
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1、获取cookie数据
        Cookie cookie = CookieUtil.getCookie(request, "JT_TICKET");
        if (cookie != null) {
            // 如果cookie、不为空
            String ticket = cookie.getValue();
            if (!StringUtils.isEmpty(ticket)) {
                // 检验redis缓存中是否存在该数据
                if (jedisCluster.exists(ticket)) {
                    // 校验IP地址是否匹配
                    String nowIP = IPUtil.getIpAddr(request);
                    String realIP = jedisCluster.hget(ticket, "JT_USER_IP");
                    if (realIP.equals(nowIP)) {
                        // 运行至此，表示都正确
                        String userJSON = jedisCluster.hget(ticket, "JT_USER");
                        User user = ObjectMapperUtil.jsonToObject(userJSON, User.class);
                        request.setAttribute("JT_USER", user);
                        // 利用ThreadLocal的方式动态获取数据
                        UserThreadLocal.setUser(user);
                        // 放行
                        return true;
                    }
                }
            }
        }
        // 重定向到登录页面
        response.sendRedirect("/user/login.html");
        return false;
    }

    /**
     * 再拦截器的最后一步实现数据的清空
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        UserThreadLocal.remove();

    }
}
