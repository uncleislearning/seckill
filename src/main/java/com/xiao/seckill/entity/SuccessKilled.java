package com.xiao.seckill.entity;

import java.sql.Date;

/**
 * Created by unclexiao on 29/03/2018.
 *
 * 跟数据库中success_killed保持一致
 */
public class SuccessKilled {

    private long secKillId;
    private long userPhone;
    private short state;
    private Date createTime;

    //多对一
    private SecKill secKill;

    public long getSecKillId() {
        return secKillId;
    }

    public void setSecKillId(long secKillId) {
        this.secKillId = secKillId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public SecKill getSecKill() {
        return secKill;
    }

    public void setSecKill(SecKill secKill) {
        this.secKill = secKill;
    }


    @Override
    public String toString() {
        return "secKillId:"+secKillId+",userPhone:"+userPhone+",state:"+state+",createTime:"+createTime;
    }
}
