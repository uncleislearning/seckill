package com.xiao.seckill.exceptions;

/**
 * Created by unclexiao on 30/03/2018.
 */
public class SecKillException extends RuntimeException {
    public SecKillException(String message) {
        super(message);
    }

    public SecKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
