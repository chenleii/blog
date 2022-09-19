package com.chen.blog.core.notification.domain.model.cqrs.command;

import com.chen.blog.core.sharedkernel.cqrs.Command;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @author cl
 * @version 1.0
 * @since 2022/9/6 22:27
 */
@Getter
@ToString
@Builder
public class NotificationCreateCommand implements Command {

}
