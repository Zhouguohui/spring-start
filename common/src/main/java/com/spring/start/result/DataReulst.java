package com.spring.start.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by 50935 on 2019/9/9.
 */
@Data
public class DataReulst <T> implements Serializable {
    private boolean success;
    private String code;
    private String message;
    private Object data;

    public static <T> DataReulst<T> Fail(String msg) {
        return Fail("000", msg);
    }

    public static <T> DataReulst<T> Fail(String code, String msg) {
        return Fail(code, msg, new Object());
    }

    public static <T> DataReulst<T> Fail(String code, String msg, Object data) {
        DataReulst<T> tResultOK = new DataReulst();
        tResultOK.setSuccess(false);
        tResultOK.setCode(code);
        tResultOK.setMessage(msg);
        tResultOK.setData(data);
        return tResultOK;
    }

    public static <T> DataReulst<T> Success(String message, Object data) {
        DataReulst<T> tResultOK = new DataReulst();
        tResultOK.setSuccess(true);
        tResultOK.setCode("1");
        tResultOK.setMessage(message);
        if(Objects.nonNull(data)) {
            tResultOK.setData(data);
        }

        return tResultOK;
    }

    public static <T> DataReulst<T> Success(Object data) {
        return Success("1", data);
    }

    public static <T> DataReulst<T> Success(String message) {
        return Success(message, new Object());
    }


}