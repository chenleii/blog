package com.chen.blog.core.account.domain.model.exception;

import com.chen.blog.core.sharedkernel.exception.NotExistException;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 21:57
 */
public class AccountNotExistsException extends NotExistException {
    public AccountNotExistsException(String message) {
        super(message);
    }

    public AccountNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountNotExistsException(Throwable cause) {
        super(cause);
    }

    public AccountNotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
