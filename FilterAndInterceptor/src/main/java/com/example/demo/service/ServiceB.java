package com.zero.aop.service;

import com.zero.aop.event.TestEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import javax.annotation.PostConstruct;

public class ServiceB {
    @Value("${content.message}")
    private String message;

    @Autowired
    private ServiceA sa;

    public void saySomeThing(){
        System.out.println("我是服务B"+message);
    }

    @PostConstruct
    public void init(){
        System.out.println("我是服务B,我想让A说些什么" + message);
        sa.saySomeThing();
    }

    @EventListener(ContextRefreshedEvent.class)
    public void handleEvent(ContextRefreshedEvent event){
        System.out.println("服务B处理事件"+event.getSource());
    }
}
