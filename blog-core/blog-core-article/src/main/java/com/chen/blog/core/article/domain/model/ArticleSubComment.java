package com.chen.blog.core.article.domain.model;

import com.chen.blog.core.account.domain.model.AccountId;
import com.chen.blog.core.sharedkernel.ddd.annotation.Entity;
import com.google.common.base.Preconditions;
import lombok.*;

import java.time.Instant;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 19:14
 */
@Entity
@Builder
@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode(of = "id")
public class ArticleSubComment {

    /**
     * 评论ID
     */
    private ArticleSubCommentId id;
    /**
     * 回复的子评论ID
     */
    private ArticleSubCommentId replyId;
    /**
     * 评论的账户ID
     */
    private AccountId accountId;

    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论于
     */
    private Instant commentedAt;


    public static ArticleSubComment create(ArticleSubCommentId subCommentId, ArticleSubCommentId replySubCommentId, AccountId accountId, String content) {
        Preconditions.checkNotNull(subCommentId);
        Preconditions.checkNotNull(accountId);
        Preconditions.checkNotNull(content);

        return ArticleSubComment.builder()
                .id(subCommentId)
                .replyId(replySubCommentId)
                .accountId(accountId)
                .content(content)
                .commentedAt(Instant.now())
                .build();
    }


}
