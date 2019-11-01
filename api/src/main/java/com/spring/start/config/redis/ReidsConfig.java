package com.spring.start.config.redis;

/**
 * Created by 50935 on 2019/9/19.
 */

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Data
@Component
public class ReidsConfig {

    @Value("${spring.redis.cluster.nodes}")
    private String redisServer;

    @Value("${spring.redis.timeout}")
    private int timeOut;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.lettuce.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.lettuce.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.lettuce.pool.max-wait}")
    private long maxWait;

    @Value("${spring.redis.cluster.max-redirects}")
    private int maxRedirects;

    @Bean
    public JedisCluster getJedisCluster() {
        String[] serverArray = redisServer.split(",");
        Set<HostAndPort> nodes = new HashSet();
        Arrays.asList(serverArray).stream().forEach(s->{
            String[] ipPortPair = s.split(":");
            nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
        });
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        jedisPoolConfig.setTestOnBorrow(true);
        return new JedisCluster(nodes, timeOut, timeOut, maxRedirects, password, jedisPoolConfig);
    }

}
