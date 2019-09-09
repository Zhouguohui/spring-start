package com.spring.start;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by 50935 on 2019/8/23.
 */
@SpringBootApplication
@MapperScan("com.spring.start.mapper")
public class Application {
    public static void main(String[] args) {
            SpringApplication.run(Application.class, args);
    }

}
