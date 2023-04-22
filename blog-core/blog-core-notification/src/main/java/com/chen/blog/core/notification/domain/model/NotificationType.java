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
public enum NotificationType {
    /**
     * 账户通知
     */
    ACCOUNT,
    /**
     * 文章通知
     */
    ARTICLE,

    /**
     * 热度通知
     */
    HOT,

    ;

}
