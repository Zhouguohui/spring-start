package com.spring.start.redis.impl;

import com.spring.start.redis.RedisKvCall;
import com.spring.start.spring.SpringUtils;
import redis.clients.jedis.JedisCluster;

/**
 * Created by 50935 on 2019/11/1.
 */
public class RedisKvFactory implements RedisKvCall {

    private static final JedisCluster jedisCluster = SpringUtils.getBean(JedisCluster.class);

    private String baseKey = "";

    public RedisKvFactory(String sKey) {
        baseKey = sKey;
    }

    @Override
    public String hget(final String key, final String field) {
        return jedisCluster.hget(baseKey + key, field);
    }

    @Override
    public Long hdel(String key, String field) {
        return jedisCluster.hdel(baseKey + key, field);
    }

    @Override
    public Long hset(String key, String field, String value) {

        return jedisCluster.hset(baseKey + key, field, value);
    }

    @Override
    public String setex(String key, int seconds, String value) {
        return jedisCluster.setex(baseKey + key, seconds, value);
    }

    @Override
    public String set(String key, String value) {
        return jedisCluster.set(baseKey + key, value);
    }

    @Override
    public String get(String key) {
        return jedisCluster.get(baseKey + key);
    }

    @Override
    public Long incrBy(String key, long integer) {
        return jedisCluster.incrBy(baseKey + key, integer);
    }

    @Override
    public Long setnx(String key, String value) {

        return jedisCluster.setnx(baseKey + key, value);
    }

    @Override
    public Long del(String key) {
        return jedisCluster.del(baseKey + key);
    }

    @Override
    public Boolean exists(String key) {
        return jedisCluster.exists(baseKey + key);
    }

    @Override
    public Long hincrBy(String key, String field, long value) {

        return jedisCluster.hincrBy(baseKey + key, field, value);
    }

    @Override
    public Long expire(String key, int seconds) {
        return jedisCluster.expire(baseKey + key, seconds);
    }

    @Override
    public Long ttl(String key) {
        return jedisCluster.ttl(baseKey + key);
    }
}
