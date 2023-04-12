package com.chen.blog.core.notification.domain.model.cqrs.command;

import com.chen.blog.core.sharedkernel.cqrs.Command;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @author cl
 * @version 1.0
 * @since 2023/4/12 21:23
 */
@Getter
@ToString
@Builder
public class NotificationArticleLikedCommand implements Command {

    /**
     * 文章ID
     */
    @NotNull
    private final Long articleId;

    /**
     * 喜欢的账户ID
     */
    @NotNull
    private final Long likedAccountId;
}
