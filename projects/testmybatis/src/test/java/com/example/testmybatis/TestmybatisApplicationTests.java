package com.example.testmybatis;

import com.example.testmybatis.mapper.FilmMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestmybatisApplicationTests {
    @Resource
    FilmMapper filmMapper;
    @Test
    public void contextLoads() {
        System.out.println(filmMapper.selectAll());
    }

}
