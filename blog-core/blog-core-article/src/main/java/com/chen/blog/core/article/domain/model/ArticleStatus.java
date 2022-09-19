package com.chen.blog.core.article.domain.model;

import com.chen.blog.core.sharedkernel.ddd.annotation.ValueObject;
import lombok.Getter;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 19:25
 */
@ValueObject
@Getter
public enum ArticleStatus {
    /**
     * 草稿
     */
    DRAFT,
    /**
     * 发布
     */
    PUBLICATION,

    /**
     * 可见的
     */
    VIEWABLE,

    /**
     * 不可见的
     */
    INVISIBLE,
    ;

}
