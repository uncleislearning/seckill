package com.xiao.seckill.dao;

import com.xiao.seckill.entity.SecKill;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by unclexiao on 29/03/2018.
 */
public interface SecKillDao {

    /**
     * 减库存
     *
     *
     * @param secKillId  秒杀商品
     * @param killTime  秒杀的时间  killTime应该在start_time end_time之间
     * @return 影响行数
     */
    int reduceNumber(@Param("secKillId") long secKillId,@Param("killTime") Date killTime);



//    查询相关


    /**
     * 查询 秒杀商品库存信息对象
     * @param secKillId
     * @return
     */
    SecKill queryById(long secKillId);

    /**
     * 根据偏移量来查询秒杀商品列表
     * @param offset
     * @param limit
     * @return
     */
    List<SecKill> queryAll(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 调用存储过程，执行秒杀全过程操作
     * @param params
     */
    void killByProcedure(Map<String,Object> params);
}
