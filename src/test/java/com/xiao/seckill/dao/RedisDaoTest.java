package com.xiao.seckill.dao;

import com.xiao.seckill.entity.SecKill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by unclexiao on 02/04/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:Spring/spring-dao.xml"})
public class RedisDaoTest {

    private final Logger logger  = LoggerFactory.getLogger(this.getClass());
    private final long id = 1001L;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SecKillDao secKillDao;

    @Test
    public void redis() throws Exception {
            SecKill seckill = redisDao.getSecKill(id);

            //缓存中没有，去数据库取
            if(seckill == null){
               seckill =  secKillDao.queryById(id);
               logger.info("从数据库取到:"+seckill.toString());
               // 没有，则写入缓存
               redisDao.putSecKill(seckill);
            }else {
                //缓存中有
                logger.info("从缓存中取到："+seckill.toString());
            }
    }


}