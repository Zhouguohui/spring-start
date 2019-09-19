package com.spring.start.config.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by 50935 on 2019/9/18.
 */
@Component
public class ZkConfiguration {

    /**
     *zk信息初始化配置
     */
    @Autowired
    private ZkProperties zkProperties;

    @Bean(initMethod = "start")
    public CuratorFramework curatorFramework() {
        /**
         * 重试次数和时间限制
         * baseSleepTimeMs 初始sleep的时间
         * maxRetries  最大重试次数
         * maxSleepMs  最大重试间隔时间
         */
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(zkProperties.getBaseSleepTimeMs(),zkProperties.getMaxRetries(),zkProperties.getMaxSleepMs());

        return CuratorFrameworkFactory.builder()
                .connectString(zkProperties.getConnectString())
                .sessionTimeoutMs(zkProperties.getSessionTimeoutMs())
                .connectionTimeoutMs(zkProperties.getConnectionTimeoutMs())
                .namespace(zkProperties.getNameSpace())
                .retryPolicy(retryPolicy).build();
    }
}
