package com.spring.start.exception;


import com.spring.start.result.DataReulst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Set;

/**
 * 请求错误解析
 */
@RestControllerAdvice
@Component
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public DataReulst handleBindException(BindException ex) {
        // ex.getFieldError():随机返回一个对象属性的异常信息。如果要一次性返回所有对象属性异常信息，则调用ex.getAllErrors()
        FieldError fieldError = ex.getFieldError();
        StringBuilder sb = new StringBuilder();
        sb.append(fieldError.getField()).append("=[").append(fieldError.getRejectedValue()).append("]")
                .append(fieldError.getDefaultMessage());
        return DataReulst.Fail("Bad_request-error", sb.toString());
    }


    /**
     * 通用异常的处理 400 数据校验
     * @param request
     * @param ex
     * @return
     */
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public DataReulst handleValidationException(HttpServletRequest request, ConstraintViolationException ex) {
        log.error("异常:"+request.getRequestURI(),ex);
        Set<ConstraintViolation<?>> errors = ex.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : errors) {
            strBuilder.append(violation.getMessage() + "\n");
        }
        return DataReulst.Fail("Bad_request-error", strBuilder.toString());
    }


    /**
     * 通用异常的处理 400
     * @param request
     * @param exception
     * @return
     * @throws Exception
     *
     */
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value=MethodArgumentNotValidException.class)
    @ResponseBody
    public DataReulst MethodArgumentNotValidHandler(HttpServletRequest request,
                                                    MethodArgumentNotValidException exception) throws Exception
    {
        log.error("异常:"+request.getRequestURI(),exception);
        HashMap<String,String> errortip=new HashMap<>();
        //解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errortip.put(error.getField(),error.getDefaultMessage());
        }

        return DataReulst.Fail("Argument-error","请求参数错误",errortip);
    }

    /**
     *通用异常的处理 404
     * @param request
     * @param ex
     * @return
     */
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public DataReulst NoHandlerFoundException(HttpServletRequest request, Exception ex) {
        log.error("异常:"+request.getRequestURI(),ex);
        return DataReulst.Fail("NoHandlerFound-error","找不到接口地址："+request.getRequestURI().toString());
    }


    /**
     * 通用异常的处理，返回500
     * @param request
     * @param ex
     * @return
     */
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public DataReulst handleException(HttpServletRequest request, Exception ex) {
        log.error("异常:"+request.getRequestURI(),ex);
        if(ex instanceof BindException){
            BindException bindException = (BindException)ex;
            FieldError fieldError = bindException.getFieldError();
            StringBuilder sb = new StringBuilder();
            sb.append(fieldError.getField()).append("=[").append(fieldError.getRejectedValue()).append("]")
                    .append(fieldError.getDefaultMessage());
            // 生成返回结果
            return DataReulst.Fail("valid-error", sb.toString());
        }
        if(ex instanceof BaseException){
            BaseException baseex=(BaseException)ex;
            return DataReulst.Fail(baseex.getCode(),baseex.getMessage());
        }
        if(ex instanceof  org.springframework.web.multipart.MultipartException){
            return DataReulst.Fail("0000","文件太大");
        }
        if(ex instanceof com.alibaba.fastjson.JSONException){
            return DataReulst.Fail("server-error","请求参数格式不正确");
        }
        if(ex instanceof org.springframework.web.HttpRequestMethodNotSupportedException){
            return DataReulst.Fail("server-error","请求方法不正确");
        }else {
            return DataReulst.Fail("server-error", "请求不正确，服务器处理异常");
        }

    }

}