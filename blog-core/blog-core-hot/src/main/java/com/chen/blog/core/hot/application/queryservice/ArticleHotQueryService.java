package com.chen.blog.core.hot.application.queryservice;

import com.chen.blog.core.article.domain.model.cqrs.representation.ArticleRepresentation;
import com.chen.blog.core.sharedkernel.cqrs.Pagination;
import com.chen.blog.core.hot.doamin.model.cqrs.query.ArticleHotPageQuery;
import com.chen.blog.core.hot.doamin.model.repository.ArticleHotQueryRepository;
import com.chen.blog.core.sharedkernel.cqrs.annotation.QueryService;
import com.chen.blog.core.sharedkernel.trace.TraceMonitorLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.Valid;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/23 23:46
 */
@TraceMonitorLog
@QueryService
@Slf4j
@Named
@Validated
public class ArticleHotQueryService {
    @Inject
    private ArticleHotQueryRepository articleHotQueryRepository;


    public Pagination<ArticleRepresentation> pageQuery(@Valid ArticleHotPageQuery pageQuery) {
        return articleHotQueryRepository.pageQuery(pageQuery);
    }
}
