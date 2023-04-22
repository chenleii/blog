package com.chen.blog.core.notification.domain.model.repository;

import com.chen.blog.core.notification.domain.model.Notification;
import com.chen.blog.core.notification.domain.model.NotificationId;
import com.chen.blog.core.notification.domain.model.exception.NotificationNotExistsException;
import com.chen.blog.core.sharedkernel.ddd.annotation.Repository;

import java.util.Optional;

/**
 * @author cl
 * @version 1.0
 * @since 2022/9/6 22:19
 */
@Repository
public interface NotificationRepository {


    Notification getById(NotificationId notificationId);


    default Notification getByIdNotExistsThrowException(NotificationId notificationId) {
        Notification notification = getById(notificationId);
        return Optional.ofNullable(notification)
                .orElseThrow(() -> new NotificationNotExistsException("notification not exists. " +
                        "notificationId:[" + notificationId.getId() + "]."));
    }

    void save(Notification notification);

    void update(Notification notification);

}
