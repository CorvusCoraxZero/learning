package com.zero.aop;

import com.zero.aop.annotation.MyAnnotation;
import com.zero.aop.pointcut.TestPoint;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;

@SpringBootTest
class AopApplicationTests {

    @Resource
    ApplicationContext context;

    @Test
    @MyAnnotation
    void contextLoads() {
        TestPoint bean = (TestPoint)context.getBean("testPoint");
        bean.sayHello();
    }

}
