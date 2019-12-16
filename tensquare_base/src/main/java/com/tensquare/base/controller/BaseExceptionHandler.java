package com.tensquare.base.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理类
 */
@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler
    public Result error(Exception e){
        e.printStackTrace();//打印出异常信息
        System.out.println(e.getMessage());
        return  new Result(false, StatusCode.ERROR, "执行出错");
    }

}
