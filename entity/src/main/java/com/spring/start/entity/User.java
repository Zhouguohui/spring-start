package com.spring.start.entity;

import lombok.Data;

/**
 * Created by 50935 on 2019/9/9.
 */
@Data
public class User {
    private Integer id;

    private String userName;

    private String mobile;

    private String password;

    private Integer delFlag;
}
