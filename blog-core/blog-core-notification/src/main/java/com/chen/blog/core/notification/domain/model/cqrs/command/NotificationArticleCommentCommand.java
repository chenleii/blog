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
public class NotificationArticleCommentCommand implements Command {

    /**
     * 文章ID
     */
    @NotNull
    private final Long articleId;

    /**
     * 评论ID
     */
    @NotNull
    private final Long commentId;

    /**
     * 子评论ID
     */
    private final Long subCommentId;
    /**
     * 回复的子评论ID
     */
    private final Long replySubCommentId;

    /**
     * 评论的账户ID
     */
    @NotNull
    private final Long commentAccountId;
}
