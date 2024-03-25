package com.nimbleways.springboilerplate.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;


//using AOP for logging perposes , to track the lifecycle of handling a product by the handlers
@Aspect
@Component
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("@annotation(com.nimbleways.springboilerplate.annotation.Loggable)")
    public void applicationPackagePointcut() {}

    @Before("applicationPackagePointcut()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Enter: {}.{}() ", joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "applicationPackagePointcut()")
    public void logAfterReturning(JoinPoint joinPoint) {
        log.info("Exit: {}.{}() ", joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName());
    }

}
