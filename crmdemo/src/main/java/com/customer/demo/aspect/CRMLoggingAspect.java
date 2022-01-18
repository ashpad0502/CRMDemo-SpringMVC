package com.customer.demo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {
    
    private Logger myLogger = Logger.getLogger(getClass().getName());
    
    @Pointcut("execution (* com.customer.demo.controller.*.*(..))")
    private void forControllerPackage() {
    }

    @Pointcut("execution (* com.customer.demo.service.*.*(..))")
    private void forServicePackage() {
    }

    @Pointcut("execution (* com.customer.demo.dao.*.*(..))")
    private void forDaoPackage() {
    }

    @Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
    private void forAppFlow() {
    }

    @Before("forAppFlow()")
    public void before(JoinPoint theJoinPoint) {
        String method = theJoinPoint.getSignature().toShortString();
        myLogger.info("==> In @Before while calling method: "+method);

        // Display the arguments to the methods
        Object[] args = theJoinPoint.getArgs();

        for(Object arg : args) {
            myLogger.info("==> Argument: "+arg);
        }
    }

    @AfterReturning(pointcut = "forAppFlow()", returning = "result")
    public void afterReturning(JoinPoint theJoinPoint, Object result) {
        String method = theJoinPoint.getSignature().toShortString();
        myLogger.info("==> In @AfterReturning while calling method: "+method);

        // Display data returned
        myLogger.info("==> Result: "+result);
    }
}