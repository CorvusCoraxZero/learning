package com.zero.aop.pointcut;

import com.zero.aop.annotation.MyAnnotation;
import org.springframework.stereotype.Component;

@Component("testPoint")
public class TestPoint {
    @MyAnnotation
    public String sayHello() {
        String name = "tom";
        System.out.println(name + "Hello!");
        return name;
    }
}
