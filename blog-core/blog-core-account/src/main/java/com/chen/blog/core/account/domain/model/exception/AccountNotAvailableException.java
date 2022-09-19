package com.chen.blog.core.account.domain.model.exception;

import com.chen.blog.core.sharedkernel.exception.DomainRuntimeException;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 21:57
 */
public class AccountNotAvailableException extends DomainRuntimeException {
    public AccountNotAvailableException(String message) {
        super(message);
    }

    public AccountNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountNotAvailableException(Throwable cause) {
        super(cause);
    }

    public AccountNotAvailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
