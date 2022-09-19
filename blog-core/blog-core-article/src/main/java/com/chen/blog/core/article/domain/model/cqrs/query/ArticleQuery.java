package com.chen.blog.core.article.domain.model.cqrs.query;

import com.chen.blog.core.sharedkernel.cqrs.Query;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 20:10
 */
@Getter
@ToString
@Builder
public class ArticleQuery implements Query {

    /**
     * 当前的账户ID
     */
    private final Long currentAccountId;

    /**
     * 文章ID
     */
    @NotNull
    private final Long articleId;
}
