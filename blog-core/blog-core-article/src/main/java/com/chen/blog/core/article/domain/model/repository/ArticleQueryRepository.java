package com.chen.blog.core.article.domain.model.repository;

import com.chen.blog.core.article.domain.model.cqrs.representation.ArticleRepresentation;
import com.chen.blog.core.sharedkernel.cqrs.Pagination;
import com.chen.blog.core.article.domain.model.cqrs.query.ArticlePageQuery;
import com.chen.blog.core.article.domain.model.cqrs.query.ArticleQuery;
import com.chen.blog.core.sharedkernel.cqrs.annotation.QueryRepository;

/**
 * @author cl
 * @version 1.0
 * @since 2021/9/6 17:34
 */
@QueryRepository
public interface ArticleQueryRepository {


    Pagination<ArticleRepresentation> pageQuery(ArticlePageQuery pageQuery);


    ArticleRepresentation queryById(ArticleQuery query);

}
