package com.spring.start.cache.impl;

import com.spring.start.cache.RootEhcache;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Created by 50935 on 2019/10/30.
 */
@Slf4j
@Component
public class TopConfig extends RootEhcache<String, String> implements ApplicationRunner {

    @Synchronized
    @Override
    public void refresh() throws Exception{
        log.info("加载TopConfig资源配置文件-------开始");
        Properties properties = PropertiesLoaderUtils.loadAllProperties("config/config.properties");
        properties.entrySet().stream().forEach(s->this.inElement(String.valueOf(s.getKey()),String.valueOf(s.getValue())));
        log.info("加载TopConfig资源配置文件-------结束");
    }

    @Override
    public String upOne(String s) {
        return null;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        log.info("*****项目启动刷新TopConfig的ehcache*****");
        refresh();
    }
}
