package com.xiao.seckill.entity;


import java.util.Date;

/**
 * Created by unclexiao on 29/03/2018.
 *
 * 与数据库表seckill字段保持一致
 */
public class SecKill {

    private long secKillId;

    private int num;
    private String name;
    private Date startTime;
    private Date endTime;
    private Date createTime;

    public long getSecKillId() {
        return secKillId;
    }

    public void setSecKillId(long secKillId) {
        this.secKillId = secKillId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }


    @Override
    public String toString() {
        return "secKillId:"+secKillId+",name:"+name+",num:"+num
                +",startTime:"+startTime+",endTime:"+endTime+",createTime:"+createTime;
    }
}
