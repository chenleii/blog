package com.chen.blog.core.notification.domain.model;

import com.chen.blog.core.sharedkernel.ddd.annotation.ValueObject;
import lombok.Getter;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 19:25
 */
@ValueObject
@Getter
public enum NotificationStatus {
    /**
     * 未读的
     */
    UNREAD,
    /**
     * 已读的
     */
    HAS_READ,

    /**
     * 清除的
     */
    CLEARED,

    ;

}
