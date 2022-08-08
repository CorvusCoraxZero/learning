package com.zero.aop.service;

import com.zero.aop.event.TestEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ServiceC  {

    @EventListener(TestEvent.class)
    public void myEventHandler(TestEvent event) {
        // 处理事件的方法块
        System.out.println("接收到了MyEvent事件，开始处理...  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(event.toString());
        System.out.println("事件处理完毕！");
    }

    /*@Override
    public void onApplicationEvent(TestEvent event) {
        System.out.println("我是服务C 我处理了TestEvent事件");
    }*/
}
