package com.example.testevent.service;

import com.example.testevent.event.MyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ServerA {
    @Autowired
    ApplicationContext context;

    public void publishOnRunning() {
        System.out.println("A在初始阶段发送了事件");
        context.publishEvent(new MyEvent(this));
    }

    @PostConstruct
    public void publishOnInit() {
        System.out.println("A在初始阶段发送了事件");
        context.publishEvent(new MyEvent(this));
        context.publishEvent(new ContextRefreshedEvent(context));
    }
}
