package com.spring.start.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by 50935 on 2019/8/28.
 */
public class RedisTest {

    public static void main(String[] args) {
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.171.128", 7000));
        nodes.add(new HostAndPort("192.168.171.128", 7001));
        nodes.add(new HostAndPort("192.168.171.128", 7002));
        nodes.add(new HostAndPort("192.168.171.128", 7003));
        nodes.add(new HostAndPort("192.168.171.128", 7004));
        nodes.add(new HostAndPort("192.168.171.128", 6379));
        JedisCluster cluster = new JedisCluster(nodes);
        cluster.set("zgh","123");

        System.out.println(cluster.get("zgh"));

        cluster.close();
    }
}
