package com.chen.blog.core.notification.domain.model;

import com.chen.blog.core.sharedkernel.ddd.annotation.Entity;
import lombok.*;

import java.util.HashMap;

/**
 * @author cl
 * @version 1.0
 * @since 2022/9/8 22:21
 */
@Entity
@Builder
@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
public class NotificationExtraInfo extends HashMap<String, Object> {



    public static NotificationExtraInfo empty() {
        return NotificationExtraInfo.builder().build();
    }
}
