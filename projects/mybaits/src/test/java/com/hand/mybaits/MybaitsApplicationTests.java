package com.hand.mybaits;

import com.hand.mybaits.mapper.StockMapper;
import com.hand.mybaits.pojo.Stock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybaitsApplicationTests {

    private  static Logger logger=LoggerFactory.getLogger(MybaitsApplication.class);
    @Resource
    StockMapper stockMapper;
    @Test
    public void contextLoads() {

        System.out.println(stockMapper.selectAll());
//        Stock stock=new Stock();
//        stock.setStockId("asdasfa");
//        stock.setCompanyName("sadadasdaf");
//        stock.setShareCoding("qweqe");
//        stock.setStockName("asfaf");
//        stock.setStatus(2);
//
//        System.out.println(stockMapper.insert(stock));
    }

}
