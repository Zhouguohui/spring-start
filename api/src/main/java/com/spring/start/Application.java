package com.spring.start;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Created by 50935 on 2019/8/23.
 */
//@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@SpringBootApplication
//@EnableTransactionManagement
@MapperScan("com.spring.start.mapper")
@Slf4j
public class Application {
    public static void main(String[] args) {
        ApplicationContext ctx =  SpringApplication.run(Application.class, args);

        /*String[] beanNames = ctx.getBeanDefinitionNames();

        for(String s : beanNames){
            log.info(s);
        }*/
    }

}
