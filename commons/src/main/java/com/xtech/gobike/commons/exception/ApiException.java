package com.xtech.gobike.commons.exception;

/**
 * Api异常
 */
public class ApiException extends RuntimeException {
    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    public ApiException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ApiException(Throwable throwable) {
        super(throwable);
    }
}
