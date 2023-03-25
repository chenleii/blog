package com.chen.blog.core.article.domain.model.cqrs.command;

import com.chen.blog.core.sharedkernel.cqrs.Command;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 20:09
 */
@Getter
@ToString
@Builder
public class ArticleReplySubCommentCommand implements Command {

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
     * 回复的评论ID
     */
    private final Long replyCommentId;
    /**
     * 账户ID
     */
    @NotNull
    private final Long accountId;

    /**
     * 评论内容
     */
    @NotBlank
    private final String content;


}
