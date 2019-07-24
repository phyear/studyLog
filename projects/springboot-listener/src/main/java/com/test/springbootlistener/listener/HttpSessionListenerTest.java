package com.test.springbootlistener.listener;

import org.springframework.util.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
@WebListener
public class HttpSessionListenerTest implements HttpSessionListener {


    @Override
    public void sessionCreated(HttpSessionEvent se) {

        //获取session的上下文
        ServletContext servletContext = se.getSession().getServletContext();
        //判断count是否存在,不存在就创建
        if(StringUtils.isEmpty( servletContext.getAttribute("count"))){
            servletContext.setAttribute("count",0);
        }
        int count= (int) servletContext.getAttribute("count");
        //增加
        servletContext.setAttribute("count",count+1);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        ServletContext servletContext = se.getSession().getServletContext();
        if(StringUtils.isEmpty( servletContext.getAttribute("count"))){
            servletContext.setAttribute("count",0);
        }
        int count= (int) servletContext.getAttribute("count");
        if(count==0){ servletContext.setAttribute("count",0);}
        if(count>0){servletContext.setAttribute("count",count-1);}
    }
}
