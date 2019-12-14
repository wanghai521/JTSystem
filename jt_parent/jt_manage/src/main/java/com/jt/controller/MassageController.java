package com.jt.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther WangHai
 * @Date 2019/11/30
 * @Describle what
 */

@RestController
public class MassageController {
    @Value("${server.port}")
    private String port;

    @RequestMapping("/getPort")
    public String getPort(){

        return "当前服务器端口号："+port;
    }
}
