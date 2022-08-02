package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping()
public class TestController {

    @GetMapping("/test/{value}")
    @ResponseBody
    public String test(@PathVariable String value) {
        System.out.println("我是controller test" + value);
        return value;
    }

    @GetMapping("/testxx")
    @ResponseBody
    public String testxx(@RequestParam String value) {
        System.out.println("我是controller testxx " + value);
        return value;
    }
}


