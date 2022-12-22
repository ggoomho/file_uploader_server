package com.chols.fu.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LogAspect {
    @Pointcut("@annotation(com.chols.fu.aop.annotation.ServiceLog)")
    private void service(){}

    @Around("service()")
    public Object serviceLog(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = pjp.proceed();
        long endTime = System.currentTimeMillis();

        log.info("[SERVICE]");
        log.info("   Class: " + pjp.getSignature().getDeclaringTypeName());
        log.info("   Method: " + pjp.getSignature().getName());
        log.info("   Execution Time: " + (endTime - startTime) + "ms");

        return result;
    }
}

