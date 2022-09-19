package com.chen.blog.core.notification.domain.model.exception;

import com.chen.blog.core.sharedkernel.exception.DomainRuntimeException;

/**
 * @author cl
 * @version 1.0
 * @since 2022/9/6 22:21
 */
public class NotificationNotExistsException extends DomainRuntimeException {
    public NotificationNotExistsException() {
    }

    public NotificationNotExistsException(String message) {
        super(message);
    }

    public NotificationNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotificationNotExistsException(Throwable cause) {
        super(cause);
    }

    public NotificationNotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
