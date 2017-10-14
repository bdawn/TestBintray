package com.xtech.gobike.commons.dao;

/**
 * dao 异常
 * Created by heshen on 15/9/21.
 */
public class DaoException extends Exception {
    public DaoException(String detailMessage) {
        super(detailMessage);
    }

    public DaoException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public DaoException(Throwable throwable) {
        super(throwable);
    }
}
