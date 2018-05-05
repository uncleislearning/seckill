package com.xiao.seckill.service.impl;

import com.xiao.seckill.common.SecKillState;
import com.xiao.seckill.dao.RedisDao;
import com.xiao.seckill.dao.SecKillDao;
import com.xiao.seckill.dao.SuccessKilledDao;
import com.xiao.seckill.dto.Exposer;
import com.xiao.seckill.dto.SecKillExection;
import com.xiao.seckill.entity.SecKill;
import com.xiao.seckill.entity.SuccessKilled;
import com.xiao.seckill.exceptions.RepeatKillException;
import com.xiao.seckill.exceptions.SecKillClosedException;
import com.xiao.seckill.exceptions.SecKillException;
import com.xiao.seckill.service.SecKillService;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by unclexiao on 30/03/2018.
 */
@Service
public class SecKillServiceImpl implements SecKillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecKillDao secKillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Autowired
    private RedisDao redisDao;

    //用于混淆md5
    private final String slat = "1538uwtbt0358940124(*P*(%^$@##%&WSDdsgusf";

    public List<SecKill> getSecKillList() {
        //默认只返回四条记录
        return secKillDao.queryAll(0,4);
    }

    public SecKill getSecKillById(long secKillId) {
        return secKillDao.queryById(secKillId);
    }

    public Exposer exportSecKillUrl(long secKillId) {
        //先去Redis去取
        SecKill secKill = redisDao.getSecKill(secKillId);
        if(secKill == null){
            //Redis中没有，则去数据库中去取
            secKill = secKillDao.queryById(secKillId);
            if(secKill == null){
                return new Exposer(false,secKillId);
            }
            //写入Redis
            redisDao.putSecKill(secKill);
        }

        Date startTime = secKill.getStartTime();
        Date endTime = secKill.getEndTime();

        Date now = new Date();

        if(now.getTime() < startTime.getTime() ||  now.getTime() >endTime.getTime()){
            return new Exposer(false,secKillId,now.getTime(),startTime.getTime(),endTime.getTime());
        }

        //MD5 由商品ID加密生成---》每个商品的秒杀接口地址URL中的MD5是唯一的
        String md5 = getMD5(secKillId);
        return new Exposer(true,md5,secKillId);
    }

    /**
     * 生成md5
     * @param secKillId
     * @return
     */
    private String getMD5(long secKillId){
        String base = secKillId+"/"+slat;
        String md5  = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Transactional
    /**
     * 使用注解控制事务的方式执行秒杀操作
     *
     * 以下操作有个最重要的点：为什么在这里 操作执行失败都要抛出异常而不是直接返回一个带错误信息的执行结果对象呢？
     * 原因是只有捕获到运行时异常，Spring管理的事实才会进行回滚
     *
     *
     * 在后续我们使用存储过程执行管理事务的方式中，在这一层的操作结果就可以不用抛出异常，而执行返回一个执行结果对象
     */
    public SecKillExection executeSecKill(long secKillId, long userPhone, String md5) throws SecKillException, RepeatKillException, SecKillClosedException {

        if(md5 == null || !md5.equals(getMD5(secKillId))){
            throw  new SecKillException("seckill data rewrite");
        }
        try {
            //记录购买行为
            int insertCount = successKilledDao.insertSuccessKilled(secKillId,userPhone);

            if(insertCount <0 ){
                throw new SecKillException("seckill inner error  ");
            }else if(insertCount ==0 ){
                throw new RepeatKillException("Repeat Kill ");
            }else  {
                //记录秒杀成功
                //执行秒杀逻辑:减库存
                Date now = new Date();
                //减库存
                int updateCount = secKillDao.reduceNumber(secKillId,now);
                if(updateCount < 0){
                    throw new SecKillException("seckill inner error  ");
                }else if(updateCount == 0){
                    throw new SecKillClosedException("seckill is closed");
                } else {
                    //减库存成功
                    SuccessKilled successKilled = successKilledDao.querySuccessKilledWithIdPhone(secKillId,userPhone);
                    return new SecKillExection(secKillId,SecKillState.SUCCESS,successKilled);
                }
            }
        }catch (SecKillClosedException e1){
            throw e1;
        } catch (RepeatKillException e2){
            throw e2;
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            //所有编译期异常转换成运行期异常
            throw new SecKillException("seckill inner error"+e.getMessage());
        }

    }


    /**
     * 通过存储过程来执行秒杀操作，事务的管理都放在MySQL端进行管理
     * @param secKillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SecKillException
     * @throws RepeatKillException
     * @throws SecKillClosedException
     */

    public SecKillExection executeSecKillByProcedure(long secKillId, long userPhone, String md5) {
        if(md5 == null || !md5.equals(getMD5(secKillId))){
            //这里 不在抛出异常
            return  new SecKillExection(secKillId,SecKillState.DATA_REWRITE);
        }

        Date now = new Date();

        Map<String,Object> params = new HashMap<String, Object>();
        params.put("seckill_id",secKillId);
        params.put("user_phone",userPhone);
        params.put("kill_time",now);
        params.put("result",null);

        try {
            //执行秒杀操作
            secKillDao.killByProcedure(params);
            int res =  MapUtils.getInteger(params,"result",-2);
            if(res == 1){
                SuccessKilled successKilled = successKilledDao.querySuccessKilledWithIdPhone(secKillId,userPhone);
                return new SecKillExection(secKillId,SecKillState.SUCCESS,successKilled);
            }else{
                return new SecKillExection(secKillId,SecKillState.stateOf(res));
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return new SecKillExection(secKillId,SecKillState.INNER_ERROR);
        }
    }


}
