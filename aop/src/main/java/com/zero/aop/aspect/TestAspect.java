package com.zero.aop.aspect;

import com.zero.aop.annotation.MyAnnotation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TestAspect {

    @Around("execution(public String com.zero.aop.pointcut.TestPoint.sayHello())")
//    @Around("@annotation(com.zero.aop.annotation.MyAnnotation)")
    public Object AroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("方法执行前-------");
        Object proceed = joinPoint.proceed();
        System.out.println("方法执行后--------");
        System.out.println(proceed);
        return null;
    }
}
