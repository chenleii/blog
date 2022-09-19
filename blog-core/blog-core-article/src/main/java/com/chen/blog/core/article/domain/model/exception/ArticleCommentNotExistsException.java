package com.chen.blog.core.article.domain.model.exception;

import com.chen.blog.core.sharedkernel.exception.DomainRuntimeException;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 20:14
 */
public class ArticleCommentNotExistsException extends DomainRuntimeException {
    public ArticleCommentNotExistsException() {
        super();
    }

    public ArticleCommentNotExistsException(String message) {
        super(message);
    }

    public ArticleCommentNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArticleCommentNotExistsException(Throwable cause) {
        super(cause);
    }

    public ArticleCommentNotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
