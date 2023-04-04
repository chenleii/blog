package com.chen.blog.core.article.application.queryservice;

import com.chen.blog.core.article.domain.model.cqrs.query.ArticlePageQuery;
import com.chen.blog.core.article.domain.model.cqrs.query.ArticleQuery;
import com.chen.blog.core.article.domain.model.cqrs.representation.ArticleRepresentation;
import com.chen.blog.core.article.domain.model.repository.ArticleQueryRepository;
import com.chen.blog.core.sharedkernel.cqrs.Pagination;
import com.chen.blog.core.sharedkernel.cqrs.annotation.QueryService;
import com.chen.blog.core.sharedkernel.tracer.TraceMonitorLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.Valid;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 20:09
 */
@TraceMonitorLog
@QueryService
@Slf4j
@Named
@Validated
public class ArticleQueryService {

    @Inject
    private ArticleQueryRepository articleQueryRepository;

    public Pagination<ArticleRepresentation> pageQuery(@Valid ArticlePageQuery pageQuery) {
        return articleQueryRepository.pageQuery(pageQuery);
    }

    public ArticleRepresentation query(@Valid ArticleQuery query) {
        return articleQueryRepository.queryById(query);
    }
}
