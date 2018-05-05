package com.xiao.seckill.dao;

import com.xiao.seckill.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by unclexiao on 30/03/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:Spring/spring-dao.xml"})
public class SuccessKilledDaoTest {


    @Autowired
    private SuccessKilledDao successKilledDao;

    @Test
    public void testInsertSuccessKilled() throws Exception {
        long id = 1001L;
        long userPhone=18668067317L;
        int lines  = successKilledDao.insertSuccessKilled(id,userPhone);
        System.out.println("影响行数:"+lines);
    }

    @Test
    public void testQuerySuccessKilledWithId() throws Exception {
        long id = 1001L;
        long userPhone=18668067317L;
        SuccessKilled res  = successKilledDao.querySuccessKilledWithIdPhone(id,userPhone);

        System.out.println(res.toString());
        System.out.println(res.getSecKill().toString());

    }

}