package com.jt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


//告知springBoot程序,启动时不要加载数据源配置.
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class WebSpringBootRun {
	
	public static void main(String[] args) {
		
		SpringApplication.run(WebSpringBootRun.class,args);
	}
}
