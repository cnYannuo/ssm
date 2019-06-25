package com.yn.common.exception;

/**
 * Api统一异常类
 */
public class ApiException extends BaseException {

    public ApiException(String msg) {
        super(msg);
    }

    public ApiException(String code, String msg) {
        super(code, msg);
    }
}
