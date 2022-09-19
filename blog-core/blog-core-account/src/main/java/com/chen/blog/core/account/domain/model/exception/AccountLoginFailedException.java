package com.chen.blog.core.account.domain.model.exception;

import com.chen.blog.core.sharedkernel.exception.DomainRuntimeException;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 21:57
 */
public class AccountLoginFailedException extends DomainRuntimeException {
    public AccountLoginFailedException(String message) {
        super(message);
    }

    public AccountLoginFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountLoginFailedException(Throwable cause) {
        super(cause);
    }

    public AccountLoginFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
