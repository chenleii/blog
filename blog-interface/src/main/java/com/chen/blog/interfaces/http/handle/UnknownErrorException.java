package com.chen.blog.interfaces.http.handle;

/**
 * @author cl
 * @version 1.0
 * @since 2022/9/3 15:52
 */
public class UnknownErrorException extends RuntimeException{
    public UnknownErrorException() {
    }

    public UnknownErrorException(String message) {
        super(message);
    }

    public UnknownErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownErrorException(Throwable cause) {
        super(cause);
    }

    public UnknownErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
