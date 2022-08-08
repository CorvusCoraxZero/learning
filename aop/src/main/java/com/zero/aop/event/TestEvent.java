package com.zero.aop.event;

import org.springframework.context.ApplicationEvent;


import java.time.Clock;

public class TestEvent extends ApplicationEvent {
    public TestEvent(Object source) {
        super(source);
    }

    public TestEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
