package com.newcoder.controller;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.Date;

/**
 * Created by Super腾 on 2017/2/8.
 */
@Controller
@Aspect
public class Ascept {

    private static final Logger logger = LoggerFactory.getLogger(Ascept.class);

    @Pointcut("execution(* com.newcoder.controller.*.*(..))")
    public void cut(){}

    @Before("cut()")
    public void beforeMethod(JoinPoint joinPoint){
        logger.info("参数"+joinPoint.getArgs());
        logger.info("进入时间"+new Date());
    }

    @After("cut()")
    public void afterMethod(){
        logger.info("返回时间"+new Date());
    }

    @Around("cut()")
    public String aroundMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        logger.info("around 前");
        String returnValue = (String) proceedingJoinPoint.proceed();
        logger.info("around 后");
        return returnValue;
    }

}
