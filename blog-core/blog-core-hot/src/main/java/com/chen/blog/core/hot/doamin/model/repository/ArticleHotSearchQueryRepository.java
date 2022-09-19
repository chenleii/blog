package com.chen.blog.core.hot.doamin.model.repository;

import com.chen.blog.core.sharedkernel.cqrs.Pagination;
import com.chen.blog.core.hot.doamin.model.cqrs.query.ArticleHotSearchPageQuery;
import com.chen.blog.core.sharedkernel.cqrs.annotation.QueryRepository;

/**
 * @author cl
 * @version 1.0
 * @since 2021/9/6 17:34
 */
@QueryRepository
public interface ArticleHotSearchQueryRepository {


    Pagination<String> pageQuery(ArticleHotSearchPageQuery pageQuery);


}
