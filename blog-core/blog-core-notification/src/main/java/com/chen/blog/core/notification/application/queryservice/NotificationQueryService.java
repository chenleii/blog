package com.chen.blog.core.notification.application.queryservice;

import com.chen.blog.core.notification.domain.model.cqrs.representation.NotificationRepresentation;
import com.chen.blog.core.notification.domain.model.cqrs.query.NotificationPageQuery;
import com.chen.blog.core.notification.domain.model.cqrs.query.NotificationQuery;
import com.chen.blog.core.notification.domain.model.repository.NotificationQueryRepository;
import com.chen.blog.core.sharedkernel.cqrs.Pagination;
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
 * @since 2022/8/20 20:09
 */
@TraceMonitorLog
@QueryService
@Slf4j
@Named
@Validated
public class NotificationQueryService {

    @Inject
    private NotificationQueryRepository notificationQueryRepository;

    public Pagination<NotificationRepresentation> pageQuery(@Valid NotificationPageQuery pageQuery) {
        return notificationQueryRepository.pageQuery(pageQuery);
    }

    public NotificationRepresentation query(@Valid NotificationQuery query) {
        return notificationQueryRepository.queryById(query);
    }
}
