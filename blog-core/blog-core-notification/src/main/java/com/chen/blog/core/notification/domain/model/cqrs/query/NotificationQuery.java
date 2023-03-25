package com.chen.blog.core.notification.domain.model.cqrs.query;

import com.chen.blog.core.sharedkernel.cqrs.Query;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import jakarta.validation.constraints.NotNull;

/**
 * @author cl
 * @version 1.0
 * @since 2022/9/6 22:24
 */
@Getter
@ToString
@Builder
public class NotificationQuery implements Query {

    /**
     * 通知ID
     */
    @NotNull
    private final Long notificationId;
}
