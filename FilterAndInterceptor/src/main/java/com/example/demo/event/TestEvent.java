package com.zero.aop.event;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ApplicationContextEvent;

import java.time.Clock;

public class TestEvent extends ApplicationEvent {
    public TestEvent(Object source) {
        super(source);
    }

    public TestEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
