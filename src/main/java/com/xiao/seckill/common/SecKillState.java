package com.xiao.seckill.common;

/**
 * Created by unclexiao on 30/03/2018.
 * <p>
 * 使用枚举表示常量数据字段
 */
public enum SecKillState {
    SUCCESS(1, "秒杀成功"),
    END(0, "秒杀结束"),
    REPEAT_KILL(-1, "重复秒杀"),
    INNER_ERROR(-2, "系统异常"),
    DATA_REWRITE(-3, "数据篡改");



    private int state;
    private String stateInfo;


    SecKillState(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }


    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }


    /**
     * 通过state找到对应的SecKillState对象
     *
     * @param state
     * @return
     */
    public static SecKillState stateOf(int state) {
        for (SecKillState s : values()) {
            if (s.getState() == state) {
                return s;
            }
        }
        return null;
    }


}
