package com.zero.aop.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public ServiceB getServiceB(){
        ServiceB sb = new ServiceB();
        return sb;
    }
}
