package com.example.testevent.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {
    @Bean
    public ServerB ConfigServer(){
        ServerB serverB = new ServerB();
        return serverB;
    }
}
