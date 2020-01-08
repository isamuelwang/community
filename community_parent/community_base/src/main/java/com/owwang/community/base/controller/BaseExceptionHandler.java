package com.owwang.community.base.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Classname BaseExceptionHandler
 * @Description TODO
 * @Date 2020-01-06
 * @Created by WANG
 */
@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result excption(Exception e){
        return new Result(false, StatusCode.ERROR,e.getMessage());
    }
}
