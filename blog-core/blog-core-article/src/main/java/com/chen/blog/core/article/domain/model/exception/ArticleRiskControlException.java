package com.chen.blog.core.article.domain.model.exception;

import com.chen.blog.core.sharedkernel.exception.DomainRuntimeException;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/21 8:57
 */
public class ArticleRiskControlException extends DomainRuntimeException {
    public ArticleRiskControlException() {
    }

    public ArticleRiskControlException(String message) {
        super(message);
    }

    public ArticleRiskControlException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArticleRiskControlException(Throwable cause) {
        super(cause);
    }

    public ArticleRiskControlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
