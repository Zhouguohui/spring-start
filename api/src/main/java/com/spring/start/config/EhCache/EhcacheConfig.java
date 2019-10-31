package com.spring.start.config.EhCache;

import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by 50935 on 2019/10/30.
 */
@Configuration
public class EhcacheConfig {

    /**
     * ehcache 主要的管理器
     * @param bean
     * @return
     */
    @Bean
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean){
        return new EhCacheCacheManager(bean.getObject());
    }


    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
        EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();

        factoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        factoryBean.setShared(true);

        return factoryBean;
    }

}
