package com.test.springbootlistener.listener;

import ch.qos.logback.classic.util.ContextInitializer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class TestListener implements ServletContextListener {
    private static  int count=0;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ServletContex初始化");

        System.out.println(sce.getServletContext().getServerInfo());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ServletContex销毁");

    }
}
