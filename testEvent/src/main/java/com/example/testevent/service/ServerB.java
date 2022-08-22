package com.example.testevent.service;

import com.example.testevent.event.MyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

public class ServerB {
    @EventListener(MyEvent.class)
    public void handler(ApplicationEvent event) {
        System.out.println("B处理了ApplicationEvent事件" + event.toString());
    }

    @EventListener(ContextRefreshedEvent.class)
    public void handlerCRE(ApplicationEvent event) {
        System.out.println("B处理了ContextRefreshedEvent事件" + event.toString());
    }


}
