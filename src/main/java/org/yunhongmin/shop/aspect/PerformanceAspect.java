package org.yunhongmin.shop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class PerformanceAspect {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("@annotation(org.yunhongmin.shop.annotation.PerformanceLogging)")
    public Object logPerformance(ProceedingJoinPoint pjp) throws Throwable {
        long begin = System.currentTimeMillis();
        Object result = pjp.proceed();
        logger.info("execute time: " + (System.currentTimeMillis() - begin));
        return result;
    }
}
