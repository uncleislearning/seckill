package com.xiao.seckill.dto;

/**
 * Created by unclexiao on 30/03/2018.
 *
 * 暴露秒杀地址DTO
 */
public class Exposer {

    //秒杀是否开启
    private boolean exposed;

    //加密
    private String md5;

    //秒杀商品
    private long seckillId;

    //秒杀未开始则返回系统当前时间
    private long now;

    //秒杀开始时间
    private long start;

    //秒杀结束时间
    private long end;

    public Exposer(boolean exposed, long secKillId) {
        this.exposed = exposed;
        this.seckillId = secKillId;
    }

    public Exposer(boolean exposed, long secKillId, long now, long start, long end) {
        this.exposed = exposed;
        this.seckillId = secKillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean exposed, String md5, long secKillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = secKillId;
    }


    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
