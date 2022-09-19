
package com.chen.blog.core.notification.domain.model;

import com.chen.blog.core.sharedkernel.ddd.Identifiable;
import com.chen.blog.core.sharedkernel.ddd.Identifier;
import com.chen.blog.core.sharedkernel.ddd.annotation.ValueObject;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

/**
 * @author cl
 * @version 1.0
 * @since 2021/9/6 16:10
 */
@ValueObject
@EqualsAndHashCode(of = {"id"})
@ToString
@Setter(AccessLevel.PRIVATE)
public class NotificationId implements Identifiable<Long>, Identifier {
    /**
     * id
     */
    private Long id;

    private NotificationId(Long id) {
        setId(id);
    }

    public static NotificationId of(Long id) {
        Preconditions.checkNotNull(id);
        return new NotificationId(id);
    }

    @Override
    public Long getId() {
        return id;
    }
}
