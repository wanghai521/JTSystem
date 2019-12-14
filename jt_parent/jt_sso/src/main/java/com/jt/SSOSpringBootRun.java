package com.jt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther WangHai
 * @Date 2019/12/7
 * @Describle what
 */
@SpringBootApplication
@MapperScan("com.jt.mapper")    //导入mapper接口文件
public class SSOSpringBootRun {

    public static void main(String[] args) {

        SpringApplication.run(SSOSpringBootRun.class, args);
    }
}
