package com.spring.start.redis.impl;

import com.spring.start.enums.RedisTypeEnum;

/**
 * Created by 50935 on 2019/11/1.
 */
public class RedisUp  {

    public static RedisKvFactory upFactory(RedisTypeEnum redisTypeEnum){
        return new RedisKvFactory(redisTypeEnum.toString());
    }
}
