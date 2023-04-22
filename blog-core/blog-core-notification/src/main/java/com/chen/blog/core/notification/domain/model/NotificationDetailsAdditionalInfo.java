package com.chen.blog.core.notification.domain.model;

import com.chen.blog.core.sharedkernel.ddd.annotation.ValueObject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author cl
 * @version 1.0
 * @since 2022/9/8 22:21
 */
@ValueObject
@SuperBuilder
@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
public abstract class NotificationDetailsAdditionalInfo {


    @ValueObject
    @SuperBuilder
    @Getter
    @Setter(AccessLevel.PRIVATE)
    @ToString
    public static class AccountDisabled extends NotificationDetailsAdditionalInfo {

        /**
         * 禁用的账户ID
         */
        private Long accountId;
        /**
         * 禁用的账户名称
         */
        private String accountName;

    }

    @ValueObject
    @SuperBuilder
    @Getter
    @Setter(AccessLevel.PRIVATE)
    @ToString
    public static class ArticleLiked extends NotificationDetailsAdditionalInfo {

        /**
         * 文章ID
         */
        private Long articleId;
        /**
         * 文章标题
         */
        private String articleTitle;

        /**
         * 点赞的账户ID
         */
        private Long accountId;
        /**
         * 点赞的账户名称
         */
        private String accountName;

    }

    @ValueObject
    @SuperBuilder
    @Getter
    @Setter(AccessLevel.PRIVATE)
    @ToString
    public static class ArticleComment extends NotificationDetailsAdditionalInfo {

        /**
         * 文章ID
         */
        private Long articleId;
        /**
         * 文章标题
         */
        private String articleTitle;

        /**
         * 文章评论ID
         */
        private Long articleCommentId;

        /**
         * 文章子评论ID
         */
        private Long articleSubCommentId;
        /**
         * 文章回复的子评论ID
         */
        private Long articleReplySubCommentId;

        /**
         * 评论的账户ID
         */
        private Long accountId;
        /**
         * 评论的账户名称
         */
        private String accountName;
        /**
         * 评论内容艾特的账户ID列表
         */
        private List<Long> atAccountIds;

    }
}
