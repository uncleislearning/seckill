package com.xiao.seckill.dao;

import com.xiao.seckill.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 * Created by unclexiao on 29/03/2018.
 */
public interface SuccessKilledDao {

    //插入购买记录

    /**
     * 插入购买明细，可以过滤重复 ？如何实现呢，数据表中使用的是联合主键（seckill_id,user_phone）
     *
     *
     *
     * @param secKillId
     * @param userPhone
     * @return 插入的行数
     */
    int insertSuccessKilled(@Param("secKillId") long secKillId, @Param("userPhone") long userPhone);



//    查询功能

    /**
     * 根据id查询SuccessKilled对象并携带秒杀产品对象实体
     *
     *
     * 为什么这里返回的不是一个列表？一个秒杀商品应该对应多条成功购买的记录啊？？ 为什么只用返回一个SuccessKilled对象
     * @param secKillId
     * @return
     */
    SuccessKilled querySuccessKilledWithIdPhone(@Param("secKillId") long secKillId,@Param("userPhone") long userPhone);
}
