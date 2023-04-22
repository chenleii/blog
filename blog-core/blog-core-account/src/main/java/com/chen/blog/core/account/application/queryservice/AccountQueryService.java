package com.chen.blog.core.account.application.queryservice;

import com.chen.blog.core.account.domain.model.cqrs.query.AccountQuery;
import com.chen.blog.core.account.domain.model.cqrs.representation.AccountRepresentation;
import com.chen.blog.core.account.domain.model.repository.AccountQueryRepository;
import com.chen.blog.core.sharedkernel.cqrs.Pagination;
import com.chen.blog.core.sharedkernel.cqrs.annotation.QueryService;
import com.chen.blog.core.sharedkernel.tracer.TraceMonitorLog;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

import jakarta.inject.Named;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/21 17:59
 */
@TraceMonitorLog
@QueryService
@Slf4j
@Named
@Validated
public class AccountQueryService {
    @Inject
    private AccountQueryRepository accountQueryRepository;

    public AccountRepresentation query(@Valid AccountQuery query) {
        return accountQueryRepository.queryById(query);
    }
}
