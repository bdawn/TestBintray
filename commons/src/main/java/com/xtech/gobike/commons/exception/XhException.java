package com.xtech.gobike.commons.exception;

public class XhException extends RuntimeException{

    public XhException() {
        super();
    }

    public XhException(String message) {
        super(message);
    }

    public XhException(String message, Throwable cause) {
        super(message, cause);
    }

    public XhException(Throwable cause) {
        super(cause);
    }

    public static XhException wrap(String message) {
        return new XhException(message);
    }

    public static XhException wrap(String message, Throwable cause) {
        return new XhException(message, cause);
    }

    public static XhException wrap(Throwable cause) {
        if (cause == null)
            throw wrap(new NullPointerException());

        return (XhException.class.isInstance(cause)) ?
                XhException.class.cast(cause)
                : new XhException(cause);

    }

}
