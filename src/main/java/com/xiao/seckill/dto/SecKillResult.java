package com.xiao.seckill.dto;

/**
 * Created by unclexiao on 31/03/2018.
 *
 * 接口返回结果
 */
public class SecKillResult<T> {
    private T data;
    private boolean success;
    private String error;


    public SecKillResult(boolean success,T data) {
        this.data = data;
        this.success = success;
    }

    public SecKillResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
