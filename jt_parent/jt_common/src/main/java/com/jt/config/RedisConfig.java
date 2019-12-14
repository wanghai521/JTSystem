package com.jt.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @Auther WangHai
 * @Date 2019/12/1
 * @Describle what
 */
@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {
    //    @Value("${redis.host}")
//    private String host;
//    @Value("${redis.port}")
//    private Integer port;
    // redis的集群节点
    @Value("${redis.cluster.nodes}")
    private String nodes;

    @Bean // 交给spring管理
    @Scope("prototype") // 设置为多例，用户使用时创建
    public JedisCluster jedisCluster(@Qualifier("redisSet") Set<HostAndPort> redisSet) {
        return new JedisCluster(redisSet);
    }

    /**
     * redis集群的搭建
     *
     * @return
     */
    @Bean("redisSet")
    public Set<HostAndPort> redisSet() {
        Set<HostAndPort> redisSet = new HashSet<>();
        // 将字符串数组分割
        String[] nodeList = nodes.split(",");
        // 循环遍历，存入set集合
        for (String node : nodeList) {
            String[] hostAndPort = node.split(":");
            redisSet.add(new HostAndPort(hostAndPort[0],Integer.parseInt(hostAndPort[1])));
        }

        return redisSet;
    }

}
