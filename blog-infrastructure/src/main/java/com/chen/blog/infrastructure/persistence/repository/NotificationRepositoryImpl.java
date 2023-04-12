package com.chen.blog.infrastructure.persistence.repository;

import com.chen.blog.core.notification.domain.model.Notification;
import com.chen.blog.core.notification.domain.model.NotificationId;
import com.chen.blog.core.notification.domain.model.NotificationStatus;
import com.chen.blog.core.notification.domain.model.cqrs.representation.NotificationRepresentation;
import com.chen.blog.core.notification.domain.model.cqrs.query.NotificationPageQuery;
import com.chen.blog.core.notification.domain.model.cqrs.query.NotificationQuery;
import com.chen.blog.core.notification.domain.model.repository.NotificationQueryRepository;
import com.chen.blog.core.notification.domain.model.repository.NotificationRepository;
import com.chen.blog.core.sharedkernel.cqrs.Pagination;
import com.chen.blog.infrastructure.persistence.repository.dataobject.AccountDO;
import com.chen.blog.infrastructure.persistence.repository.dataobject.NotificationDO;
import com.chen.blog.infrastructure.persistence.repository.domainconverter.AccountConverter;
import com.chen.blog.infrastructure.persistence.repository.domainconverter.NotificationConverter;
import com.chen.blog.infrastructure.persistence.repository.mongodb.AccountMongoRepository;
import com.chen.blog.infrastructure.persistence.repository.mongodb.NotificationMongoRepository;
import com.chen.blog.infrastructure.persistence.repository.representationconverter.NotificationRepresentationConverter;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author cl
 * @version 1.0
 * @since 2023/4/12 23:20
 */
@Named
@Slf4j
public class NotificationRepositoryImpl implements NotificationRepository, NotificationQueryRepository {

    @Inject
    private MongoTemplate mongoTemplate;

    @Inject
    private AccountMongoRepository accountMongoRepository;
    @Inject
    private NotificationMongoRepository notificationMongoRepository;


    @Override
    public Notification getById(NotificationId notificationId) {
        Preconditions.checkNotNull(notificationId);

        Optional<NotificationDO> notificationDO = notificationMongoRepository.findById(notificationId.getId());
        return NotificationConverter.MAPPER.from(notificationDO.orElse(null));
    }

    @Override
    public void save(Notification notification) {
        Preconditions.checkNotNull(notification);

        NotificationDO notificationDO = NotificationConverter.MAPPER.to(notification);
        notificationMongoRepository.save(notificationDO);
    }

    @Override
    public void update(Notification notification) {
        save(notification);
    }


    @Override
    public Pagination<NotificationRepresentation> pageQuery(NotificationPageQuery pageQuery) {
        Preconditions.checkNotNull(pageQuery);
        Preconditions.checkNotNull(pageQuery.getPageIndex());
        Preconditions.checkNotNull(pageQuery.getPageSize());
        Preconditions.checkNotNull(pageQuery.getAccountId());

        Long pageIndex = pageQuery.getPageIndex();
        Long pageSize = pageQuery.getPageSize();
        Long accountId = pageQuery.getAccountId();
        Set<NotificationStatus> statuses = pageQuery.getStatuses();

        Criteria criteria = Criteria.where("accountId").is(accountId);
        if (CollectionUtils.isNotEmpty(statuses)) {
            criteria.and("status").in(statuses);
        }
        long count = mongoTemplate.count(Query.query(criteria), NotificationDO.class);
        List<NotificationDO> notificationDOList = mongoTemplate.query(NotificationDO.class)
                .matching(Query.query(criteria)
                                .with(Sort.by(Sort.Direction.DESC, "createdAt", "id"))
                                .skip((pageIndex - 1) * pageQuery.getPageSize())
                                .limit(pageSize.intValue())
                )
                .all();

        List<NotificationRepresentation> notificationRepresentations = NotificationRepresentationConverter.MAPPER.listFromList(notificationDOList);
        return Pagination.create(pageQuery, count, notificationRepresentations);
    }

    @Override
    public NotificationRepresentation queryById(NotificationQuery query) {
        Preconditions.checkNotNull(query);
        Preconditions.checkNotNull(query.getNotificationId());

        Optional<NotificationDO> notificationDO = notificationMongoRepository.findById(query.getNotificationId());
        return NotificationRepresentationConverter.MAPPER.from(notificationDO.orElse(null));
    }
}
