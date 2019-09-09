package com.spring.start.exception;

import lombok.Data;

/**
 * Created by 50935 on 2019/9/9.
 */
@Data
public class BaseException extends RuntimeException {
    private String code = "";

    public BaseException(String code, String msg) {
        super(msg);
        this.setCode(code);
    }
}