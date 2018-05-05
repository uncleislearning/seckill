package com.xiao.seckill.dto;

import com.xiao.seckill.common.SecKillState;
import com.xiao.seckill.entity.SuccessKilled;

/**
 * Created by unclexiao on 30/03/2018.
 *
 *
 * 封装秒杀执行后结果
 */
public class SecKillExection{

    //秒杀的商品
    private long secKillId;

    //秒杀结果
    private int state;

    //秒杀结果信息
    private String stateInfo;

    //秒杀成功对象
    private SuccessKilled successKilled;

    public SecKillExection(long secKillId,SecKillState state, SuccessKilled successKilled) {
        this.secKillId = secKillId;
        this.state =  state.getState();
        this.stateInfo = state.getStateInfo();
        this.successKilled = successKilled;
    }

    public SecKillExection(long secKillId, SecKillState state) {
        this.secKillId = secKillId;
        this.state = state.getState();
        this.stateInfo = state.getStateInfo();
    }

    public long getSecKillId() {
        return secKillId;
    }

    public void setSecKillId(long secKillId) {
        this.secKillId = secKillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }

    @Override
    public String toString() {
        return "SecKillExection{" +
                "secKillId=" + secKillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKilled=" + successKilled +
                '}';
    }
}
