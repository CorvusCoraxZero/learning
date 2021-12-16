package com.example.nacoslearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nacos")
public class discoverController {
    @Autowired
    DiscoveryClient discoveryClient;

    @RequestMapping("register")
    public String register(){
        return discoveryClient.getOrder()+"";
    }
}
