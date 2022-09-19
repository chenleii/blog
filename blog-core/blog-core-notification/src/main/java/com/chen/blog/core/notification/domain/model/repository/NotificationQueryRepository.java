package com.chen.blog.core.notification.domain.model.repository;

import com.chen.blog.core.notification.domain.model.cqrs.representation.NotificationRepresentation;
import com.chen.blog.core.notification.domain.model.cqrs.query.NotificationPageQuery;
import com.chen.blog.core.notification.domain.model.cqrs.query.NotificationQuery;
import com.chen.blog.core.sharedkernel.cqrs.Pagination;
import com.chen.blog.core.sharedkernel.cqrs.annotation.QueryRepository;

/**
 * @author cl
 * @version 1.0
 * @since 2021/9/6 17:34
 */
@QueryRepository
public interface NotificationQueryRepository {


    Pagination<NotificationRepresentation> pageQuery(NotificationPageQuery pageQuery);



    NotificationRepresentation queryById(NotificationQuery query);

}
