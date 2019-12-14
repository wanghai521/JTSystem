package com.jt.config;

import com.jt.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther WangHai
 * @Date 2019/12/2
 * @Describle what
 */
@Configuration // 表示配置类
public class MvcConfigurer implements WebMvcConfigurer {
    @Autowired
    private UserInterceptor userInterceptor;

    /**
     * 开启匹配后缀配置
     *
     * @param configurer
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {

        configurer.setUseSuffixPatternMatch(true);
    }

    /**
     * 跳添加用户的拦截器
     * /** 表示拦截多级
     * /*  表示拦截一级
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/cart/**", "/order/**");
    }
}
