package com.example.testmybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "com.example.testmybatis.mapper")
public class TestmybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestmybatisApplication.class, args);
    }

}
