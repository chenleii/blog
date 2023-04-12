package com.chen.blog.core.notification.domain.model;

import com.chen.blog.core.sharedkernel.ddd.annotation.ValueObject;
import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author cl
 * @version 1.0
 * @since 2022/8/20 19:25
 */
@ValueObject
@Getter
@AllArgsConstructor
public enum NotificationDetailsType {

    ACCOUNT_DISABLED(NotificationDetailsAdditionalInfo.AccountDisabled.class) {
        @Override
        public String generateTitle(NotificationDetailsAdditionalInfo additionalInfo) {
            return "账户禁用通知";
        }

        @Override
        public String generateContent(NotificationDetailsAdditionalInfo additionalInfo) {
            return "很抱歉，因为种种原因你的账户已被禁用。";
        }
    },

    ARTICLE_LIKE(NotificationDetailsAdditionalInfo.ArticleLiked.class) {
        @Override
        public String generateTitle(NotificationDetailsAdditionalInfo additionalInfo) {
            return "文章点赞通知";
        }

        @Override
        public String generateContent(NotificationDetailsAdditionalInfo additionalInfo) {
            this.checkAdditionalInfo(additionalInfo);
            NotificationDetailsAdditionalInfo.ArticleLiked articleLiked = (NotificationDetailsAdditionalInfo.ArticleLiked) additionalInfo;
            return String.format("%s 点赞了你的文章【%s】。",
                    articleLiked.getAccountName(),
                    articleLiked.getArticleTitle());
        }

    },

    ARTICLE_COMMENT(NotificationDetailsAdditionalInfo.ArticleComment.class) {
        @Override
        public String generateTitle(NotificationDetailsAdditionalInfo additionalInfo) {
            return "文章评论通知";
        }

        @Override
        public String generateContent(NotificationDetailsAdditionalInfo additionalInfo) {
            this.checkAdditionalInfo(additionalInfo);
            NotificationDetailsAdditionalInfo.ArticleComment articleComment = (NotificationDetailsAdditionalInfo.ArticleComment) additionalInfo;
            return String.format("%s 评论了你的文章【%s】。",
                    articleComment.getAccountName(),
                    articleComment.getArticleTitle());
        }
    },


    ;

    /**
     * 额外信息的class
     */
    private final Class<? extends NotificationDetailsAdditionalInfo> additionalInfoClass;

    /**
     * 生成标题
     *
     * @param additionalInfo 额外信息
     * @return 标题
     */
    abstract String generateTitle(NotificationDetailsAdditionalInfo additionalInfo);

    /**
     * 生成内容
     *
     * @param additionalInfo 额外信息
     * @return 内容
     */
    abstract String generateContent(NotificationDetailsAdditionalInfo additionalInfo);

    /**
     * 检查额外信息类型
     *
     * @param additionalInfo 额外信息
     */
    public void checkAdditionalInfo(NotificationDetailsAdditionalInfo additionalInfo) {
        Preconditions.checkNotNull(additionalInfo);
        Preconditions.checkArgument(this.getAdditionalInfoClass().isInstance(additionalInfo));
    }

}
