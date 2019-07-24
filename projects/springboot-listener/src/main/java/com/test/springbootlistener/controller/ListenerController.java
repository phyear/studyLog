package com.test.springbootlistener.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class ListenerController {
    @RequestMapping("/test")
    public String test(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("name","aaa");
        int count=0;
        if(session.getServletContext().getAttribute("count")!=null){
            count= (int) session.getServletContext().getAttribute("count");
        }



        return "当前在线人数："+count;
    }
}
