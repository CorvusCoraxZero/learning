package com.zero.aop.service;

import com.zero.aop.event.TestEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ServiceA {
    @Value("${content.message}")
    private String message;

    @Autowired
    private ApplicationContext applicationContext;


    public void saySomeThing() {
        System.out.println("我是A服务" + message);
        applicationContext.publishEvent(new TestEvent(this));
        applicationContext.publishEvent(new ContextRefreshedEvent(applicationContext));
    }

    @EventListener(ContextRefreshedEvent.class)
    public void handleEvent(ContextRefreshedEvent event){
        System.out.println("服务A处理事件"+event.getSource());
    }

}
