package com.chen.blog.core.notification.domain.model;

import com.chen.blog.core.account.domain.model.AccountId;
import com.chen.blog.core.sharedkernel.ddd.annotation.AggregateRoot;
import com.chen.blog.core.sharedkernel.event.DomainEventPublisher;
import com.google.common.base.Preconditions;
import lombok.*;
import org.apache.commons.lang3.ObjectUtils;

import java.time.Instant;

/**
 * @author cl
 * @version 1.0
 * @since 2022/9/6 21:50
 */
@AggregateRoot
@Builder
@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
public class Notification implements DomainEventPublisher {


    /**
     * ID
     */
    private NotificationId id;

    /**
     * 账户ID
     */
    private AccountId accountId;

    /**
     * 详情
     */
    private NotificationDetails details;

    /**
     * 状态
     */
    private NotificationStatus status;

    /**
     * 创建于
     */
    private Instant createdAt;
    /**
     * 已读于
     */
    private Instant hasReadAt;
    /**
     * 清除于
     */
    private Instant clearedAt;


    public static Notification create(NotificationId id, AccountId accountId, NotificationDetails details) {
        Preconditions.checkNotNull(id);
        Preconditions.checkNotNull(accountId);

        Instant now = Instant.now();

        return Notification.builder()
                .id(id)
                .accountId(accountId)
                .details(details)
                .status(NotificationStatus.UNREAD)
                .createdAt(now)
                .hasReadAt(Instant.EPOCH)
                .clearedAt(Instant.EPOCH)
                .build();
    }


    public void read() {
        setStatus(NotificationStatus.HAS_READ);
        setHasReadAt(Instant.now());
    }

    public void clear() {
        setStatus(NotificationStatus.CLEARED);
        setClearedAt(Instant.now());
    }
}
