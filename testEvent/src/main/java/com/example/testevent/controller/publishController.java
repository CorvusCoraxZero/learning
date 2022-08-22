package com.example.testevent.controller;

import com.example.testevent.service.ServerA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class publishController {
    @Autowired
    ServerA sa;

    @GetMapping("/publish")
    public String publish(){
        sa.publishOnRunning();
        return "发送成功";
    }


}
