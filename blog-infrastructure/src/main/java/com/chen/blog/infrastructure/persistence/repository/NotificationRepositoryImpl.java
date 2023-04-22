package com.chen.blog.infrastructure.persistence.repository;

import com.chen.blog.core.notification.domain.model.Notification;
import com.chen.blog.core.notification.domain.model.NotificationId;
import com.chen.blog.core.notification.domain.model.cqrs.representation.NotificationRepresentation;
import com.chen.blog.core.notification.domain.model.cqrs.query.NotificationPageQuery;
import com.chen.blog.core.notification.domain.model.cqrs.query.NotificationQuery;
import com.chen.blog.core.notification.domain.model.repository.NotificationQueryRepository;
import com.chen.blog.core.notification.domain.model.repository.NotificationRepository;
import com.chen.blog.core.sharedkernel.cqrs.Pagination;
import com.chen.blog.infrastructure.persistence.repository.mongodb.AccountMongoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;

import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * @author cl
 * @version 1.0
 * @since 2021/9/7 16:41
 */
@Named
@Slf4j
public class NotificationRepositoryImpl implements NotificationRepository, NotificationQueryRepository {

    @Inject
    private MongoTemplate mongoTemplate;

    @Inject
    private AccountMongoRepository accountMongoRepository;


    @Override
    public Notification getById(NotificationId notificationId) {
        return null;
    }

    @Override
    public void save(Notification notification) {

    }


    @Override
    public Pagination<NotificationRepresentation> pageQuery(NotificationPageQuery pageQuery) {
        return null;
    }

    @Override
    public NotificationRepresentation queryById(NotificationQuery query) {
        return null;
    }
}
