package com.jt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
 * mybatis的分页拦截器,提高mybatisplus的的分页效率
 * @author 赵丹
 *
 */
@Configuration
public class MybatisConfig {
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		
		return new PaginationInterceptor();
	}
}
