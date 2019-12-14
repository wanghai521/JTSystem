package com.jt.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * HTTP协议客户端的配置类
 *
 * @Auther WangHai
 * @Date 2019/12/4
 * @Describle what
 */
@Configuration
@PropertySource("classpath:/properties/httpClient.properties")
public class HttpClientConfig {
    @Value("${http.maxTotal}")
    private Integer maxTotal; // 最大连接数

    @Value("${http.defaultMaxPerRoute}")
    private Integer defaultMaxPerRoute; // 并发数

    @Value("${http.connectTimeout}")
    private Integer connectTimeout; // 创建连接的最长时间

    @Value("${http.connectionRequestTimeout}")
    private Integer connectionRequestTimeout; // 从池中获取连接的最长时间

    @Value("${http.socketTimeout}")
    private Integer socketTimeout; // 数据传输的超时时间

    @Value("${http.staleConnectionCheckEnabled}")
    private Boolean staleConnectionCheckEnabled; // 提交请求前测试连接是否可用

    /**
     * 定义一个HttpClient连接池的管理器
     *
     * @return
     */
    @Bean(name = "httpClientConnectionManager") // 交给Spring管理
    public PoolingHttpClientConnectionManager getPoolingHttpClientConnectionManager() {
        // 先创建一个需要返回的对象
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();

        manager.setMaxTotal(maxTotal); // 设置最大的连接数
        manager.setDefaultMaxPerRoute(defaultMaxPerRoute); // 设置并发量

        return manager;
    }

    /**
     * 实例化连接处，设置链接池的管理器
     * 这里使用参数的形式进行注入上面实例化的连接处管理器 使用注解@Qualifier(name = "") 这个属性是Bean的name
     *
     * @return
     */
    @Bean(name = "httpClientBuilder")
    public HttpClientBuilder getHttpClientBuilder(@Qualifier("httpClientConnectionManager") PoolingHttpClientConnectionManager httpClientConnectionManager) {

        /*HttpClientBuilder中的构造方法是被protected修饰的，
         * 所以这里不能直接使用new来进行创建实例化HttpClientBuilder
         * 但是可以使用HttpClientBuilder提供的静态方法create()来获取对象*/
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // 将参数中的池管理器注入到builder中
        httpClientBuilder.setConnectionManager(httpClientConnectionManager);

        return httpClientBuilder;
    }

    /**
     * 注入连接池，用于获取httpClient
     *
     * @param httpClientBuilder
     * @return
     */
    @Bean
    public CloseableHttpClient getCloseableHttpclient(@Qualifier("httpClientBuilder") HttpClientBuilder httpClientBuilder) {

        CloseableHttpClient build = httpClientBuilder.build();

        return build;
    }

    /**
     * builder是reuqestConfig的一个内部类
     * 使用requestConfig的一个custom方式来获取一个Builder对象
     * 设置builder的连接信息
     *
     * @return
     */
    @Bean(name = "builder")
    public RequestConfig.Builder getBuilder() {

        // 获取一个内部类的实例对象，builder
        RequestConfig.Builder builder = RequestConfig.custom();

        builder.setConnectTimeout(connectTimeout) // 连接超时时间
                .setConnectionRequestTimeout(connectionRequestTimeout)  // 设置链接响应超时时间
                .setSocketTimeout(socketTimeout) // 设置传输的超时时间
                .setStaleConnectionCheckEnabled(staleConnectionCheckEnabled); // 在连接前，测试连接是否可用
        return builder;
    }

    /**
     * 使用builder创建一个requestConfig的对象
     *
     * @param builder
     * @return
     */
    @Bean
    public RequestConfig getRequestConfig(@Qualifier("builder") RequestConfig.Builder builder) {

        return builder.build();
    }
}
