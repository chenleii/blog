package com.chen.blog.core.article.domain.model.exception;

import com.chen.blog.core.sharedkernel.exception.DomainRuntimeException;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 20:14
 */
public class ArticleNotExistsException extends DomainRuntimeException {
    public ArticleNotExistsException() {
        super();
    }

    public ArticleNotExistsException(String message) {
        super(message);
    }

    public ArticleNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArticleNotExistsException(Throwable cause) {
        super(cause);
    }

    public ArticleNotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
