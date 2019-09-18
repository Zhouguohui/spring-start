package com.spring.start.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by 50935 on 2019/9/18.
 */
@Data
@Component
@ConfigurationProperties(prefix = "curator")
public class ZkProperties {

    /**
     * 初始sleep的时间
     */
    private int baseSleepTimeMs;

    /**
     * 最大重试次数
     */
    private int maxRetries;

    /**
     * 最大重试间隔时间
     */
    private int maxSleepMs;

    /**
     * 连接地址
     */
    private String connectString;

    /**
     * session超时时间
     */
    private int sessionTimeoutMs;

    /**
     * 连接超时时间
     */
    private int connectionTimeoutMs;

    /**
     * 命名空间
     */
    private String nameSpace;
}
