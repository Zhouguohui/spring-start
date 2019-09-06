package com.spring.start.redis;

import lombok.Data;

import java.util.Date;

/**
 * Created by 50935 on 2019/8/29.
 */
@Data
public class User {

    private String userName;

    private Integer age;

    private Date createTime;

    private String remark;


}
