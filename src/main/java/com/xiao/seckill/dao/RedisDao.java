package com.xiao.seckill.dao;

import com.dyuproject.protostuff.LinkedBuffer;

import com.dyuproject.protostuff.ProtostuffIOUtil;

import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.xiao.seckill.entity.SecKill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by unclexiao on 02/04/2018.
 */
public class RedisDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //用于生成Jedis对象
    private final JedisPool jedisPool;

    private RuntimeSchema<SecKill> schema = RuntimeSchema.createFrom(SecKill.class);

    public RedisDao(String host, int port) {
        this.jedisPool = new JedisPool(host, port);
    }

    /**
     * 从Redis缓存中 取出Seckill对象
     *
     * @param secKillId
     * @return
     */

    public SecKill getSecKill(long secKillId) {

        try {
            //可能会抛异常
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "SecKill:" + secKillId;
                //从Redis拿到缓存的SecKill对象字节数组
                byte[] bytes = jedis.get(key.getBytes());
                //反序列化  现在就要考虑SecKill对象该使用怎样的序列化机制，Java默认的？还是Protostuff？还是其他更好的序列化框架

                if (bytes != null) {
                    //反序列化
                    SecKill secKill = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes, secKill, schema);
                    return secKill;
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * 向Redis中放入SecKill对象进行缓存
     *
     * @param secKill
     * @return 放入Redis的结果状态码 ：成功 ok
     */
    public String putSecKill(SecKill secKill) {
        try {
            Jedis jedis = jedisPool.getResource();

            try {
                String key = "SecKill:" + secKill.getSecKillId();
                int timeout = 60 * 30; //单位秒  缓存过期时间
                //序列化
                byte[] values = ProtostuffIOUtil.toByteArray(secKill, schema
                        , LinkedBuffer.allocate(LinkedBuffer.MIN_BUFFER_SIZE));

                //存入Redis
                String res = jedis.setex(key.getBytes(), timeout, values);
                return res;
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
