package com.chen.blog.core.hot.doamin.model.repository;

import com.chen.blog.core.sharedkernel.cqrs.Pagination;
import com.chen.blog.core.hot.doamin.model.cqrs.query.ArticleHotPageQuery;
import com.chen.blog.core.article.domain.model.cqrs.representation.ArticleRepresentation;
import com.chen.blog.core.sharedkernel.cqrs.annotation.QueryRepository;

/**
 * @author cl
 * @version 1.0
 * @since 2021/9/6 17:34
 */
@QueryRepository
public interface ArticleHotQueryRepository {


    Pagination<ArticleRepresentation> pageQuery(ArticleHotPageQuery pageQuery);


}
