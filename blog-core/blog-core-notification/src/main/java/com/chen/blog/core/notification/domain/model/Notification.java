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
     * id
     */
    private NotificationId id;

    /**
     * 账户ID
     */
    private AccountId accountId;

    /**
     * 类型
     */
    private NotificationType type;

    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;

    /**
     * 额外信息
     * <p>
     * 如: 来源ID
     */
    private NotificationExtraInfo extraInfo;

    /**
     * 状态
     */
    private NotificationStatus status;

    /**
     * 创建于
     */
    private Instant createdAt;
    /**
     * 查看于
     */
    private Instant viewedAt;


    public static Notification create(NotificationId id, AccountId accountId, NotificationType type,
                                      String title, String content, NotificationExtraInfo extraInfo) {
        Preconditions.checkNotNull(id);
        Preconditions.checkNotNull(accountId);
        Preconditions.checkNotNull(type);
        Preconditions.checkNotNull(title);
        Preconditions.checkNotNull(content);

        extraInfo = ObjectUtils.defaultIfNull(extraInfo, NotificationExtraInfo.empty());

        Instant now = Instant.now();

        return Notification.builder()
                .id(id)
                .accountId(accountId)
                .type(type)
                .title(title)
                .content(content)
                .extraInfo(extraInfo)
                .status(NotificationStatus.UNREAD)
                .createdAt(now)
                .viewedAt(Instant.EPOCH)
                .build();
    }
}
