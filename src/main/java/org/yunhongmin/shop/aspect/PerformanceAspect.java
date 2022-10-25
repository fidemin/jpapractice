package org.yunhongmin.shop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {
    @Around("@annotation(org.yunhongmin.shop.annotation.PerformanceLogging)")
    public Object logPerformance(ProceedingJoinPoint pjp) throws Throwable {
        long begin = System.currentTimeMillis();
        Object result = pjp.proceed();
        System.out.println("execute time: " + (System.currentTimeMillis() - begin));
        return result;
    }
}
