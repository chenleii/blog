package com.chen.blog.core.notification.domain.model.cqrs.command;

import com.chen.blog.core.sharedkernel.cqrs.Command;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @author cl
 * @version 1.0
 * @since 2023/4/12 20:27
 */
@Getter
@ToString
@Builder
public class NotificationClearCommand implements Command {

    /**
     * 通知ID
     */
    @NotNull
    private final Long notificationId;

}
