package com.xiao.seckill.exceptions;

/**
 * Created by unclexiao on 30/03/2018.
 */
public class SecKillClosedException extends SecKillException {
    public SecKillClosedException(String message) {
        super(message);
    }

    public SecKillClosedException(String message, Throwable cause) {
        super(message, cause);
    }
}
