package com.zero.aop.aspect;

import com.zero.aop.annotation.MyAnnotation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TestAspect {

    /*
        任意公共方法的执行：execution(public * *(..))
        任何一个以“set”开始的方法的执行：execution(* set*(..))
        AccountService 接口的任意方法的执行：execution(* com.xyz.service.AccountService.*(..))
        定义在service包里的任意方法的执行： execution(* com.xyz.service.*.*(..))
        定义在service包和所有子包里的任意类的任意方法的执行：execution(* com.xyz.service..*.*(..))
        第一个表示匹配任意的方法返回值， …(两个点)表示零个或多个，第一个…表示service包及其子包,第二个表示所有类, 第三个*表示所有方法，第二个…表示方法的任意参数个数
        定义在pointcutexp包和所有子包里的JoinPointObjP2类的任意方法的执行：execution(*com.test.spring.aop.pointcutexp..JoinPointObjP2.*(..))")

        pointcutexp包里的任意类： within(com.test.spring.aop.pointcutexp.*)
        pointcutexp包和所有子包里的任意类：within(com.test.spring.aop.pointcutexp..*)
        实现了Intf接口的所有类,如果Intf不是接口,限定Intf单个类：this(com.test.spring.aop.pointcutexp.Intf)
        当一个实现了接口的类被AOP的时候,用getBean方法必须cast为接口类型,不能为该类的类型
        带有@Transactional标注的所有类的任意方法： @within(org.springframework.transaction.annotation.Transactional) @target(org.springframework.transaction.annotation.Transactional)
        带有@Transactional标注的任意方法：@annotation(org.springframework.transaction.annotation.Transactional)
        参数带有@Transactional标注的方法：@args(org.springframework.transaction.annotation.Transactional)
        参数为String类型(运行是决定)的方法：@args(String)

        @within和@target针对类的注解,
        @within会匹配到标注了指定注解的类，并且在该类的子类中，那些没有重写的父类方法也会被匹配到。
        @target只匹配标注了指定注解的类。不涉及任何其他类。
        @annotation是针对方法的注解
     */
    // @Around("execution(public String com.zero.aop.pointcut.TestPoint.sayHello())")
   @Around("@annotation(com.zero.aop.annotation.MyAnnotation)")
    public Object AroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("方法执行前-------- AroundAdvice");
        Object proceed = joinPoint.proceed();
        System.out.println("方法执行后-------- AroundAdvice");
        System.out.println(proceed);
        return null;
    }

    @Before("execution(public String com.zero.aop.pointcut.TestPoint.sayHello())")
    public Object BeforeAdvice() throws Throwable {
        System.out.println("方法执行前-------- BeforeAdvice");
        return null;
    }

    @After("execution(public String com.zero.aop.pointcut.TestPoint.sayHello())")
    public Object AfterAdvice() throws Throwable {
        System.out.println("方法执行后-------- AfterAdvice");
        return null;
    }
}
