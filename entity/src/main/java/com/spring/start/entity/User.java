package com.spring.start.entity;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Created by 50935 on 2019/9/9.
 */
@Data
public class User {

    private Integer id;

    @NotEmpty(message = "不能为空")
    private String userName;

    private String mobile;

    private String password;

    private Integer delFlag;
}
