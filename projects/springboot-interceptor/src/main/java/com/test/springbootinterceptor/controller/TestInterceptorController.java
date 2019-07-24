package com.test.springbootinterceptor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestInterceptorController {

    @GetMapping("/test")
    public String test(){
        return "aaa";
    }


    @GetMapping("/login")
    public String login(){
        return "登录来了";
    }
}
