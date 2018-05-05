package com.xiao.seckill.service;

import com.xiao.seckill.dto.Exposer;
import com.xiao.seckill.dto.SecKillExection;
import com.xiao.seckill.entity.SecKill;
import com.xiao.seckill.exceptions.RepeatKillException;
import com.xiao.seckill.exceptions.SecKillClosedException;
import com.xiao.seckill.exceptions.SecKillException;

import java.util.List;

/**
 * Created by unclexiao on 30/03/2018.
 */
public interface SecKillService {


    /**
     * 查询 所有秒杀商品列表
     *
     * @return
     */
    List<SecKill> getSecKillList();

    /**
     * 查看单个秒杀商品
     *
     * @param secKillId
     * @return
     */
    SecKill getSecKillById(long secKillId);

    /**
     * 输出秒杀接口地址，否则输出系统时间和秒杀时间
     * 为什么要输出一个秒杀接口地址呢？难道不是固定的嘛
     * 当然不是，如果秒杀接口是固定的，那么就可能别人在秒杀开始之前已经知道秒杀接口，从而使用程序开抢
     * <p>
     * <p>
     * 效果：秒杀之前，接口地址不是确定的
     *
     * @param secKillId
     */
    Exposer exportSecKillUrl(long secKillId);


    /**
     * 执行秒杀
     * @param secKillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SecKillException
     * @throws RepeatKillException
     * @throws SecKillClosedException
     */
    SecKillExection executeSecKill(long secKillId, long userPhone, String md5)
            throws SecKillException,RepeatKillException,SecKillClosedException;





    /**
     * 通过存储过程 执行秒杀
     * @param secKillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SecKillException
     * @throws RepeatKillException
     * @throws SecKillClosedException
     */
    SecKillExection executeSecKillByProcedure(long secKillId, long userPhone, String md5);
}
