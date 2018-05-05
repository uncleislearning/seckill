package com.xiao.seckill.service;

import com.xiao.seckill.dto.Exposer;
import com.xiao.seckill.dto.SecKillExection;
import com.xiao.seckill.entity.SecKill;
import com.xiao.seckill.exceptions.RepeatKillException;
import com.xiao.seckill.exceptions.SecKillClosedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;


import static org.junit.Assert.*;

/**
 * Created by unclexiao on 30/03/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration({"classpath:Spring/spring-*.xml"})
public class SecKillServiceTest {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SecKillService secKillService;

    @Test
    public void getSecKillList() throws Exception {

        List<SecKill> res = secKillService.getSecKillList();
        logger.info("list={}", res);
        for (SecKill s : res) {
            System.out.println(s.toString());

        }
    }

    @Test
    public void getSecKillById() throws Exception {
        long id = 1002;
        System.out.println(secKillService.getSecKillById(id));
    }


    @Test
    public void exportSecKillUrl() throws Exception {
        long id = 1001;
        Exposer exposer = secKillService.exportSecKillUrl(id);

        //已经开始秒杀
        if (exposer.isExposed()) {
            System.out.println(exposer.getNow());
        }

    }

    @Test
    public void executeSecKill() throws Exception {

        long id = 1006;

        Exposer exposer = secKillService.exportSecKillUrl(id);

        if (exposer.isExposed()) {
            try {
                SecKillExection res = secKillService.executeSecKill(id, 18668067317L, exposer.getMd5());
                logger.info("result={}", res);
            } catch (RepeatKillException e) {
                logger.error(e.getMessage());
            } catch (SecKillClosedException e) {
                logger.error(e.getMessage());
            }
        } else {
            logger.warn("秒杀未开始");
        }

    }


    @Test
    public void executeSecKillByProcedure() {
        long id = 1004L;
        long userPhone = 18943535432L;

        Exposer exposer = secKillService.exportSecKillUrl(id);
        if (exposer.isExposed()){
            try{
               SecKillExection secKillExection =  secKillService.executeSecKill(id,userPhone,exposer.getMd5());
               logger.info("result:"+secKillExection);
            }catch (Exception e){
                logger.info(e.getMessage(),e);
            }

        }

    }


    @Test
    public void test(){

    }

}