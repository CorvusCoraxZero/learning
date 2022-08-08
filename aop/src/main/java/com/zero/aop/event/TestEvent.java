package com.zero.aop.event;

import org.springframework.context.ApplicationEvent;

public class TestEvent extends ApplicationEvent {
    private String message;

    public TestEvent(Object source) {
        super(source);
    }

    @Override
    public Object getSource() {
        return message;
    }
}
