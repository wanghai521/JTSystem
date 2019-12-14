package com.jt.exe;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.vo.SysResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther WangHai
 * @Date 2019/11/11
 * @Describle 全局异常处理机制
 */

@RestControllerAdvice // 异常通知，只对controller层生效
@Slf4j  // 用于纪录日志
public class SysException {

    // 当系统出现异常时，给予自己定义的异常抛出
    // 区分系统正常的异常和跨域异常
    // 跨域的异常，一定会有添加callback的参数
    @ExceptionHandler(RuntimeException.class)
    public Object error(Exception e, HttpServletRequest request) {
        String callback = request.getParameter("callback");
        // 判断是否有该参数
        if (StringUtils.isEmpty(callback)) {
            // 用户不是跨域请求
            e.printStackTrace();
            log.error(e.getMessage());
            return SysResult.fail();
        } else {
            // 用户是跨域请求
            e.printStackTrace();
            log.error(e.getMessage());
            return new JSONPObject(callback, SysResult.fail());
        }
    }
}
