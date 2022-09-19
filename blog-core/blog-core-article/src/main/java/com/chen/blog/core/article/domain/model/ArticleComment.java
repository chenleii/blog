package com.chen.blog.core.article.domain.model;

import com.chen.blog.core.account.domain.model.AccountId;
import com.chen.blog.core.sharedkernel.ddd.annotation.Entity;
import com.google.common.base.Preconditions;
import lombok.*;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

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
public class ArticleComment {

    /**
     * 评论ID
     */
    private ArticleCommentId id;
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

    /**
     * 子评论
     */
    private Set<ArticleSubComment> subComments;


    public static ArticleComment create(ArticleCommentId commentId, AccountId accountId, String content) {
        Preconditions.checkNotNull(commentId);
        Preconditions.checkNotNull(accountId);
        Preconditions.checkNotNull(content);

        return ArticleComment.builder()
                .id(commentId)
                .accountId(accountId)
                .content(content)
                .commentedAt(Instant.now())
                .subComments(new LinkedHashSet<>())
                .build();
    }

    /**
     * 回复子评论
     *
     * @param replySubComment 回复的子评论
     */
    public void replySubComment(ArticleSubComment replySubComment) {
        getSubComments().add(replySubComment);
    }

}
