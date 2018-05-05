package com.xiao.seckill.exceptions;

/**
 * Created by unclexiao on 30/03/2018.
 */
public class RepeatKillException extends SecKillException {

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
