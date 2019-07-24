package com.springbootvalid.controller;

import com.springbootvalid.pojo.UserInfo;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    @RequestMapping("/create")
    public UserInfo getUser(@Valid UserInfo userInfo){


        return userInfo;
    }
}
