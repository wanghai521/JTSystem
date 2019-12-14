package com.jt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther WangHai
 * @Date 2019/12/9
 * @Describle what
 */
@SpringBootApplication
@MapperScan("com.jt.mapper") //为mapper接口创建代理对象
public class OrderSpringBootRun {
    public static void main(String[] args) {

        SpringApplication.run(OrderSpringBootRun.class, args);
    }
}
