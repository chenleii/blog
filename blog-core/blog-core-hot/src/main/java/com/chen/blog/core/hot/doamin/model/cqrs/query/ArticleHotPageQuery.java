package com.chen.blog.core.hot.doamin.model.cqrs.query;

import com.chen.blog.core.sharedkernel.cqrs.PageQuery;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 20:10
 */
@Getter
@ToString
@SuperBuilder
public class ArticleHotPageQuery extends PageQuery {

    /**
     * 当前的账户ID
     */
    private final Long currentAccountId;

}
