package com.xiao.seckill.dao;

import com.xiao.seckill.entity.SecKill;
import org.apache.commons.collections.MapUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by unclexiao on 29/03/2018.
 * <p>
 * 配置Spring 和junit整合,为了junit启动时加载SpringIOC容器
 */
//Junit启动加载SpringIOC容器
@RunWith(SpringJUnit4ClassRunner.class)

//告诉Junit Spring的配置文件位置
@ContextConfiguration({"classpath:Spring/spring-dao.xml"})
public class SecKillDaoTest {

//    注入SecKillDao实现类

    @Resource
    private SecKillDao secKillDao;


    @Test
    public void testQueryById() throws Exception {
        long id = 1000;
        SecKill secKill = secKillDao.queryById(id);
        System.out.println(secKill.getName());
        System.out.println(secKill.toString());
    }

    @Test
    public void testQueryAll() throws Exception {

        List<SecKill> secKills = secKillDao.queryAll(1,100);
        for(SecKill s : secKills){
            System.out.println(s.toString());
        }
    }


    @Test
    public void testReduceNumber() throws Exception {
        int lines = secKillDao.reduceNumber(1000L,new Date());
        System.out.println(lines);
    }



    @Test
    public void testExecutionByProcedure(){
        Date now = new Date();

        Map<String,Object> params = new HashMap<String, Object>();
        params.put("seckill_id",1005L);
        params.put("user_phone",13874767082L);
        params.put("kill_time",now.getTime());
        params.put("result",null);

        secKillDao.killByProcedure(params);

        int res = MapUtils.getInteger(params,"result",-2);
        System.out.println(res);
    }


}